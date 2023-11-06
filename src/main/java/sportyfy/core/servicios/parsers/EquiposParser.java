package sportyfy.core.servicios.parsers;

import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.servicios.lectores.LectorJson;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase que se encarga de crear los equipos a partir de los nombres de los archivos de partidos.
 */
public class EquiposParser {
    /**
     * Crea los equipos a partir de los nombres de los archivos de partidos.
     *
     * @param rutaCarpetaPartidos La ruta de la carpeta que contiene los archivos de partidos.
     * @return Una lista de equipos.
     * @throws IOException Si hay un error al leer los archivos.
     * @throws IllegalArgumentException Si el nombre del equipo es demasiado corto o vacío.
     */
    public List<Equipo> crearEquiposDesdeArchivos(String rutaCarpetaPartidos) throws IOException {
        List<String> nombresArchivos = LectorJson.leerNombresArchivosJsons(rutaCarpetaPartidos);
        return nombresArchivos.stream()
                .map(this::extraerNombreEquipoDesdeNombreArchivo)
                .map(nombreEquipo -> {
                    if (nombreEquipo.isEmpty()) {
                        throw new IllegalArgumentException("El nombre del equipo está o vacío.");
                    }
                    return new Equipo(nombreEquipo);
                })
                .collect(Collectors.toList());
    }

    /**
     * Extrae el nombre del equipo a partir del nombre del archivo.
     *
     * @param nombreArchivo El nombre del archivo.
     * @return El nombre del equipo.
     */
    private String extraerNombreEquipoDesdeNombreArchivo(String nombreArchivo) {
        String nombreEquipo = nombreArchivo.replaceFirst("^partidos_", "").replaceFirst(".json$", "");
        return nombreEquipo.replaceAll("_", " ");
    }
}
