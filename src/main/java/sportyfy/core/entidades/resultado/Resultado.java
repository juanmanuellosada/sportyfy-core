package sportyfy.core.entidades.resultado;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import sportyfy.core.entidades.equipo.Equipo;

/**
 * Entidad que representa un resultado en el sistema.
 */
@Data
public class Resultado {
    private Map<Equipo, Integer> marcadorPorEquipo = new HashMap<>();

    /**
     * Método que devuelve el marcador del equipo local.
     *
     * @return Marcador del equipo local.
     */
    public Integer getMarcador(Equipo equipo) {
        return marcadorPorEquipo.get(equipo);
    }

}