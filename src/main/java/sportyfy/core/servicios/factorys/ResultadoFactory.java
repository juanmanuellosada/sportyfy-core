package sportyfy.core.servicios.factorys;

import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.resultado.Resultado;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Clase que se encarga de crear los resultados a partir de los archivos de
 * resultados.
 */
public class ResultadoFactory {
    /**
     * Crea un resultado a partir de un equipo y un marcador.
     *
     * @param local          El equipo local.
     * @param marcadorLocal  El marcador del equipo local.
     * @param visitante      El equipo visitante.
     * @param marcadorVisitante El marcador del equipo visitante.
     * @return Un resultado.
     */
    static Resultado crearResultado(Equipo local, int marcadorLocal, Equipo visitante, int marcadorVisitante) {
        Map<Equipo, Integer> marcadores = new LinkedHashMap<>(Map.of(local, marcadorLocal, visitante, marcadorVisitante));
        return new Resultado(marcadores);
    }
}