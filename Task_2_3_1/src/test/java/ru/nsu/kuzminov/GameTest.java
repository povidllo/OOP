package ru.nsu.kuzminov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.nsu.kuzminov.Cell.CellType.*;

class GameTest {

    @Test
    public void testWait() {
        Game game = new Game(10, 10, 5, 5);
        assertEquals(Direction.WAIT, game.getDirection());
    }

    @Test
    public void testSetNextDirection() {
        Game game = new Game(10, 10, 5, 5);
        game.setNextDirection(Direction.UP);
        game.move();
        assertEquals(Direction.UP, game.getDirection());
    }

    @Test
    public void testGrid() {
        Game game = new Game(2, 2, 1, 1);
        Cell[][] gameGrid = game.getGrid();
        int height = game.getHeight();
        int width = game.getWidth();
        Cell[][] testGrid = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                testGrid[i][j] = new Cell(i, j, GRID);
            }
        }

        testGrid[1][1].setType(SNAKE_BODY);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (gameGrid[i][j].getType() == APPLE) {
                    continue;
                }
                assertEquals(testGrid[i][j].getType(), gameGrid[i][j].getType());
            }
        }
    }

    @Test
    public void testStatus() {
        Game game = new Game(2, 2, 1, 1);
        assertEquals(GameStatus.IN_GAME, game.getStatus());
    }

    @Test
    public void testWIN() {
        Game game = new Game(1, 2, 0, 0);
        game.setNextDirection(Direction.RIGHT);
        game.move();
        assertEquals(GameStatus.WIN, game.getStatus());
    }

    @Test
    public void testReset() {
        Game game = new Game(1, 2, 0, 0);
        game.reset();
        var gameGrid = game.getGrid();
        assertEquals(SNAKE_BODY, gameGrid[2 / 2][1 / 2].getType());
    }
}