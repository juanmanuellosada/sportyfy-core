package sportyfy.core.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
/**
 * DTO para la entrada de un resultado de un partido.
 */
public class PartidoDTO {
    private EquipoDTO local;
    private EquipoDTO visitante;

    /**
     * Convierte el DTO a una entidad Partido.
     * 
     * @param equipos El conjunto de equipos.
     * @throws IllegalArgumentException Si no se encuentra el equipo con el nombre o
     *                                  si el nombre es nulo o vacío.
     * @return La entidad Partido.
     */
    public Partido toPartido(Set<Equipo> equipos) {
        if (this.local == null || this.visitante == null) {
            throw new IllegalArgumentException("Los equipos local y visitante no pueden ser nulos");
        }

        return new Partido(
                this.local.toEquipo(equipos),
                this.visitante.toEquipo(equipos));
    }

}