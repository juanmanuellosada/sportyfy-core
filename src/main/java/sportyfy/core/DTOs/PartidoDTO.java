package sportyfy.core.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartidoDTO {
    private EquipoDTO local;
    private EquipoDTO visitante;
    private ResultadoDTO resultado;
}