package sportyfy.core.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import sportyfy.core.entidades.equipo.Equipo;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
/**
 * DTO para la entrada de un resultado de un partido.
 */
public class EquipoDTO {
    private String nombre;

    /**
     * Convierte el DTO a una entidad Equipo.
     *
     * @throws IllegalArgumentException Si no se encuentra el equipo con el nombre o
     *                                  si el nombre es nulo o vacío.
     * @return La entidad Equipo.
     */
    public Equipo toEquipo() {
        if (this.nombre == null || this.nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del equipo no puede ser nulo o vacío");
        }

        return new Equipo(this.nombre);
    }
}