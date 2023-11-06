package sportyfy.core.servicios.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;
import sportyfy.core.DTOs.PartidoDTO;
import sportyfy.core.servicios.factorys.PartidosFactory;
import sportyfy.core.servicios.lectores.LectorJson;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Clase que se encarga de crear los partidos a partir de los archivos de partidos.
 */

@Data
public class PartidosParser {
    private final List<Equipo> equipos;
    /**
     * Crea los partidos a partir de un directorio que contiene archivos JSON de partidos.
     *
     * @param rutaCarpeta La ruta de la carpeta que contiene los archivos JSON de partidos.
     * @param objectMapper El ObjectMapper.
     * @return Una lista de partidos.
     * @throws IOException Si hay un error al leer los archivos.
     */
    public static List<Partido> crearPartidos(Path rutaCarpeta, ObjectMapper objectMapper, List<Equipo> equipos) throws IOException {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(rutaCarpeta, "*.json")) {
            return StreamSupport.stream(directoryStream.spliterator(), false)
                    .map(archivo -> {
                        return LectorJson.leerPartidos(archivo, objectMapper).stream()
                                .map(dto -> PartidosFactory.crearPartido(dto, equipos))
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList());
                    })
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        }
    }
}
