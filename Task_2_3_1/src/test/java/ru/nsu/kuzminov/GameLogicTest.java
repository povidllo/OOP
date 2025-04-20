package ru.nsu.kuzminov;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class GameLogicTest {

    private Game game;
    private GridController gridController;
    private GameLogic gameLogic;

    @BeforeEach
    void setUp() {
        game = mock(Game.class);
        gridController = mock(GridController.class);
        gameLogic = new GameLogic(game, gridController);
    }

    @Test
    void testHandleWkey() {
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "W", "W",
                KeyCode.W, false, false, false, false);
        gameLogic.handle(event);

        verify(game).setNextDirection(Direction.UP);
        verifyNoMoreInteractions(game, gridController);
    }

    @Test
    void testHandleAkey() {
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "A", "A",
                KeyCode.A, false, false, false, false);
        gameLogic.handle(event);

        verify(game).setNextDirection(Direction.LEFT);
        verifyNoMoreInteractions(game, gridController);
    }

    @Test
    void testHandleSkey() {
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "S", "S",
                KeyCode.S, false, false, false, false);
        gameLogic.handle(event);

        verify(game).setNextDirection(Direction.DOWN);
        verifyNoMoreInteractions(game, gridController);
    }

    @Test
    void testHandleDkey() {
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "D", "D",
                KeyCode.D, false, false, false, false);
        gameLogic.handle(event);

        verify(game).setNextDirection(Direction.RIGHT);
        verifyNoMoreInteractions(game, gridController);
    }

    @Test
    void testHandleEnterKey() {
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "ENTER", "ENTER",
                KeyCode.ENTER, false, false, false, false);
        gameLogic.handle(event);

        verify(game).reset();
        verify(gridController).updateGrid();
        verifyNoMoreInteractions(game, gridController);
    }
}
