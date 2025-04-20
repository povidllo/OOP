package ru.nsu.kuzminov;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StartGameControllerTest {

    @Test
    void validTest() {
        StartGameController cont = new StartGameController();
        assertTrue(cont.valid(12,12));
    }

    void testInitGame() {
        StartGameController cont = new StartGameController();
        assertNotNull(cont.initGame(13,13));
    }
}