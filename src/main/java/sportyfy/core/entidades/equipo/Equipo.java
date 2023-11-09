package sportyfy.core.entidades.equipo;

import lombok.*;

/**
 * Entidad que representa un equipo en el sistema
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Equipo {
    /**
     * Nombre del equipo
     */
    private final String nombre;
}