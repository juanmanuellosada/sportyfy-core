package sportyfy.core.servicios.factorys;

import sportyfy.core.DTOs.PartidoDTO;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;
import sportyfy.core.servicios.buscadores.BuscadorEquipos;

import java.util.Set;

/**
 * Clase que se encarga de crear los partidos a partir de los archivos de
 * partidos.
 */
public class PartidosFactory {
        /**
         * Crea los partidos a partir de los archivos de partidos.
         *
         * @param partidoDTO El DTO del partido.
         *
         * @return Un partido.
         */
        public static Partido crearPartido(PartidoDTO partidoDTO, Set<Equipo> equipos) {
                return new Partido(
                                BuscadorEquipos.encontrarEquipoPorNombre(partidoDTO.getLocal().getNombre(),
                                                equipos)
                                                .orElseThrow(() -> new IllegalArgumentException(
                                                                "No se encontró el equipo local")),
                                BuscadorEquipos.encontrarEquipoPorNombre(
                                                partidoDTO.getVisitante().getNombre(), equipos)
                                                .orElseThrow(() -> new IllegalArgumentException(
                                                                "No se encontró el equipo visitante")));
        }

}
