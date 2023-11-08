package sportyfy.core.servicios.factorys;

import sportyfy.core.DTOs.PartidoDTO;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;

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
        public static Partido crearPartido(PartidoDTO partidoDTO) {
                return new Partido(
                                new Equipo(partidoDTO.getLocal().getNombre()),
                                new Equipo(partidoDTO.getVisitante().getNombre()));
        }
}
