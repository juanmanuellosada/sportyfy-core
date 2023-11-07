package sportyfy.core.servicios.factorys;

import sportyfy.core.DTOs.PartidoDTO;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;
import sportyfy.core.servicios.buscadores.BuscadorEquipos;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

/**
 * Clase que se encarga de crear los partidos a partir de los archivos de
 * partidos.
 */
public class PartidosFactory {
    /**
     * Crea los partidos a partir de los archivos de partidos.
     *
     * @param partidoDTO El DTO del partido.
     * @param equipos    La lista de equipos.
     * @return Un partido.
     */
    public static Optional<Partido> crearPartido(PartidoDTO partidoDTO, List<Equipo> equipos) {
        Map<String, Integer> marcadorPorEquipo = partidoDTO.getPartido().getMarcadorPorEquipo();
        List<String> nombresEquipos = new ArrayList<>(marcadorPorEquipo.keySet());

        Equipo equipoLocal = BuscadorEquipos.encontrarEquipoPorNombre(nombresEquipos.get(0), equipos)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el equipo local"));
        Equipo equipoVisitante = BuscadorEquipos.encontrarEquipoPorNombre(nombresEquipos.get(1), equipos)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el equipo visitante"));

        Partido partido = new Partido(equipoLocal, equipoVisitante, marcadorPorEquipo.get(nombresEquipos.get(0)),
                marcadorPorEquipo.get(nombresEquipos.get(1)));

        return Optional.of(partido);
    }
}
