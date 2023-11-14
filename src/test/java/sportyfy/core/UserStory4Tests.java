package sportyfy.core;

import org.junit.jupiter.api.*;
import sportyfy.apiFootball.GeneradorJsons;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserStory4Tests {

    @Test
    @Order(1)
    @DisplayName("Se la conexión con la API de API-FOOTBALL")
    public void CA1_PruebaConexion() {
        assertDoesNotThrow(GeneradorJsons::testConexion);
    }

}
