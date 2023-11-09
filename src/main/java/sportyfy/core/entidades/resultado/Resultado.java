package sportyfy.core.entidades.resultado;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sportyfy.core.entidades.equipo.Equipo;

/**
 * Entidad que representa un resultado en el sistema.
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

    /**
     * Seteo el marcador de un equipo.
     *
     * @param equipo     El equipo.
     * @param valor El valor del marcador.
     */
    public void setMarcador(Equipo equipo, Integer valor) {
        marcadorPorEquipo.put(equipo, valor);
    }
}