package sportyfy.core;

import org.junit.jupiter.api.*;
import sportyfy.core.entidades.Pronosticador;
import sportyfy.core.entidades.core.SportyfyCore;
import sportyfy.core.servicios.iniciadores.IniciadorSportyfyCore;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserStory2Tests {

    private static IniciadorSportyfyCore iniciador;

    @BeforeAll
    public static void escenario() throws IOException {
        iniciador = new IniciadorSportyfyCore();
    }

    @Test
    @Order(1)
    @DisplayName("Carpeta con un Pronosticador válido")
    public void CA1_CarpetaConPronosticadorValido() throws IOException {
        SportyfyCore core = iniciador.iniciar("ruta donde esté el pronosticador de futbol");
        Set<Pronosticador> pronosticador = core.getPronosticadores();
        assertEquals(pronosticador.size(),1);
        assertTrue(pronosticador.contains(PronosticadorFutbol));
    }

    @Test
    @Order(2)
    @DisplayName("Carpeta con un Pronosticador válido")
    public void CA2_CarpetaConPronosticadoresValidos() throws IOException {
        SportyfyCore core = iniciador.iniciar("ruta donde esté el pronosticador de futbol y alguno mas");
        Set<Pronosticador> pronosticadores = core.getPronosticadores();
        assertEquals(pronosticadores.size(),2);
        assertTrue(pronosticadores.contains(PronosticadorFutbol));
        assertTrue(pronosticadores.contains(PronosticadorValido));
    }

    @Test
    @Order(3)
    @DisplayName("Carpeta vacía")
    public void CA3_CarpetaVacia() throws IOException {
        SportyfyCore coreCarpetaVacia = iniciador.iniciar(
                "ruta de carpeta existente pero vacía ");
        Set<Pronosticador> pronosticador = coreCarpetaVacia.getPronosticadores();
        assertTrue(pronosticador.isEmpty());
    }

    @Test
    @Order(4)
    @DisplayName("Carpeta con archivo de extensión inválida (no es .JAR)")
    public void CA4_ubicacionInvalida() {
        assertThrows(IllegalArgumentException.class, () -> iniciador.iniciar("ruta donde se encuentra el archivo que no es .jar"));
    }

    @Test
    @Order(5)
    @DisplayName("Carpeta con JAR pero no es Pronosticador")
    public void CA5_CarpetaConJARNoPronosticador() throws IOException {
        SportyfyCore coreJarNoPronosticador = iniciador.iniciar("ruta donde esta el JAR que no es Pronosticador");
        Set<Pronosticador> pronosticadores = coreJarNoPronosticador.getPronosticadores();
        assertTrue(pronosticadores.isEmpty());
    }

    @Test
    @Order(6)
    @DisplayName("Carpeta inexistente")
    public void CA6_CarpetaNoExiste() {
        assertThrows(FileNotFoundException.class, () -> iniciador.iniciar("ruta inexistente en el proyecto"));
    }
}