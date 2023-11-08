package sportyfy.core.entidades.equipo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa un equipo en el sistema
 */
@Getter
@Setter
@AllArgsConstructor
public class Equipo {
    /**
     * Nombre del equipo
     */
    private final String nombre;
}