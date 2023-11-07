package sportyfy.core.servicios.lectores;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import sportyfy.core.DTOs.PartidoDTO;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Clase que permite leer archivos JSON.
 */
public class LectorJson {

    private static final Logger logger = Logger.getLogger(LectorJson.class.getName());

    /**
     * Método que lee todos los archivos JSON de una carpeta.
     *
     * @param rutaArchivo Ruta del archivo que contiene los partidos.
     * @return Lista de partidos.
     */
    public static Optional<List<PartidoDTO>> leerPartidos(String rutaArchivo, ObjectMapper objectMapper) {
        try {
            List<PartidoDTO> partidos = objectMapper.readValue(new File(rutaArchivo),
                    new TypeReference<List<PartidoDTO>>() {
                    });
            return Optional.of(partidos);
        } catch (IOException e) {
            logger.severe("Error al leer el archivo " + rutaArchivo + ": " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Lee los nombres de los archivos JSON desde una carpeta y los devuelve como
     * una lista de cadenas.
     *
     * @param rutaCarpeta La carpeta que contiene los archivos JSON.
     * @return Una lista de nombres de archivos JSON.
     */
    public static List<String> leerNombresArchivosJsons(String rutaCarpeta) {
        return Arrays.stream(Objects.requireNonNull(new File(rutaCarpeta).listFiles((dir, name) -> name.toLowerCase().endsWith(".json"))))
                .map(File::getName)
                .collect(Collectors.toList());
    }
}
