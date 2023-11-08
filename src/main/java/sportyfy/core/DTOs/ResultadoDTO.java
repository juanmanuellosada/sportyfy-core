package sportyfy.core.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ResultadoDTO {
    private Map<String, Integer> marcadorPorEquipo;
}
