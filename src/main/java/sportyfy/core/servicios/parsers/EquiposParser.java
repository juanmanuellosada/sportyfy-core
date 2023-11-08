package sportyfy.core.servicios.parsers;

import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.servicios.lectores.LectorJson;

import java.io.IOException;
import java.util.Set;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Clase que se encarga de crear los equipos a partir de los nombres de los
 * archivos de partidos.
 */
public class EquiposParser {
    /**
     * Crea los equipos a partir de los nombres de los archivos de partidos.
     *
     * @param rutaCarpetaPartidos La ruta de la carpeta que contiene los archivos de
     *                            partidos.
     * @return Un set de equipos.
     * @throws IOException              Si hay un error al leer los archivos.
     * @throws IllegalArgumentException Si el nombre del equipo es demasiado corto o
     *                                  vacío.
     */
    public Set<Equipo> crearEquiposDesdeArchivos(String rutaCarpetaPartidos) throws IOException {
        List<String> nombresArchivos = LectorJson.leerNombresArchivosJsons(rutaCarpetaPartidos);
        return nombresArchivos.stream()
                .map(this::extraerNombreEquipoDesdeNombreArchivo)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Equipo::new)
                .collect(Collectors.toSet());
    }

    /**
     * Extrae el nombre del equipo a partir del nombre del archivo.
     *
     * @param nombreArchivo El nombre del archivo.
     * @return El nombre del equipo.
     */
    private Optional<String> extraerNombreEquipoDesdeNombreArchivo(String nombreArchivo) {
        String nombreEquipo = nombreArchivo.replace("partidos_", "").replace(".json", "").replace("_", " ");
        return nombreEquipo.isEmpty() ? Optional.empty() : Optional.of(nombreEquipo);
    }
}
