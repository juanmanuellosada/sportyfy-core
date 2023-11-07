package sportyfy.core.entidades;

import sportyfy.core.entidades.partido.Partido;

import java.util.Set;

/**
 * Interfaz que representa un pronosticador.
 */
public interface Pronosticador {

    void setPartidos(Set<Partido> partidos);

    /**
     * Pronostica un partido.
     *
     * @param partido El partido.
     */
    void pronosticar(Partido partido);

    /**
     * Devuelve el deporte del pronosticador.
     *
     * @return El deporte del pronosticador.
     */
    String getDeporte();
}
