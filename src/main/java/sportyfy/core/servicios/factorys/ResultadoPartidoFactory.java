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

        List<Resultado> resultadosPartidos = new ArrayList<>();
        List<Partido> partidosList = new ArrayList<>();
        for (String nombreArchivo : LectorJson.leerNombresArchivosJsons(rutaCarpetaPartidos)) {
            List<ResultadoPartidoDTO> resultados = objectMapper.readValue(
                    new File(rutaCarpetaPartidos + File.separator + nombreArchivo),
                    new TypeReference<>() {
                    });
            for (ResultadoPartidoDTO resultado : resultados) {
                Partido partido = resultado.getResultadoPartido().getPartido().toPartido(equipos);
                partidosList.add(partido);
                Resultado resultadoPart = resultado.getResultadoPartido().getResultado().toResultado(equipos);
                resultadosPartidos.add(resultadoPart);
            }
        }

        Map<Partido, Resultado> partidosResultado = getMapPartidoResultado(partidosList, resultadosPartidos);
        return partidosResultado;
    }

    private static Map<Partido, Resultado> getMapPartidoResultado(List<Partido> partidosList,
            List<Resultado> resultadosPartidos) {
        Map<Partido, Resultado> partidosResultado = new HashMap<>();
        for (Partido partido : partidosList) {
            for (Resultado resultado : resultadosPartidos) {
                if (resultado.getMarcador(partido.getLocal()).isPresent()
                        && resultado.getMarcador(partido.getVisitante()).isPresent()) {
                    partidosResultado.put(partido, resultado);
                }
            }
        }
        return partidosResultado;
    }
}