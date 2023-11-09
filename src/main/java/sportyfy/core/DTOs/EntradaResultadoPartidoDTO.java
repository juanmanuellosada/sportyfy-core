package sportyfy.core.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
/**
 * DTO para la entrada de un resultado de un partido.
 */
public class EntradaResultadoPartidoDTO {
    private PartidoDTO partido;
    private ResultadoDTO resultado;
}