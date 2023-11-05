package sportyfy.core.DTOs;

import lombok.Data;

import java.util.Map;

@Data
public class ResultadoDTO {
    private Map<String, Integer> marcadorPorEquipo;
}
