package sportyfy.core.entidades.resultado;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import sportyfy.core.entidades.equipo.Equipo;

/**
 * Entidad que representa un resultado en el sistema.
 */
public class Resultado {

    /**
     * Mapa que contiene el marcador por equipo.
     * Uso LinkedHashMap para mantener el orden de inserción y asegurarme que
     * siempre el primero es el local y el segundo el visitante.
     */
    private Map<Equipo, Integer> marcadorPorEquipo = new LinkedHashMap<>();

    /**
     * Obtengo el marcador por equipo.
     * 
     * @param equipo El equipo del cual quiero obtener el marcador.
     * @return El marcador del equipo.
     */
    public Optional<Integer> getMarcador(Equipo equipo) {
        return Optional.ofNullable(marcadorPorEquipo.get(equipo));
    }
}