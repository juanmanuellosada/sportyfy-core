package sportyfy.core.servicios.lectores;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import sportyfy.core.DTOs.PartidoDTO;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public static List<PartidoDTO> leerPartidos(Path rutaArchivo, ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(rutaArchivo.toFile(), new TypeReference<List<PartidoDTO>>() {});
        } catch (IOException e) {
            logger.severe("Error al leer el archivo " + rutaArchivo);
            return null;
        }
    }

    /**
     * Lee los nombres de los archivos JSON desde una carpeta y los devuelve como una lista de cadenas.
     *
     * @param carpeta La carpeta que contiene los archivos JSON.
     * @return Una lista de nombres de archivos JSON.
     * @throws IOException Si hay un error al leer uno de los archivos.
     */
    public static List<String> leerNombresArchivosJsons(Path carpeta) throws IOException {
        try (Stream<Path> paths = Files.walk(carpeta)) {
            return filtrarArchivosJson(paths)
                    .map(t -> t.getFileName().toString())
                    .collect(Collectors.toList());
        }
    }

    private static Stream<Path> filtrarArchivosJson(Stream<Path> paths) {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.json");
        return paths.filter(Files::isRegularFile).filter(matcher::matches);
    }
}
