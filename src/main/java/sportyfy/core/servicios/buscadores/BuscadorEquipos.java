package sportyfy.core.servicios.buscadores;

import sportyfy.core.entidades.equipo.Equipo;

import java.util.List;
import java.util.Optional;

/**
 * Clase que se encarga de buscar equipos.
 */
public class BuscadorEquipos {
    /**
     * Encuentra un equipo por su nombre.
     *
     * @param nombreEquipo El nombre del equipo.
     * @param equipos      La lista de equipos.
     * @return El equipo.
     */
    public static Optional<Equipo> encontrarEquipoPorNombre(String nombreEquipo, List<Equipo> equipos) {
        return equipos.stream()
                .filter(e -> e.getNombre().equals(nombreEquipo))
                .findFirst();
    }
}