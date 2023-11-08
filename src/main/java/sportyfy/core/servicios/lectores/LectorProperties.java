package sportyfy.core.servicios.lectores;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.Optional;

/**
 * Clase que se encarga de leer archivos de propiedades.
 */
public class LectorProperties {

    private static final Logger logger = Logger.getLogger(LectorProperties.class.getName());

    /**
     * Lee un archivo de propiedades y devuelve el valor de una propiedad.
     *
     * @param property    La propiedad que se quiere leer.
     * @param rutaArchivo La ruta del archivo de propiedades.
     * @return El valor de la propiedad.
     */
    public static Optional<String> leerProperties(String property, String rutaArchivo) {
        Properties prop = new Properties();

        try (Reader reader = Files.newBufferedReader(Paths.get(rutaArchivo))) {
            prop.load(reader);
            return Optional.ofNullable(prop.getProperty(property));
        } catch (IOException e) {
            logger.severe("Error al leer el archivo de propiedades: " + e.getMessage());
            return Optional.empty();
        }
    }

}