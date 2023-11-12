package sportyfy.core;

import org.junit.jupiter.api.*;
import sportyfy.core.entidades.core.SportyfyCore;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;
import sportyfy.core.entidades.resultado.Resultado;
import sportyfy.core.servicios.buscadores.BuscadorEquipos;
import sportyfy.core.servicios.iniciador.IniciadorSportyfyCore;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserStory1Tests {

    private SportyfyCore sportyfyCore;

    private SportyfyCore sportyfyCoreDosPronosticadores;
    private Equipo riverPlate;
    private Equipo talleres;
    private Equipo newellsOldBoys;

    private Equipo equipoSinPartidos;

    private Equipo equipoNulo;

    private Partido partidoNulo;

    private static final String PRONOSTICADOR_FUTBOL = "PronosticadorFutbol";
    private static final String PRONOSTICADOR_FUTBOL_CARA_A_CARA = "PronosticadorFutbolCaraACara";
    private static final String rutaPartidos = "src/main/resources/datos/partidos";

    @BeforeEach
    public void escenario() {
        sportyfyCore = IniciadorSportyfyCore.iniciar("src/main/resources/pronosticadores",
                rutaPartidos);
        Pronosticador pronosticadorFutbol = sportyfyCore.getPronosticadores().iterator().next();
        assertThat(pronosticadorFutbol.getDeporte(), is("Fútbol"));
        assertThat(pronosticadorFutbol.getEquipos(), is(notNullValue()));
        assertThat(pronosticadorFutbol.getEquipos(), is(not(empty())));
        riverPlate = BuscadorEquipos.encontrarEquipoPorNombre("River Plate", pronosticadorFutbol.getEquipos())
                .orElseThrow();
        talleres = BuscadorEquipos
                .encontrarEquipoPorNombre("Talleres Cordoba", pronosticadorFutbol.getEquipos())
                .orElseThrow();
        newellsOldBoys = BuscadorEquipos.encontrarEquipoPorNombre("Newells Old Boys", pronosticadorFutbol.getEquipos())
                .orElseThrow();
        equipoSinPartidos = new Equipo("Equipo sin partidos");
        equipoNulo = null;
        partidoNulo = null;
    }

    @Test
    @Order(1)
    @DisplayName("Pronóstico efectivo del partido (hay ganador)")
    public void CA1_PronosticoGanador() {
        Partido partido = new Partido(riverPlate, talleres);
        Resultado resultadoPronosticadorFutbol = sportyfyCore.pronosticar(partido, PRONOSTICADOR_FUTBOL);
        assertThat(resultadoPronosticadorFutbol, is(notNullValue()));
        assertThat(resultadoPronosticadorFutbol.getMarcador(riverPlate), is(notNullValue()));
        assertThat(resultadoPronosticadorFutbol.getMarcador(talleres), is(notNullValue()));
        assertThat(resultadoPronosticadorFutbol.getGanador(), is(Optional.of(riverPlate)));

    }

    @Test
    @Order(2)
    @DisplayName("Pronóstico efectivo del partido (empate)")
    public void CA2_PronosticoEmpate() {
        Partido partido = new Partido(riverPlate, newellsOldBoys);
        Resultado resultado = sportyfyCore.pronosticar(partido, "PronosticadorFutbol");
        assertThat(resultado, is(notNullValue()));
        assertThat(resultado.getMarcador(riverPlate), is(notNullValue()));
        assertThat(resultado.getMarcador(newellsOldBoys), is(notNullValue()));
        assertThat(resultado.getGanador(), is(Optional.empty()));
    }

    @Test
    @Order(3)
    @DisplayName("No se encuentran partidos de equipo (gana el otro equipo con partidos)")
    public void CA3_NoSeEncuentranPartidosDeEquipo() {
        Partido partido = new Partido(riverPlate, equipoSinPartidos);
        Resultado resultado = sportyfyCore.pronosticar(partido, "PronosticadorFutbol");
        assertThat(resultado, is(notNullValue()));
        assertThat(resultado.getMarcador(riverPlate), is(notNullValue()));
        assertThat(resultado.getMarcador(equipoSinPartidos), is(notNullValue()));
        assertThat(resultado.getMarcador(equipoSinPartidos), is(not(0)));
        assertThat(resultado.getGanador(), is(Optional.of(riverPlate)));
    }

    @Test
    @Order(4)
    @DisplayName("Intento hacer un pronostico con un partido con el mismo equipo")
    public void CA4_PronosticoMismoEquipo() {
        Partido partido = new Partido(riverPlate, riverPlate);
        assertThrows(IllegalArgumentException.class, () -> sportyfyCore.pronosticar(partido, "PronosticadorFutbol"));
    }

    @Test
    @Order(5)
    @DisplayName("Intento hacer un pronostico con un partido con un equipo null")
    public void CA5_PronosticoEquipoNull() {
        Partido partido = new Partido(riverPlate, equipoNulo);
        assertThrows(NullPointerException.class, () -> sportyfyCore.pronosticar(partido, "PronosticadorFutbol"));
    }

    @Test
    @Order(6)
    @DisplayName("Intento hacer un pronostico con un pronosticador desconocido (No lo encuentra en el core)")
    public void CA6_PronosticoPronosticadorDesconocido() {
        Partido partido = new Partido(riverPlate, talleres);
        assertThrows(IllegalArgumentException.class, () -> sportyfyCore.pronosticar(partido, "PronosticadorDesconocido"));
    }

    @Test
    @Order(7)
    @DisplayName("Intento hacer un pronostico con un partido nulo")
    public void CA7_PronosticoPartidoNulo() {
        assertThrows(NullPointerException.class, () -> sportyfyCore.pronosticar(partidoNulo, "PronosticadorFutbol"));
    }

    @Test
    @Order(8)
    @DisplayName("Intento hacer un pronostico con un pronosticador nulo")
    public void CA8_PronosticoPronosticadorNulo() {
        Partido partido = new Partido(riverPlate, talleres);
        assertThrows(IllegalArgumentException.class, () -> sportyfyCore.pronosticar(partido, null));
    }

    @Test
    @Order(9)
    @DisplayName("Intento hacer un pronostico con un pronosticador vacío")
    public void CA9_PronosticoPronosticadorVacio() {
        Partido partido = new Partido(riverPlate, talleres);
        assertThrows(IllegalArgumentException.class, () -> sportyfyCore.pronosticar(partido, ""));
    }
}