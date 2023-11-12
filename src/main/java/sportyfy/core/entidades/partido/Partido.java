package sportyfy.core.entidades.partido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.resultado.Resultado;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Clase que representa un partido en el sistema.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Partido {

    private Equipo local;
    private Equipo visitante;

    /**
     * Método que devuelve el equipo ganador del partido.
     * 
     * @param resultado El resultado del partido.
     * @return El equipo ganador del partido.
     */
    public Optional<Equipo> getGanador(Resultado resultado) {
        if (resultado.getMarcador(local).isEmpty() || resultado.getMarcador(visitante).isEmpty()) {
            throw new IllegalArgumentException("El resultado debe corresponder a este partido.");
        }

        return Stream.of(local, visitante)
                .max(Comparator.comparing(equipo -> resultado.getMarcador(equipo).orElse(0)));
    }

    public boolean participa(Equipo equipo) {
        return local.equals(equipo) || visitante.equals(equipo);
    }
}
