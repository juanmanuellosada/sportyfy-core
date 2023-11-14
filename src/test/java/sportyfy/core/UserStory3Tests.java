package sportyfy.core;

import org.junit.jupiter.api.*;
import sportyfy.core.entidades.core.SportyfyCore;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;
import sportyfy.core.entidades.resultado.Resultado;
import sportyfy.core.servicios.buscadores.BuscadorPronosticadores;
import sportyfy.core.servicios.iniciador.IniciadorSportyfyCore;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsIterableContaining.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserStory3Tests {

    private static SportyfyCore corePronosticadores;

    private static Pronosticador pronosticadorCaraACara;
    private static Set<Pronosticador> pronosticadoresPorDefecto;
    private static Set<Pronosticador> pronosticadoresConUnoNuevo;
    private static Equipo riverPlate;
    private static Equipo arsenal;
    private static Partido partido;
    private static Partido partidoCA3;
    private static final String rutaPartidos = "src/main/resources/datos/partidos";

    @BeforeAll
    public static void escenario() {
        corePronosticadores = new IniciadorSportyfyCore(false)
                .iniciar("src/test/resources/carpetasPruebaUS3/pronosticadoresPorDefecto", rutaPartidos);
        pronosticadoresPorDefecto = corePronosticadores.getPronosticadores();

        SportyfyCore corePronosticadorCaraACara = new IniciadorSportyfyCore(false)
                .iniciar("src/test/resources/carpetasPruebaUS3/pronosticadorCaraACara", rutaPartidos);
        pronosticadorCaraACara = corePronosticadorCaraACara.getPronosticadores().iterator().next();

        SportyfyCore corePronosticadoresConNuevo = new IniciadorSportyfyCore(false)
                .iniciar("src/test/resources/carpetasPruebaUS3/pronosticadoresConUnoNuevo", rutaPartidos);
        pronosticadoresConUnoNuevo = corePronosticadoresConNuevo.getPronosticadores();

        riverPlate = new Equipo("River Plate");
        Equipo centralCordoba = new Equipo("Central Cordoba de Santiago");
        arsenal = new Equipo("Arsenal Sarandi");
        partido = new Partido(riverPlate, centralCordoba);
        partidoCA3 = new Partido(riverPlate, arsenal);
    }

    @Test
    @Order(1)
    @DisplayName("Se prueba que el PronosticadorCaraACara efectivamente realice un pronóstico")
    public void CA1_PronosticoCaraACara() {
        Resultado resultado = pronosticadorCaraACara.pronosticar(partido);
        assertEquals(resultado.getGanador(), Optional.of(riverPlate));
    }

    @Test
    @Order(2)
    @DisplayName("Se elige entre los pronosticadores por defecto uno para realizar un pronóstico")
    public void CA2_SeHacePronosticoConUnPronosticador() {
        assertEquals(2, pronosticadoresPorDefecto.size());
        Resultado resultado = corePronosticadores.pronosticar(partido, "PronosticadorFutbol");
        assertEquals(resultado.getGanador(), Optional.of(riverPlate));
    }

    @Test
    @Order(3)
    @DisplayName("Se pronostica el mismo partido con los dos pronosticadores y se devuelve un ganador diferente")
    public void CA3_MismoPartidoDiferentePronostico() {
        Resultado resultadoPronosticadorFutbol = corePronosticadores.pronosticar(partidoCA3, "PronosticadorFutbol");
        Resultado resultadoPronosticadorCaraACara = corePronosticadores.pronosticar(partidoCA3,
                "PronosticadorFutbolCaraACara");
        assertThat(resultadoPronosticadorFutbol.getGanador(), is(not(resultadoPronosticadorCaraACara.getGanador())));
        assertThat(resultadoPronosticadorFutbol.getGanador(), is(Optional.of(riverPlate)));
        assertThat(resultadoPronosticadorCaraACara.getGanador(), is(Optional.of(arsenal)));
    }

    @Test
    @Order(4)
    @DisplayName("Se le pasa una nueva ruta al IniciadorSportyfyCore para que encuentre el nuevo Pronosticador y  se agregue al set de pronosticadores del core")
    public void CA4_AgregadoDeNuevoPronosticadorAlCore() {
        assertThat(pronosticadoresConUnoNuevo, hasSize(3));
        assertThat(pronosticadoresConUnoNuevo, hasItem(new BuscadorPronosticadores()
                .buscarPronosticador(pronosticadoresConUnoNuevo, "PronosticadorNuevo").orElse(null)));
    }

    @Test
    @Order(5)
    @DisplayName("Se pasa una ruta inexistente al IniciadorSportyfyCore al querer cargar nuevos pronosticadores, devuelve un RuntimeException del iniciador")
    public void CA5_RutaInexistenteParaAgregarPronosticador() {
        assertThrows(RuntimeException.class, () -> new IniciadorSportyfyCore(false)
                .iniciar("src/test/resources/carpetasPruebaUS3/direccioninvalida", rutaPartidos));
    }

    @Test
    @Order(6)
    @DisplayName("SportyfyCore tiene set de pronosticadores pero se elige para pronosticar uno que no existe en ese set, arroja IllegalArgumentException")
    public void CA6_SeBuscaPronosticarConPronosticadorInexistente() {
        assertThrows(IllegalArgumentException.class,
                () -> corePronosticadores.pronosticar(partido, "PronosticadorInexistente"));
    }

    @Test
    @Order(7)
    @DisplayName("No se le pasa un pronosticador al Core cuando se quiere pronosticar")
    public void CA7_NoSeEspecificaUnPronosticador() {
        assertThrows(IllegalArgumentException.class, () -> corePronosticadores.pronosticar(partido, null));
    }

}
