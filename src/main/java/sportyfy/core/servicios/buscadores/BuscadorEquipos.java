package sportyfy.core.servicios.buscadores;

import sportyfy.core.entidades.equipo.Equipo;

import java.util.Set;
import java.util.Optional;
import java.util.LinkedHashMap;

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
    public static Optional<Equipo> encontrarEquipoPorNombre(String nombreEquipo, Set<Equipo> equipos) {
        return equipos.stream().filter(e -> e.getNombre().equals(nombreEquipo)).findFirst();
    }

    /**
     * Busca un equipo por su índice en el marcador.
     *
     * @param equipos           El conjunto de equipos.
     * @param indice            El índice del equipo.
     * @param marcadorPorEquipo El marcador por equipo.
     * @return El equipo.
     */
    public static Equipo buscarEquipo(Set<Equipo> equipos, int indice,
            LinkedHashMap<String, Integer> marcadorPorEquipo) {
        String nombreEquipo = marcadorPorEquipo.keySet().toArray()[indice].toString();
        return encontrarEquipoPorNombre(nombreEquipo, equipos).orElseThrow(
                () -> new IllegalArgumentException("No se encontró el equipo con el nombre: " + nombreEquipo));
    }
}
