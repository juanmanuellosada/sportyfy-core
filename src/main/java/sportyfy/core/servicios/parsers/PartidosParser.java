package sportyfy.core.servicios.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;
import sportyfy.core.servicios.factorys.PartidosFactory;
import sportyfy.core.servicios.lectores.LectorJson;

import java.io.IOException;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Clase que se encarga de crear los partidos a partir de los archivos de
 * partidos.
 */

@Data
public class PartidosParser {

        /**
         * Lista de equipos.
         */
        private final Set<Equipo> equipos;

        /**
         * Crea los partidos a partir de un directorio que contiene archivos JSON de
         * partidos.
         *
         * @param rutaCarpeta  La ruta de la carpeta que contiene los archivos JSON de
         *                     partidos.
         * @param objectMapper El ObjectMapper.
         * @return Un set de partidos.
         * @throws IOException Si hay un error al leer los archivos.
         */
        public static Set<Partido> crearPartidos(String rutaCarpeta, ObjectMapper objectMapper, Set<Equipo> equipos)
                        throws IOException {
                File directorio = new File(rutaCarpeta);
                File[] archivos = directorio.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

                return archivos != null
                                ? Arrays.stream(archivos)
                                                .map(archivo -> LectorJson.leerPartidos(archivo.getAbsolutePath(),
                                                                objectMapper))
                                                .filter(Optional::isPresent)
                                                .map(Optional::get)
                                                .flatMap(Collection::stream)
                                                .map(partidoDTO -> PartidosFactory.crearPartido(partidoDTO, equipos))
                                                .collect(Collectors.toSet())
                                : Collections.emptySet();
        }
}
