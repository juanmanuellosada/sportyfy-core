package sportyfy.core.entidades.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import sportyfy.core.entidades.Pronosticador;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;
import sportyfy.core.entidades.resultado.Resultado;
import sportyfy.core.servicios.buscadores.BuscadorPronosticadores;

import java.beans.PropertyChangeSupport;
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
    private final PropertyChangeSupport notificador = new PropertyChangeSupport(this);

    private Set<Pronosticador> pronosticadores;

    /**
     * Pronostica un partido
     *
     * @param partido             Partido a pronosticar
     * @param nombrePronosticador Nombre del pronosticador
     * @throws IllegalArgumentException Si no se encuentra el pronosticador
     */
    public Resultado pronosticar(Partido partido, String nombrePronosticador) {
        Pronosticador pronosticador = new BuscadorPronosticadores()
                .buscarPronosticador(pronosticadores, nombrePronosticador)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el pronosticador"));

        Resultado resultado = pronosticador.pronosticar(partido);

        notificador.firePropertyChange("resultado", null, resultado);
        return resultado;
    }
}
