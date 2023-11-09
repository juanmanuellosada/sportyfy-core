package sportyfy.core.servicios.lectores;

import com.fasterxml.jackson.core.type.TypeReference;
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

    /**
     * Método que lee todos los resultados de los partidos desde un archivo JSON.
     *
     * @param rutaArchivo  Ruta del archivo que contiene los partidos.
     * @param objectMapper El ObjectMapper que se encarga de leer el archivo.
     * @return Lista de resultados de los partidos.
     */
    public static List<EntradaResultadoPartidoDTO> leerResultados(String rutaArchivo, ObjectMapper objectMapper) {
        if (rutaArchivo == null || objectMapper == null) {
            throw new IllegalArgumentException("La ruta del archivo y el ObjectMapper no pueden ser nulos");
        }

        try {
            return objectMapper.readValue(new File(rutaArchivo), new TypeReference<>() {
            });
        } catch (IOException e) {
            logger.severe("Error al leer el archivo de resultados.");
            throw new RuntimeException("Error al leer el archivo de resultados.", e);
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
        return Arrays
                .stream(Objects.requireNonNull(
                        new File(rutaCarpeta).listFiles((dir, name) -> name.toLowerCase().endsWith(".json"))))
                .map(File::getName)
                .collect(Collectors.toList());
    }
}
