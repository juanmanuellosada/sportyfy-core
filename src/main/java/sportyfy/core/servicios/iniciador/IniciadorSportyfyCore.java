package sportyfy.core.servicios.iniciador;

import sportyfy.core.Pronosticador;
import sportyfy.core.entidades.core.SportyfyCore;
import sportyfy.core.servicios.buscadores.BuscadorPronosticadores;

import java.io.IOException;

import lombok.Getter;
import sportyfy.core.servicios.lectores.LectorProperties;

import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static sportyfy.apiFootball.GeneradorJsons.generarJsons;

/**
 * Clase que se encarga de inicializar el núcleo del sistema Sportyfy.
 */
@Getter
public class IniciadorSportyfyCore {
        private static final Logger logger = Logger.getLogger(IniciadorSportyfyCore.class.getName());

        public IniciadorSportyfyCore(boolean generar) {
                if (generar) {
                        String rutaPartidos = String.valueOf(
                                        LectorProperties.leerProperties("rutaPartidos",
                                                        "src/main/resources/rutas.properties"));
                        String rutaEquipos = String.valueOf(
                                        LectorProperties.leerProperties("rutaEquipos",
                                                        "src/main/resources/rutas.properties"));
                        generarJsons(rutaPartidos, rutaEquipos);
                }
        }

        public SportyfyCore iniciar(String rutaPronosticadores, String rutaCarpetaPartidos) {
                try {
                        Set<Pronosticador> pronosticadores = new BuscadorPronosticadores()
                                        .buscarPronosticadores(rutaPronosticadores).stream()
                                        .peek(pronosticador -> pronosticador.iniciar(rutaCarpetaPartidos))
                                        .collect(Collectors.toSet());
                        return new SportyfyCore(pronosticadores);
                } catch (IOException e) {
                        logger.severe("Error al leer el fichero de pronosticadores");
                        throw new RuntimeException("Error al iniciar SportyfyCore", e);
                }
        }
}