package sportyfy.core.servicios.factorys;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;
import sportyfy.core.entidades.resultado.Resultado;
import sportyfy.core.servicios.lectores.LectorJson;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class ResultadoPartidoFactory {

    private static final Logger logger = Logger.getLogger(ResultadoPartidoFactory.class.getName());

    /**
     * Método que crea un mapa de partidos y resultados a partir de una carpeta que contiene archivos JSON.
     *
     * @param rutaCarpetaPartidos Ruta de la carpeta que contiene los archivos JSON.
     * @param objectMapper        ObjectMapper que se utilizará para leer los archivos JSON.
     * @return Mapa de partidos y resultados.
     */
    public static Map<Partido, Resultado> crearPartidosResultado(String rutaCarpetaPartidos, ObjectMapper objectMapper) {
        Map<Partido, Resultado> partidosResultadoMap = new HashMap<>();

        File carpetaPartidos = new File(rutaCarpetaPartidos);

        List<JsonNode> nodosPartidos = Arrays.stream(Objects.requireNonNull(carpetaPartidos.listFiles()))
                .filter(File::isFile)
                .flatMap(archivoPartido -> leerArchivoJSON(archivoPartido, objectMapper).stream())
                .toList();

        nodosPartidos.forEach(nodoPartido -> procesarNodoPartido(nodoPartido, partidosResultadoMap));

        return partidosResultadoMap;
    }

    /**
     * Método que crea una lista de nodos JSON a partir de un archivo JSON.
     * @param archivoPartido Archivo JSON que se va a leer.
     * @param objectMapper ObjectMapper que se utilizará para leer el archivo JSON.
     * @return Lista de nodos JSON.
     */
    private static List<JsonNode> leerArchivoJSON(File archivoPartido, ObjectMapper objectMapper) {
        try {
            return LectorJson.leerArchivoJSON(archivoPartido, objectMapper).stream().toList();
        } catch (IOException e) {
            logger.severe("Error al leer el archivo " + archivoPartido.getName());
            return Collections.emptyList();
        }
    }

    /**
     * Método que procesa un nodo JSON y lo convierte en un partido y un resultado.
     * @param nodoPartido Nodo JSON que se va a procesar.
     * @param partidosResultadoMap Mapa de partidos y resultados.
     */
    private static void procesarNodoPartido(JsonNode nodoPartido, Map<Partido, Resultado> partidosResultadoMap) {
        JsonNode resultadoPartido = nodoPartido.get("resultadoPartido");
        JsonNode resultado = resultadoPartido.get("resultado");
        JsonNode marcadorPorEquipo = resultado.get("marcadorPorEquipo");

        Iterator<Map.Entry<String, JsonNode>> fields = marcadorPorEquipo.fields();
        if (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String nombreEquipoLocal = entry.getKey();
            Equipo local = EquipoFactory.crearEquipo(nombreEquipoLocal);
            int marcadorLocal = entry.getValue().asInt();

            if (fields.hasNext()) {
                Map.Entry<String, JsonNode> siguienteEntry = fields.next();
                String nombreEquipoVisitante = siguienteEntry.getKey();
                Equipo visitante = EquipoFactory.crearEquipo(nombreEquipoVisitante);
                int marcadorVisitante = siguienteEntry.getValue().asInt();

                Partido partidoActual = PartidoFactory.crearPartido(local, visitante);
                Resultado resultadoActual = ResultadoFactory.crearResultado(local, marcadorLocal, visitante, marcadorVisitante);

                partidosResultadoMap.computeIfAbsent(partidoActual, k -> resultadoActual);
            }
        }
    }
}