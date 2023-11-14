package sportyfy.core;

import org.junit.jupiter.api.*;
import sportyfy.core.entidades.core.SportyfyCore;
import sportyfy.core.servicios.iniciador.IniciadorSportyfyCore;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserStory2Tests {

    private static final String rutaPartidos = "src/main/resources/datos/partidos";
    private static final String PRONOSTICADOR_FUTBOL = "PronosticadorFutbol";
    private static final String PRONOSTICADOR_FUTBOL_CARA_A_CARA = "PronosticadorFutbolCaraACara";

    @Test
    @Order(1)
    @DisplayName("Carpeta con un pronosticador válido")
    public void CA1_CarpetaConUnPronosticadorValido() {
        SportyfyCore sportyfyCore = new IniciadorSportyfyCore(false).iniciar("src/test/resources/pronosticadoresUnPronosticador",
                rutaPartidos);
        assertThat(sportyfyCore.getPronosticadores(), hasSize(1));
    }

    @Test
    @Order(2)
    @DisplayName("Carpeta vacía")
    public void CA2_CarpetaVacia() {
        SportyfyCore sportyfyCore = new IniciadorSportyfyCore(false).iniciar("src/test/resources/pronosticadoresVacia",
                rutaPartidos);
        assertThat(sportyfyCore.getPronosticadores(), hasSize(0));
    }

    @Test
    @Order(3)
    @DisplayName("Carpeta con un pronosticador inválido")
    public void CA3_CarpetaConUnPronosticadorInvalido() {
        assertThrows(RuntimeException.class,
                () -> new IniciadorSportyfyCore(false).iniciar("src/test/resources/pronosticadorExtensionInvalida",
                        rutaPartidos));
    }

    @Test
    @Order(4)
    @DisplayName("Carpeta con un pronosticador que no implementa la interfaz")
    public void CA4_CarpetaConUnPronosticadorQueNoImplementaLaInterfaz() {
        SportyfyCore sportyfyCore = new IniciadorSportyfyCore(false).iniciar(
                "src/test/resources/pronosticadoresJarNoEsPronosticador",
                rutaPartidos);
        assertThat(sportyfyCore.getPronosticadores(), hasSize(0));
    }

    @Test
    @Order(5)
    @DisplayName("Carpeta inexistente")
    public void CA5_CarpetaInexistente() {
        assertThrows(RuntimeException.class,
                () -> new IniciadorSportyfyCore(false).iniciar("src/test/resources/pronosticadoresInexistente",
                        rutaPartidos));
    }

    @Test
    @Order(6)
    @DisplayName("Carpeta con dos pronosticadores válidos")
    public void CA6_CarpetaConDosPronosticadoresValidos() {
        SportyfyCore sportyfyCoreDosPronosticadores = new IniciadorSportyfyCore(false).iniciar(
                "src/test/resources/pronosticadoresDosPronosticadores",
                rutaPartidos);

        assertThat(sportyfyCoreDosPronosticadores.getPronosticadores(), hasSize(2));

        List<String> nombresPronosticadores = sportyfyCoreDosPronosticadores.getPronosticadores().stream()
                .map(pronosticador -> pronosticador.getClass().getSimpleName())
                .toList();

        assertThat(nombresPronosticadores, containsInAnyOrder(PRONOSTICADOR_FUTBOL, PRONOSTICADOR_FUTBOL_CARA_A_CARA));
    }

    @Test
    @Order(7)
    @DisplayName("Carpeta con solo un pronosticador válido (varios archivos)")
    public void CA9_CarpetaConSoloUnPronosticadorValido() {
        SportyfyCore sportyfyCore = new IniciadorSportyfyCore(false).iniciar(
                "src/test/resources/pronosticadoresUnoValidoVariosInvalidos",
                rutaPartidos);
        assertThat(sportyfyCore.getPronosticadores(), hasSize(1));
    }
}
