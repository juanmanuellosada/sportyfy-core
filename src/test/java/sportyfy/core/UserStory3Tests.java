package sportyfy.core;

import lombok.Data;
import org.junit.jupiter.api.*;
import sportyfy.core.entidades.core.SportyfyCore;
import sportyfy.core.entidades.equipo.Equipo;
import sportyfy.core.entidades.partido.Partido;
import sportyfy.core.entidades.resultado.Resultado;
import sportyfy.core.servicios.buscadores.BuscadorPronosticadores;
import sportyfy.core.servicios.iniciador.IniciadorSportyfyCore;


import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserStory3Tests {

    private static SportyfyCore corePronosticadores;
    private static SportyfyCore corePronosticadorCaraACara;
    private static SportyfyCore corePronosticadoresConNuevo;

    private static Pronosticador pronosticadorCaraACara;
    private static Set<Pronosticador> pronosticadoresPorDefecto;
    private static Set<Pronosticador> pronosticadoresConUnoNuevo;
    private static Equipo riverPlate;
    private static Equipo centralCordoba;
    private static  Equipo talleres;
    private static Partido partido;
    private static Partido partidoCA3;
    private static final String rutaPartidos = "src/main/resources/datos/partidos";
    @BeforeAll
    public static void escenario () {
        corePronosticadores = IniciadorSportyfyCore.iniciar("src/test/resources/carpetasPruebaUS3/pronosticadoresPorDefecto",rutaPartidos);
        pronosticadoresPorDefecto = corePronosticadores.getPronosticadores();

        corePronosticadorCaraACara = IniciadorSportyfyCore.iniciar("src/test/resources/carpetasPruebaUS3/pronosticadorCaraACara",rutaPartidos);
        pronosticadorCaraACara = corePronosticadorCaraACara.getPronosticadores().iterator().next();

        corePronosticadoresConNuevo = IniciadorSportyfyCore.iniciar("src/test/resources/carpetasPruebaUS3/pronosticadoresConUnoNuevo",rutaPartidos);
        pronosticadoresConUnoNuevo = corePronosticadoresConNuevo.getPronosticadores();

        riverPlate = new Equipo("River Plate");
        centralCordoba = new Equipo("Central Cordoba de Santiago");
        talleres = new Equipo ("Talleres Cordoba");
        partido = new Partido(riverPlate,centralCordoba);
        partidoCA3 = new Partido(riverPlate,talleres);
    }

    @Test
    @Order(1)
    @DisplayName("Se prueba que el PronosticadorCaraACara efectivamente realice un pronóstico")
    public void CA1_PronosticoCaraACara() {
       Resultado resultado = pronosticadorCaraACara.pronosticar(partido);
       assertEquals(resultado.getGanador(),Optional.of(riverPlate));
    }

    @Test
    @Order(2)
    @DisplayName("Se elige entre los pronosticadores por defecto uno para realizar un pronóstico")
    public void CA2_SeHacePronosticoConUnPronosticador() {
        assertEquals(2, pronosticadoresPorDefecto.size());
        Resultado resultado = corePronosticadores.pronosticar(partido,"PronosticadorFutbol");
        assertEquals(resultado.getGanador(),Optional.of(riverPlate));
    }

    @Test
    @Order(3)
    @DisplayName("Se pronostica el mismo partido con los dos pronosticadores y se devuelve un ganador diferente")
    public void CA3_MismoPartidoDiferentePronostico() {
        Resultado resultadoPronosticadorFutbol = corePronosticadores.pronosticar(partidoCA3,"PronosticadorFutbol");
        Resultado resultadoPronosticadorCaraACara = corePronosticadores.pronosticar(partidoCA3,"PronosticadorFutbolCaraACara");
        assertEquals(resultadoPronosticadorFutbol.getGanador(),Optional.of(riverPlate));
        assertEquals(resultadoPronosticadorCaraACara.getGanador(),Optional.of(talleres));
    }

    @Test
    @Order(4)
    @DisplayName("Se le pasa una nueva ruta al IniciadorSportyfyCore para que encuentre el nuevo Pronosticador y  se agregue al set de pronosticadores del core")
    public void CA4_AgregadoDeNuevoPronosticadorAlCore() {
        assertEquals(3, pronosticadoresConUnoNuevo.size());
        assertTrue(pronosticadoresConUnoNuevo.contains(new BuscadorPronosticadores().buscarPronosticador(pronosticadoresConUnoNuevo,"PronosticadorNuevo").orElse(null)));
    }

    @Test
    @Order(5)
    @DisplayName("Se pasa una ruta inexistente al IniciadorSportyfyCore al querer cargar nuevos pronosticadores, devuelve un RuntimeException del iniciador")
    public void CA5_RutaInexistenteParaAgregarPronosticador() {
        assertThrows(RuntimeException.class, () -> IniciadorSportyfyCore.iniciar("src/test/resources/carpetasPruebaUS3/direccioninvalida", rutaPartidos));
    }

    @Test
    @Order(6)
    @DisplayName("SportyfyCore tiene set de pronosticadores pero se elige para pronosticar uno que no existe en ese set, arroja IllegalArgumentException")
    public void CA6_SeBuscaPronosticarConPronosticadorInexistente() {
        assertThrows(IllegalArgumentException.class, () -> corePronosticadores.pronosticar(partido,"PronosticadorInexistente"));
    }

    @Test
    @Order(7)
    @DisplayName("No se le pasa un pronosticador al Core cuando se quiere pronosticar")
    public void CA7_NoSeEspecificaUnPronosticador(){
        assertThrows(IllegalArgumentException.class, () -> corePronosticadores.pronosticar(partido,null));
    }

}
