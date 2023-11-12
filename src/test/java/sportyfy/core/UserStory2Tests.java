package sportyfy.core;

import org.junit.jupiter.api.*;
import sportyfy.core.entidades.core.SportyfyCore;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;
import sportyfy.core.entidades.resultado.Resultado;
import sportyfy.core.servicios.iniciador.IniciadorSportyfyCore;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserStory2Tests {

    private static final String rutaPartidos = "src/main/resources/datos/partidos";
    private static SportyfyCore sportyfyCoreDosPronosticadores;
    private static Partido partido;
    private static final String PRONOSTICADOR_FUTBOL = "PronosticadorFutbol";
    private static final String PRONOSTICADOR_FUTBOL_CARA_A_CARA = "PronosticadorFutbolCaraACara";

    @BeforeAll
    public static void escenario() {
        sportyfyCoreDosPronosticadores = IniciadorSportyfyCore.iniciar(
                "src/test/resources/pronosticadoresDosPronosticadores",
                rutaPartidos);
        partido = new Partido(new Equipo("Equipo 1"), new Equipo("Equipo 2"));
    }

    @Test
    @Order(1)
    @DisplayName("Carpeta con un pronosticador válido")
    public void CA1_CarpetaConUnPronosticadorValido() {
        SportyfyCore sportyfyCore = IniciadorSportyfyCore.iniciar("src/test/resources/pronosticadoresUnPronosticador",
                rutaPartidos);
        assertThat(sportyfyCore.getPronosticadores(), hasSize(1));
    }

    @Test
    @Order(2)
    @DisplayName("Carpeta vacía")
    public void CA2_CarpetaVacia() {
        SportyfyCore sportyfyCore = IniciadorSportyfyCore.iniciar("src/test/resources/pronosticadoresVacia",
                rutaPartidos);
        assertThat(sportyfyCore.getPronosticadores(), hasSize(0));
    }

    @Test
    @Order(3)
    @DisplayName("Carpeta con un pronosticador inválido")
    public void CA3_CarpetaConUnPronosticadorInvalido() {
        assertThrows(RuntimeException.class,
                () -> IniciadorSportyfyCore.iniciar("src/test/resources/pronosticadorExtensionInvalida",
                        rutaPartidos));
    }

    @Test
    @Order(4)
    @DisplayName("Carpeta con un pronosticador que no implementa la interfaz")
    public void CA4_CarpetaConUnPronosticadorQueNoImplementaLaInterfaz() {
        SportyfyCore sportyfyCore = IniciadorSportyfyCore.iniciar(
                "src/test/resources/pronosticadoresJarNoEsPronosticador",
                rutaPartidos);
        assertThat(sportyfyCore.getPronosticadores(), hasSize(0));
    }

    @Test
    @Order(5)
    @DisplayName("Carpeta inexistente")
    public void CA5_CarpetaInexistente() {
        assertThrows(RuntimeException.class,
                () -> IniciadorSportyfyCore.iniciar("src/test/resources/pronosticadoresInexistente",
                        rutaPartidos));
    }

    @Test
    @Order(6)
    @DisplayName("Carpeta con dos pronosticadores válidos")
    public void CA6_CarpetaConDosPronosticadoresValidos() {
        assertThat(sportyfyCoreDosPronosticadores.getPronosticadores(), hasSize(2));

        List<String> nombresPronosticadores = sportyfyCoreDosPronosticadores.getPronosticadores().stream()
                .map(pronosticador -> pronosticador.getClass().getSimpleName())
                .toList();

        assertThat(nombresPronosticadores, containsInAnyOrder(PRONOSTICADOR_FUTBOL, PRONOSTICADOR_FUTBOL_CARA_A_CARA));
    }

    @Test
    @Order(7)
    @DisplayName("Pronosticar con PronosticadorFutbol")
    public void CA7_PronosticarConPronosticadorFutbol() {
        Resultado resultado = sportyfyCoreDosPronosticadores.pronosticar(partido, PRONOSTICADOR_FUTBOL);
        assertThat(resultado, is(notNullValue()));
    }

    @Test
    @Order(8)
    @DisplayName("Pronosticar con PronosticadorFutbolCaraACara")
    public void CA8_PronosticarConPronosticadorFutbolCaraACara() {
        // Este pronosticador no te deja pronosticar si no encuentra partidos para esos
        // equipos
        assertThrows(RuntimeException.class,
                () -> sportyfyCoreDosPronosticadores.pronosticar(partido, PRONOSTICADOR_FUTBOL_CARA_A_CARA));
    }

    @Test
    @Order(9)
    @DisplayName("Carpeta con solo un pronosticador válido (varios archivos)")
    public void CA9_CarpetaConSoloUnPronosticadorValido() {
        SportyfyCore sportyfyCore = IniciadorSportyfyCore.iniciar(
                "src/test/resources/pronosticadoresUnoValidoVariosInvalidos",
                rutaPartidos);
        assertThat(sportyfyCore.getPronosticadores(), hasSize(1));
    }
}
