package sportyfy.core;

import org.junit.jupiter.api.*;
import sportyfy.core.entidades.core.SportyfyCore;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;
import sportyfy.core.entidades.resultado.Resultado;
import sportyfy.core.servicios.buscadores.BuscadorEquipos;
import sportyfy.core.servicios.buscadores.BuscadorPronosticadores;
import sportyfy.core.servicios.iniciador.IniciadorSportyfyCore;
import sportyfy.historial.Historial;

import java.util.List;

import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserStory5Tests {

    private static SportyfyCore sportyfyCore;
    private static final Historial historial = new Historial();
    private static Partido partido;

    private static final String rutaPartidos = "src/main/resources/datos/partidos/";

    @BeforeAll
    public static void escenario() throws IllegalArgumentException {
        sportyfyCore = new IniciadorSportyfyCore(false).iniciar("src/main/resources/pronosticadores", rutaPartidos);

        Pronosticador pronosticadorFutbol = new BuscadorPronosticadores()
                .buscarPronosticador(sportyfyCore.getPronosticadores(), "PronosticadorFutbol").orElseThrow();

        Equipo gimnasiaDeLaPlata = BuscadorEquipos
                .encontrarEquipoPorNombre("Gimnasia L.P.", pronosticadorFutbol.getEquipos()).orElseThrow();

        Equipo riverPlate = BuscadorEquipos.encontrarEquipoPorNombre("River Plate", pronosticadorFutbol.getEquipos())
                .orElseThrow();

        partido = new Partido(gimnasiaDeLaPlata, riverPlate);

        sportyfyCore.getNotificador().addPropertyChangeListener(historial);

    }

    @Test
    @Order(1)
    @DisplayName("Se guarda un pronostico con equipo ganador en el historial")
    void CA1_AgregarPronosticoAlHistorial() {
        Resultado resultadoPronosticadorFutbol = sportyfyCore.pronosticar(partido, "PronosticadorFutbol");
        Resultado resultadoPronosticadorFutbolCaraACara = sportyfyCore.pronosticar(partido,
                "PronosticadorFutbolCaraACara");

        System.out.println(historial.getPronosticosRealizados());
        assertThat(historial.getPronosticosRealizados(), is(not(empty())));
        List<Resultado> resultados = historial.getPronosticosRealizados().get(partido);

        assertThat(resultados, hasSize(2));
        assertTrue(resultados.contains(resultadoPronosticadorFutbol));
        assertTrue(resultados.contains(resultadoPronosticadorFutbolCaraACara));
    }
}
