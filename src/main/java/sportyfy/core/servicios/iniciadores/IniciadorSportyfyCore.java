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

/**
 * Clase que se encarga de inicializar el núcleo del sistema Sportyfy.
 */
public class IniciadorSportyfyCore {

    private final String rutaCarpetaPartidos = LectorProperties.leerProperties("rutaCarpetaPartidosJugados", "src/main/resources/datos/partidos");

    /**
     * Inicializa el núcleo del sistema Sportyfy.
     *
     * @param rutaPronosticadores La ruta de la carpeta de pronosticadores.
     * @return El núcleo del sistema Sportyfy.
     * @throws IOException Si ocurre un error al leer los archivos de partidos.
     */
    @SuppressWarnings("unchecked")
    public SportyfyCore iniciar(String rutaPronosticadores) throws IOException {
        List<Equipo> equipos = (List<Equipo>) IniciadorEquiposPartidos.iniciar(rutaCarpetaPartidos, IniciadorEquiposPartidos.TipoInicializacion.EQUIPOS, null);
        List<Partido> partidos = (List<Partido>) IniciadorEquiposPartidos.iniciar(rutaCarpetaPartidos, IniciadorEquiposPartidos.TipoInicializacion.PARTIDOS, equipos);
        Set<Pronosticador> pronosticadores = new BuscadorPronosticadores().buscarPronosticadores(rutaPronosticadores);

        return new SportyfyCore(pronosticadores, equipos, partidos);
    }
}
