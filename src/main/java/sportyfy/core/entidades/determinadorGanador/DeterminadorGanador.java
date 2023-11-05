package sportyfy.core.entidades.determinadorGanador;

import java.util.Optional;

import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;

/**
 * Clase que se encarga de determinar el ganador de un partido.
 */
public class DeterminadorGanador {
    /**
     * Determina el ganador de un partido.
     * @param partido El partido.
     * @return El equipo ganador.
     */
    public Optional<Equipo> determinarGanador(Partido partido) {
        if (partido.tieneMarcadores()) {
            int marcadorLocal = partido.getMarcadorLocal();
            int marcadorVisitante = partido.getMarcadorVisitante();

            return Optional.ofNullable(marcadorLocal == marcadorVisitante ? null
                    : (marcadorLocal > marcadorVisitante ? partido.getLocal() : partido.getVisitante()));
        }
        return Optional.empty();
    }
}