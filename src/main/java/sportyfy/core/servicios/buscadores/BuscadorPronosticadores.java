package sportyfy.core.servicios.buscadores;

import lombok.Data;
import sportyfy.core.entidades.Pronosticador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

/**
 * Clase que busca pronosticadores en un directorio
 */
@Data
public class BuscadorPronosticadores {
    private final Logger logger = Logger.getLogger(BuscadorPronosticadores.class.getName());

    /**
     * Busca pronosticadores en un directorio
     *
     * @param ruta Ruta del directorio
     * @return Conjunto de pronosticadores
     * @throws FileNotFoundException Si no existe el directorio
     */
    public Set<Pronosticador> buscarPronosticadores(String ruta) throws IOException {
        Set<Pronosticador> pronosticadores = new HashSet<>();
        File directorio = new File(ruta);

        if (directorio.exists() && directorio.isDirectory()) {
            for (File archivo : Objects
                    .requireNonNull(directorio.listFiles(file -> file.isFile() && file.getName().endsWith(".jar")))) {
                pronosticadores.addAll(obtenerPronosticadoresDesdeJar(archivo));
            }
        } else {
            throw new IOException("El directorio no existe o no es un directorio válido");
        }
        return pronosticadores;
    }

    /**
     * Obtiene los pronosticadores de un archivo .jar
     *
     * @param archivoJar Archivo .jar
     * @return Conjunto de pronosticadores
     * @throws IllegalArgumentException Si la extensión del archivo no es .jar
     */
    private Set<Pronosticador> obtenerPronosticadoresDesdeJar(File archivoJar) throws IOException {
        Set<Pronosticador> pronosticadores = new HashSet<>();
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { archivoJar.toURI().toURL() });

        try (JarFile archivoJAR = new JarFile(archivoJar)) {
            for (JarEntry entrada : archivoJAR.stream().filter(e -> e.getName().endsWith(".class"))
                    .toArray(JarEntry[]::new)) {
                String nombreClase = entrada.getName().replace('/', '.').substring(0, entrada.getName().length() - 6);
                try {
                    Class<?> cls = Class.forName(nombreClase, true, classLoader);
                    if (Pronosticador.class.isAssignableFrom(cls)) {
                        pronosticadores.add((Pronosticador) cls.getDeclaredConstructor().newInstance());
                    }
                } catch (Exception e) {
                    logger.severe("Error al cargar la clase: " + nombreClase);
                }
            }
        } catch (IOException e) {
            logger.severe("Error al abrir el archivo " + archivoJar.getName());
            throw e;
        }
        return pronosticadores;
    }

    /**
     * Busca un pronosticador en un conjunto de pronosticadores
     *
     * @param pronosticadores Conjunto de pronosticadores
     * @param nombre          Nombre del pronosticador
     * @return Pronosticador encontrado o null
     */
    public Pronosticador buscarPronosticador(Set<Pronosticador> pronosticadores, String nombre) {
        return pronosticadores.stream()
                .filter(p -> p.getClass().getSimpleName().equals(nombre))
                .findFirst()
                .orElse(null);
    }
}