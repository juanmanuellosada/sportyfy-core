package sportyfy.core.servicios.iniciadores;

import sportyfy.core.entidades.Pronosticador;
import sportyfy.core.entidades.core.SportyfyCore;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;
import sportyfy.core.servicios.buscadores.BuscadorPronosticadores;
import sportyfy.core.servicios.lectores.LectorProperties;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.Optional;

/**
 * Clase que se encarga de inicializar el núcleo del sistema Sportyfy.
 */
public class IniciadorSportyfyCore {

        private final Optional<String> rutaCarpetaPartidos = LectorProperties.leerProperties(
                        "rutaCarpetaPartidosJugados",
                        "src/main/resources/datos/partidos");

        /**
         * Inicializa el núcleo del sistema Sportyfy.
         *
         * @param rutaPronosticadores La ruta de la carpeta de pronosticadores.
         * @return El núcleo del sistema Sportyfy.
         * @throws IOException Si ocurre un error al leer los archivos de partidos.
         */
        @SuppressWarnings("unchecked")
        public SportyfyCore iniciar(String rutaPronosticadores) throws IOException {
                String rutaCarpeta = rutaCarpetaPartidos.orElseThrow(
                                () -> new IOException("No se pudo leer la ruta de la carpeta de partidos"));

                List<Equipo> equipos = (List<Equipo>) IniciadorEquiposPartidos.iniciar(rutaCarpeta,
                                IniciadorEquiposPartidos.TipoInicializacion.EQUIPOS, null)
                                .orElseThrow(() -> new IOException("No se pudo inicializar los equipos"));

                Set<Partido> partidos = (Set<Partido>) IniciadorEquiposPartidos.iniciar(rutaCarpeta,
                                IniciadorEquiposPartidos.TipoInicializacion.PARTIDOS, equipos)
                                .orElseThrow(() -> new IOException("No se pudo inicializar los partidos"));

                Set<Pronosticador> pronosticadores = new BuscadorPronosticadores()
                                .buscarPronosticadores(rutaPronosticadores);

                return new SportyfyCore(pronosticadores, equipos, partidos);
        }
}
