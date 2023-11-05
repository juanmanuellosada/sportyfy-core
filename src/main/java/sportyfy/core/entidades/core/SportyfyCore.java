package sportyfy.core.entidades.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import sportyfy.core.entidades.Pronosticador;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;
import sportyfy.core.servicios.buscadores.BuscadorPronosticadores;

import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Set;

/**
 * La clase SportyfyCore representa el núcleo del sistema Sportyfy. Se trata de
 * mi modelo.
 * Contiene información sobre pronosticadores, equipos, partidos y el
 * partido pronosticado actual.
 * Esta clase también proporciona la funcionalidad de pronosticar partidos
 * y notificar a los observadores cuando cambia el pronóstico actual.
 *
 * @see Pronosticador
 * @see Equipo
 * @see Partido
 * @see BuscadorPronosticadores
 */
@Data
@AllArgsConstructor
public class SportyfyCore {

    /**
     * Soporte para el patrón Observer
     */
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private Set<Pronosticador> pronosticadores;
    private List<Equipo> equipos;
    private List<Partido> partidos;

    /**
     * Pronostica un partido
     *
     * @param partido            Partido a pronosticar
     * @param nombrePronosticador Nombre del pronosticador
     * @throws IllegalArgumentException Si no se encuentra el pronosticador
     */
    public void pronosticar(Partido partido, String nombrePronosticador) {
        Pronosticador pronosticador = new BuscadorPronosticadores().buscarPronosticador(pronosticadores,
                nombrePronosticador);
        if (pronosticador == null) {
            throw new IllegalArgumentException("No se encontró el pronosticador");
        }

        pronosticador.setPartidos(Set.copyOf(partidos));
        pronosticador.pronosticar(partido);

        // Notificar a los observadores
        support.firePropertyChange("partido", null, partido);

    }
}
