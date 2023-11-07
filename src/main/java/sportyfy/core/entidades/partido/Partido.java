package sportyfy.core.entidades.partido;

import java.util.Objects;
import java.util.Optional;

import lombok.Data;
import sportyfy.core.entidades.determinadorGanador.DeterminadorGanador;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.resultado.Resultado;

/**
 * Entidad que representa un partido en el sistema.
 */
@Data
public class Partido {

    /**
     * Variable que representa el resultado del partido.
     * 
     * @see Resultado
     */
    private Resultado resultado = new Resultado();

    /**
     * Constructor de la clase Partido.
     * 
     * @param local             Equipo local del partido.
     * @param visitante         Equipo visitante del partido.
     * @param marcadorLocal     Marcador del equipo local.
     * @param marcadorVisitante Marcador del equipo visitante.
     */
    public Partido(Equipo local, Equipo visitante, int marcadorLocal, int marcadorVisitante) {
        resultado.getMarcadorPorEquipo().put(local, marcadorLocal);
        resultado.getMarcadorPorEquipo().put(visitante, marcadorVisitante);
    }

    /**
     * Método que devuelve el equipo en la posición index del partido.
     *
     * @param index Posición del equipo en el partido.
     * @return Equipo en la posición index del partido.
     * @throws IllegalArgumentException si el partido no tiene la cantidad adecuada
     *                                  de equipos o contiene equipos nulos.
     * @see Equipo
     */
    private Optional<Equipo> getEquipo(int index) {
        return resultado.getMarcadorPorEquipo().keySet().stream()
                .skip(index)
                .findFirst();
    }

    /**
     * Método que devuelve el equipo local del partido.
     *
     * @return Equipo local del partido.
     * @see Equipo
     */
    public Optional<Equipo> getLocal() {
        return getEquipo(0);
    }

    /**
     * Método que devuelve el equipo visitante del partido.
     *
     * @return Equipo visitante del partido.
     * @see Equipo
     */
    public Optional<Equipo> getVisitante() {
        return getEquipo(1);
    }

    /**
     * Método que devuelve si el partido tiene marcadores. En caso de no tenerlo
     * sería un partido a pronosticar.
     * 
     * @return true si el partido tiene marcadores, false en caso contrario.
     */
    public boolean tieneMarcadores() {
        return getLocal().isPresent() && getVisitante().isPresent() &&
                Objects.nonNull(resultado.getMarcadorPorEquipo().get(getLocal().get())) &&
                Objects.nonNull(resultado.getMarcadorPorEquipo().get(getVisitante().get()));
    }

    /**
     * Método que devuelve el ganador del partido.
     * 
     * @return El ganador del partido.
     */
    public Optional<Equipo> getGanador() {
        return new DeterminadorGanador().determinarGanador(this);
    }

    /**
     * Método que devuelve si el partido es un empate.
     * 
     * @return true si el partido es un empate, false en caso contrario.
     */
    public boolean esEmpate() {
        return getGanador().isEmpty();
    }

    /**
     * Método que devuelve el marcador del equipo local.
     * 
     * @return Marcador del equipo local.
     */
    public Integer getMarcadorLocal() {
        return resultado.getMarcador(getLocal().orElse(null));
    }

    /**
     * Método que devuelve el marcador del equipo visitante.
     * 
     * @return Marcador del equipo visitante.
     */
    public Integer getMarcadorVisitante() {
        return resultado.getMarcador(getVisitante().orElse(null));
    }

    /**
     * Método que establece el equipo local del partido.
     *
     * @param local Equipo local del partido.
     */
    public void setLocal(Equipo local) {
        resultado.getMarcadorPorEquipo().put(local, 0);
    }

    /**
     * Método que establece el equipo visitante del partido.
     *
     * @param visitante Equipo visitante del partido.
     */
    public void setVisitante(Equipo visitante) {
        resultado.getMarcadorPorEquipo().put(visitante, 0);
    }

    /**
     * Método que establece el marcador del equipo local.
     *
     * @param marcador Marcador del equipo local.
     */
    public void setMarcadorLocal(Integer marcador) {
        resultado.getMarcadorPorEquipo().put(getLocal().orElse(null), marcador);
    }

    /**
     * Método que establece el marcador del equipo visitante.
     *
     * @param marcador Marcador del equipo visitante.
     */
    public void setMarcadorVisitante(Integer marcador) {
        resultado.getMarcadorPorEquipo().put(getVisitante().orElse(null), marcador);
    }
}
