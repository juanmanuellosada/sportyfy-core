package sportyfy.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sportyfy.core.entidades.core.SportyfyCore;
import sportyfy.core.entidades.partido.Partido;

import static org.junit.jupiter.api.Assertions.*;


public class UserStory1Tests {

    @BeforeAll
    public void escenario(){
        /*  el iniciadorSportyfyCore
            los core necesarios para llamar al método pronosticar()
            Equipo River,Gimnasia,equipoSinPartidos
         */

    }

    //// CÓMO SERÍAN LOS TEST CON EL NUEVO DISEÑO ////
    @Test
    public void ca1_PronosticoGanador(){
        Partido partido = new Partido();
        partido.setLocal(river); partido.setVisitante(gimnasiaDeLaPlata);
        pronosticador.prosnosticar(partido);
        assertEquals(partido.getGanador(),river);
    }

    @Test
    public void ca2_PronosticoEmpate(){
        Partido partido = new Partido();
        partido.setLocal(gimnasiaDeLaPlata); partido.setVisitante(gimnasiaDeLaPlata);
        pronosticador.pronosticar(partido);
        assertTrue(partido.getGanador().isEmpty());
    }

    //como un equipo no tiene partidos, su promedio de gol será 0 y ganador el otro equipo que tiene partidos
    @Test
    public void ca3_EquipoSinPartidos(){
        Partido partido = new Partido();
        partido.setLocal(gimnasiaDeLaPlata); partido.setVisitante(equipoSinPartidos);
        pronosticador.pronosticar(partido);
        assertEquals(partido.getMarcadorVisitante(),0);
        assertEquals(partido.getGanador(),gimnasiaDeLaPlata);
    }

    //Pronosticar un partido con algun equipo null
    @Test
    public void ca4_PronosticoInvalidoPorEquipoNull(){
        Partido partido = new Partido();
        partido.setLocal(null);partido.setVisitante(river);
        assertThrows(IllegalArgumentException.class, () -> pronosticador.pronosticar(partido));
    }

    // Pronosticar un partido donde no hay info de partidos historicos en el sistema para sustentar el algoritmo de pronostico
    @Test
    public void ca5_PronosticoInvalidoPorPartidosVacios(){
        Partido partido = new Partido();
        partido.setLocal(river); partido.setVisitante(gimnasiaDeLaPlata);
        SportyfyCore coreSinPartidosCargados = new SportyfyCore(pronosticadores,equipos,null);
        assertThrows(IllegalArgumentException.class, () -> coreSinPartidosCargados.pronosticar(partido,"pronosticadorFutbol"));
    }
}