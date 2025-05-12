package ru.nsu.kuzminov;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class SnakeApplicationTest {

    @Test
    void setFxmlLoader() {
        SnakeApplication app = new SnakeApplication();

        assertNotNull(app.setFxmlLoader());
    }
}