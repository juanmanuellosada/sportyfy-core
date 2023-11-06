package sportyfy.core.servicios.lectores;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Clase que se encarga de leer archivos de propiedades.
 */
public class LectorProperties {

    private static final Logger logger = Logger.getLogger(LectorProperties.class.getName());

    /**
     * Lee un archivo de propiedades y devuelve el valor de una propiedad.
     *
     * @param propertie   La propiedad que se quiere leer.
     * @param rutaArchivo La ruta del archivo de propiedades.
     * @return El valor de la propiedad.
     */
    public static String leerProperties(String propertie, String rutaArchivo) {
        Properties prop = new Properties();
        FileInputStream input = null;

        try {
            input = new FileInputStream(rutaArchivo);
            prop.load(input);
        } catch (IOException e) {
            logger.severe("Error al leer el archivo de propiedades: " + e.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.warning("No se pudo cerrar el archivo de propiedades: " + e.getMessage());
                }
            }
        }

        return prop.getProperty(propertie);
    }

}