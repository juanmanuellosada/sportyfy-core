package sportyfy.core.servicios.lectores;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import sportyfy.core.DTOs.EntradaResultadoPartidoDTO;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Clase que permite leer archivos JSON.
 */
public class LectorJson {

    private static final Logger logger = Logger.getLogger(LectorJson.class.getName());

    public static List<JsonNode> leerArchivoJSON(File archivo, ObjectMapper objectMapper) throws IOException {
        // Lee el archivo JSON y conviértelo en un objeto JsonNode
        JsonNode jsonNode = objectMapper.readTree(archivo);

        // Si el nodo es un array, convierte cada elemento en una lista de JsonNode
        if (jsonNode.isArray()) {
            return objectMapper.readerFor(new TypeReference<List<JsonNode>>() {})
                    .readValue(jsonNode);
        } else {
            // Si el nodo no es un array, simplemente crea una lista con ese nodo
            return Collections.singletonList(jsonNode);
        }
    }
}
