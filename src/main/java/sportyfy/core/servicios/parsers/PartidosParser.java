package sportyfy.core.servicios.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;
import sportyfy.core.DTOs.PartidoDTO;
import sportyfy.core.servicios.factorys.PartidosFactory;
import sportyfy.core.servicios.lectores.LectorJson;

import java.io.IOException;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Clase que se encarga de crear los partidos a partir de los archivos de
 * partidos.
 */

@Data
public class PartidosParser {
    private final List<Equipo> equipos;

    /**
     * Crea los partidos a partir de un directorio que contiene archivos JSON de
     * partidos.
     *
     * @param rutaCarpeta  La ruta de la carpeta que contiene los archivos JSON de
     *                     partidos.
     * @param objectMapper El ObjectMapper.
     * @return Una lista de partidos.
     * @throws IOException Si hay un error al leer los archivos.
     */
    public static List<Partido> crearPartidos(String rutaCarpeta, ObjectMapper objectMapper, List<Equipo> equipos)
            throws IOException {
        File directorio = new File(rutaCarpeta);
        File[] archivos = directorio.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        return archivos != null
                ? Arrays.stream(archivos)
                        .flatMap(archivo -> {
                            List<PartidoDTO> partidosDTOs = LectorJson.leerPartidos(archivo.getPath(), objectMapper);
                            return partidosDTOs.stream()
                                    .map(dto -> PartidosFactory.crearPartido(dto, equipos))
                                    .filter(Objects::nonNull);
                        })
                        .collect(Collectors.toList())
                : Collections.emptyList();
    }
}
