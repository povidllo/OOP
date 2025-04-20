package ru.nsu.kuzminov;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
class StartGameControllerTest extends ApplicationTest {

    @Test
    void validTest() {
        StartGameController cont = new StartGameController();
        assertTrue(cont.valid(12,12));
    }
}