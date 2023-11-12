package sportyfy.core.DTOs;

import lombok.*;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.resultado.Resultado;
import sportyfy.core.servicios.buscadores.BuscadorEquipos;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResultadoDTO {
    private static LinkedHashMap<String, Integer> marcadorPorEquipo;

    /**
     * Método Mapea un DTO a una entidad.
     *
     * @return El resultado.
     */
    public Resultado toResultado() {
        if (marcadorPorEquipo == null || marcadorPorEquipo.size() != 2) {
            throw new IllegalArgumentException("El marcador por equipo debe contener exactamente dos entradas");
        }

        LinkedHashMap<Equipo, Integer> marcador = new LinkedHashMap<>();
        marcador.put(new Equipo(marcadorPorEquipo.keySet().toArray()[0].toString()),
                marcadorPorEquipo.get(marcadorPorEquipo.keySet().toArray()[1].toString()));

        marcador.put(new Equipo(marcadorPorEquipo.keySet().toArray()[1].toString()),
                marcadorPorEquipo.get(marcadorPorEquipo.keySet().toArray()[0].toString()));

        return new Resultado(marcador);
    }

    /**
     * Método que devuelve el marcador por equipo.
     * 
     * @return El marcador por equipo.
     */
    public Map<String, Integer> getMarcadorPorEquipo() {
        return marcadorPorEquipo;
    }

    public void setMarcadorPorEquipo(LinkedHashMap<String, Integer> marcadorPorEquipo) {
        ResultadoDTO.marcadorPorEquipo = marcadorPorEquipo;
    }

}
