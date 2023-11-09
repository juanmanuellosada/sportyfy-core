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

public class ResultadoPartidoFactory {
    /**
     * Crea un mapa de Partido y Resultado a partir de archivos JSON.
     *
     * @param rutaCarpetaPartidos La ruta de la carpeta que contiene los archivos
     *                            JSON de partidos.
     * @param objectMapper        El ObjectMapper para la lectura de JSON.
     * @param equipos             El conjunto de equipos.
     * @return Un mapa de Partido y Resultado.
     * @throws IOException Si hay un error al leer los archivos.
     */
    public static Map<Partido, Resultado> crearPartidosResultado(String rutaCarpetaPartidos, ObjectMapper objectMapper,
            Set<Equipo> equipos) throws IOException {
        List<ResultadoPartidoDTO> resultadoPartidosDTO = obtenerResultadoPartidosDTO(rutaCarpetaPartidos, objectMapper);
        List<Partido> partidos = resultadoPartidosDTO.stream()
                .map(dto -> dto.getResultadoPartido().getPartido().toPartido(equipos))
                .collect(Collectors.toList());
        List<Resultado> resultados = resultadoPartidosDTO.stream()
                .map(dto -> dto.getResultadoPartido().getResultado().toResultado(equipos))
                .collect(Collectors.toList());
        return obtenerMapaPartidoResultado(partidos, resultados);
    }

    /**
     * Crea un listado de ResultadoPartidoDTO a partir de archivos JSON.
     *
     * @param rutaCarpetaPartidos La ruta de la carpeta que contiene los archivos
     *                            JSON de partidos.
     * @param objectMapper        El ObjectMapper para la lectura de JSON.
     * @return Un listado de ResultadoPartidoDTO.
     * @throws IOException Si hay un error al leer los archivos.
     */
    private static List<ResultadoPartidoDTO> obtenerResultadoPartidosDTO(String rutaCarpetaPartidos,
            ObjectMapper objectMapper)
            throws IOException {
        List<ResultadoPartidoDTO> resultadoPartidosDTO = new ArrayList<>();
        for (String nombreArchivo : LectorJson.leerNombresArchivosJsons(rutaCarpetaPartidos)) {
            resultadoPartidosDTO.addAll(objectMapper.readValue(
                    new File(rutaCarpetaPartidos + File.separator + nombreArchivo),
                    new TypeReference<>() {
                    }));
        }
        return resultadoPartidosDTO;
    }

    /**
     * Crea un mapa de Partido y Resultado a partir de listas de partidos y
     * resultados.
     *
     * @param partidos   El listado de partidos.
     * @param resultados El listado de resultados.
     * @return Un mapa de Partido y Resultado.
     */
    private static Map<Partido, Resultado> obtenerMapaPartidoResultado(List<Partido> partidos,
            List<Resultado> resultados) {
        Map<Partido, Resultado> partidosResultado = new HashMap<>();
        for (int i = 0; i < partidos.size(); i++) {
            Partido partido = partidos.get(i);
            Resultado resultado = resultados.get(i);
            if (resultado.getMarcador(partido.getLocal()).isPresent()
                    && resultado.getMarcador(partido.getVisitante()).isPresent()) {
                partidosResultado.put(partido, resultado);
            }
        }
        return partidosResultado;
    }
}