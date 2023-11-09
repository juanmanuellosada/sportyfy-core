package sportyfy.core.entidades;

import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;
import sportyfy.core.entidades.resultado.Resultado;

import java.util.Set;

/**
 * Interfaz que representa un pronosticador.
 */
public interface Pronosticador {

    /**
     * Método que se ejecuta al iniciar el pronosticador, carga los datos de los
     * partidos.
     * 
     * @throws Exception Si ocurre un error al cargar los datos.
     */
    void iniciar();

    /**
     * Pronostica un partido.
     *
     * @param partido El partido.
     * @return El resultado del partido.
     */
    Resultado pronosticar(Partido partido);

    /**
     * Devuelve el deporte del pronosticador.
     *
     * @return El deporte del pronosticador.
     */
    String getDeporte();

    /**
     * Devuelve los equipos del pronosticador.
     *
     * @return Un set de equipos.
     * 
     */
    Set<Equipo> getEquipos();
}
