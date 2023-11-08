package sportyfy.core.servicios.factorys;

import sportyfy.core.DTOs.PartidoDTO;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;
import sportyfy.core.servicios.buscadores.BuscadorEquipos;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Map;

/**
 * Clase que se encarga de crear los partidos a partir de los archivos de
 * partidos.
 */
public class PartidosFactory {
        /**
         * Crea los partidos a partir de los archivos de partidos.
         *
         * @param partidoDTO El DTO del partido.
         * @param equipos    El Set de equipos.
         * @return Un partido.
         */
        public static Partido crearPartido(PartidoDTO partidoDTO, Set<Equipo> equipos) {
                Map<String, Integer> marcadorPorEquipo = partidoDTO.getPartido().getMarcadorPorEquipo();

                List<String> nombresEquipos = new ArrayList<>(marcadorPorEquipo.keySet());

                return new Partido(
                                BuscadorEquipos.encontrarEquipoPorNombre(nombresEquipos.get(0), equipos)
                                                .orElseThrow(() -> new IllegalArgumentException(
                                                                "No se encontró el equipo local")),
                                BuscadorEquipos.encontrarEquipoPorNombre(nombresEquipos.get(1), equipos)
                                                .orElseThrow(() -> new IllegalArgumentException(
                                                                "No se encontró el equipo visitante")));
        }
}
