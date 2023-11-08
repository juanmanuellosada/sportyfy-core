package sportyfy.core.servicios.iniciadores;

import sportyfy.core.entidades.core.SportyfyCore;
import sportyfy.core.servicios.buscadores.BuscadorPronosticadores;

import java.io.IOException;

import lombok.Getter;

import java.util.logging.Logger;

/**
 * Clase que se encarga de inicializar el núcleo del sistema Sportyfy.
 */
@Getter
public class IniciadorSportyfyCore {
        private static final Logger logger = Logger.getLogger(IniciadorSportyfyCore.class.getName());

        public static SportyfyCore iniciar(String rutaPronosticadores) {
                try {
                        return new SportyfyCore(
                                        new BuscadorPronosticadores().buscarPronosticadores(rutaPronosticadores));
                } catch (IOException e) {
                        logger.severe("Error al leer el fichero de pronosticadores");
                        throw new RuntimeException("Error al iniciar SportyfyCore", e);
                }
        }
}