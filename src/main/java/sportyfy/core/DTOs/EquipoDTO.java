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
     * @param equipos El conjunto de equipos.
     * @throws IllegalArgumentException Si no se encuentra el equipo con el nombre o
     *                                  si el nombre es nulo o vacío.
     * @return La entidad Equipo.
     */
    public Equipo toEquipo(Set<Equipo> equipos) {
        if (this.nombre == null || this.nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del equipo no puede ser nulo o vacío");
        }

        return equipos.stream()
                .filter(equipo -> equipo.getNombre().equals(this.nombre))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("No se encontró el equipo con el nombre: " + this.nombre));
    }
}