package sportyfy.core.entidades.determinadorGanador;

import java.util.Objects;
import java.util.Optional;

import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;

/**
 * Clase que se encarga de determinar el ganador de un partido.
 */
public class DeterminadorGanador {
    /**
     * Determina el ganador de un partido.
     * 
     * @param partido El partido.
     * @return El equipo ganador.
     */
    public Optional<Equipo> determinarGanador(Partido partido) {
        return partido.tieneMarcadores() && !Objects.equals(partido.getMarcadorLocal(), partido.getMarcadorVisitante())
                ? Optional.of(partido.getMarcadorLocal() > partido.getMarcadorVisitante() ? partido.getLocal() : partido.getVisitante())
                : Optional.empty();
    }
}