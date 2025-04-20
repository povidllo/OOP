package ru.nsu.kuzminov;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;

import static org.testfx.api.FxToolkit.registerPrimaryStage;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StartGameControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        registerPrimaryStage();
        new SnakeApplication().start(stage);
    }

    @Test
    void startGameWithValidData() {
        clickOn("#widthArea").write("20");
        clickOn("#heightArea").write("20");
        clickOn("#startButton");

        FxAssert.verifyThat(window("Snake"), WindowMatchers.isShowing());
    }
}
