package ru.nsu.kuzminov;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SnakeApplicationTest extends ApplicationTest {

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        new SnakeApplication().start(stage);
    }

    @Test
    void testStageTitle() {
        assertEquals("Snake - меню", stage.getTitle());
    }
}
