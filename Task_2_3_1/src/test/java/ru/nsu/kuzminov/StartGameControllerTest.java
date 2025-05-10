package ru.nsu.kuzminov;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.kuzminov.controller.StartGameController;

class StartGameControllerTest {

    @Test
    void validTest() {
        StartGameController cont = new StartGameController();

        assertTrue(cont.valid(12,12));
    }

    @Test
    void testInitGame() {
        StartGameController cont = new StartGameController();

        assertNotNull(cont.initGame(13,13));
    }
}