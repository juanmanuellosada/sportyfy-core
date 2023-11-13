package sportyfy.core.servicios.factorys;

import sportyfy.core.entidades.equipo.Equipo;

/**
 * Clase que se encarga de crear los equipos a partir de los archivos de
 * equipos.
 */
public  class EquipoFactory {

    /**
     * Crea un equipo a partir de un nombre.
     *
     * @param nombre El nombre del equipo.
     * @return Un equipo.
     */
    public static Equipo crearEquipo(String nombre) {
        return new Equipo(nombre);
    }
}