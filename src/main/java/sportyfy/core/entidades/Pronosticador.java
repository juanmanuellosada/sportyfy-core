package sportyfy.core.entidades;

import sportyfy.core.entidades.partido.Partido;

import java.util.Set;

/**
 * Interfaz que representa un pronosticador.
 */
public interface Pronosticador {

    public void setPartidos(Set<Partido> partidos);

    /**
     * Pronostica un partido.
     *
     * @param partido El partido.
     */
    public void pronosticar(Partido partido);

    /**
     * Devuelve el deporte del pronosticador.
     *
     * @return El deporte del pronosticador.
     */
    public String getDeporte();
}
