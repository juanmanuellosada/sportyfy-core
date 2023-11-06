package sportyfy.core.servicios.iniciadores;

import com.fasterxml.jackson.databind.ObjectMapper;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.servicios.parsers.EquiposParser;
import sportyfy.core.servicios.parsers.PartidosParser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Clase que se encarga de inicializar los equipos y los partidos.
 */
public class IniciadorEquiposPartidos {
    /**
     * Enumeración que representa los tipos de inicialización que se pueden realizar.
     */
    public enum TipoInicializacion {
        EQUIPOS,
        PARTIDOS
    }

    /**
     * Inicializa los equipos y los partidos.
     *
     * @param rutaCarpetaPartidosJugados La ruta de la carpeta que contiene los archivos de partidos.
     * @param tipoInicializacion         El tipo de inicialización.
     * @param equipos                    La lista de equipos.
     * @return Una lista de equipos o de partidos.
     * @throws IOException Si hay un error al leer los archivos.
     */
    public static List<?> iniciar(String rutaCarpetaPartidosJugados, TipoInicializacion tipoInicializacion, List<Equipo> equipos) throws IOException {
        if (tipoInicializacion == TipoInicializacion.EQUIPOS) {
            return new EquiposParser().crearEquiposDesdeArchivos(rutaCarpetaPartidosJugados);
        } else if (tipoInicializacion == TipoInicializacion.PARTIDOS) {
            Path rutaCarpeta = Path.of(rutaCarpetaPartidosJugados);
            return new PartidosParser(equipos).crearPartidos(rutaCarpeta, new ObjectMapper());
        } else {
            throw new IllegalArgumentException("Tipo de inicialización no válido: " + tipoInicializacion);
        }
    }

}
