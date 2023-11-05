package sportyfy.core.servicios.factorys;

import sportyfy.core.DTOs.PartidoDTO;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;
import sportyfy.core.servicios.buscadores.BuscadorEquipos;

import java.util.List;

/**
 * Clase que se encarga de crear los partidos a partir de los archivos de partidos.
 */
public class PartidosFactory {
    /**
     * Crea los partidos a partir de los archivos de partidos.
     *
     * @param partidoDTO El DTO del partido.
     * @param equipos    La lista de equipos.
     * @return Un partido.
     */
    public static Partido crearPartido(PartidoDTO partidoDTO, List<Equipo> equipos) {
        String nombreLocal = partidoDTO.getPartido().getMarcadorPorEquipo().keySet().stream().findFirst().orElse(null);
        String nombreVisitante = partidoDTO.getPartido().getMarcadorPorEquipo().keySet().stream().skip(1).findFirst().orElse(null);

        Equipo equipoLocal = BuscadorEquipos.encontrarEquipoPorNombre(nombreLocal, equipos);
        Equipo equipoVisitante = BuscadorEquipos.encontrarEquipoPorNombre(nombreVisitante, equipos);

        if (equipoLocal != null && equipoVisitante != null) {
            Partido partido = new Partido();
            partido.setLocal(equipoLocal);
            partido.setVisitante(equipoVisitante);
            partido.setMarcadorLocal(partidoDTO.getPartido().getMarcadorPorEquipo().get(nombreLocal));
            partido.setMarcadorVisitante(partidoDTO.getPartido().getMarcadorPorEquipo().get(nombreVisitante));
            return partido;
        }

        return null;
    }
}
