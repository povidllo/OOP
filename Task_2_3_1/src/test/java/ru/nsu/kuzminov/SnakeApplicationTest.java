package ru.nsu.kuzminov;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
class SnakeApplicationTest {

    @Test
    void setFxmlLoader() {
        SnakeApplication app = new SnakeApplication();
        assertNotNull(app.setFxmlLoader());
    }
}