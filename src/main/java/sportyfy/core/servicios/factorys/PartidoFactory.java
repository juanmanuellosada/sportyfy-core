package sportyfy.core.servicios.factorys;

import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;

/**
 * Clase que se encarga de crear los partidos a partir de los archivos de
 * partidos.
 */
public class PartidoFactory {

        /**
         * Crea un partido a partir de un DTO de partido.
         *
         * @param local     El nombre del equipo local.
         * @param visitante El nombre del equipo visitante.
         * @return Un partido.
         */
        static Partido crearPartido(Equipo local, Equipo visitante) {
                return new Partido(local, visitante);
        }

}
