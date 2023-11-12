package sportyfy.core.servicios.factorys;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import sportyfy.core.DTOs.ResultadoPartidoDTO;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;
import sportyfy.core.entidades.resultado.Resultado;
import sportyfy.core.servicios.lectores.LectorJson;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ResultadoPartidoFactory {
    /**
     * Crea un mapa de Partido y Resultado a partir de archivos JSON.
     *
     * @param rutaCarpetaPartidos La ruta de la carpeta que contiene los archivos
     *                            JSON de partidos.
     * @param objectMapper        El ObjectMapper para la lectura de JSON.
     * @return Un mapa de Partido y Resultado.
     * @throws IOException Si hay un error al leer los archivos.
     */
    public static Map<Partido, Resultado> crearPartidosResultado(String rutaCarpetaPartidos, ObjectMapper objectMapper)
            throws IOException {
        Map<Partido, Resultado> partidosResultado = new HashMap<>();

        for (String nombreArchivo : LectorJson.leerNombresArchivosJsons(rutaCarpetaPartidos)) {
            List<ResultadoPartidoDTO> resultados = objectMapper.readValue(
                    new File(rutaCarpetaPartidos + File.separator + nombreArchivo),
                    new TypeReference<>() {
                    });
            for (ResultadoPartidoDTO resultado : resultados) {
                Partido partido = resultado.getResultadoPartido().getPartido().toPartido();
                Resultado resultadoPart = resultado.getResultadoPartido().getResultado().toResultado();
                partidosResultado.put(partido, resultadoPart);
            }
        }

        return partidosResultado;
    }
}