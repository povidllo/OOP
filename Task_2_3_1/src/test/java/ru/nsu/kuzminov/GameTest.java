package ru.nsu.kuzminov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(10, 10, 5, 5);
    }

    @Test
    void testGetters() {
        assertEquals(10, game.getWidth());
        assertEquals(10, game.getHeight());
        assertEquals(GameStatus.IN_GAME, game.getStatus());
        assertEquals(Direction.WAIT, game.getDirection());
        assertNotNull(game.getGrid());
        assertNotNull(game.getHead());
    }

    @Test
    void testSetStatus() {
        game.setStatus(GameStatus.LOOSE);
        assertEquals(GameStatus.LOOSE, game.getStatus());
    }

    @Test
    void testSetNextDirectionWrongUp() {
        game.setNextDirection(Direction.UP);
        game.move();
        assertEquals(Direction.UP, game.getDirection());

        game.setNextDirection(Direction.DOWN);
        assertFalse(game.getDirection() == Direction.DOWN);
    }

    @Test
    void testSetNextDirectionWrongUpHave() {
        game.setNextDirection(Direction.LEFT);
        game.setNextDirection(Direction.UP);
        game.move();

        game.setNextDirection(Direction.DOWN);
        assertFalse(game.getDirection() == Direction.DOWN);
    }

    @Test
    void testSetNextDirectionWrongDown() {
        game.setNextDirection(Direction.DOWN);
        game.move();
        assertEquals(Direction.DOWN, game.getDirection());

        game.setNextDirection(Direction.UP);
        assertFalse(game.getDirection() == Direction.UP);
    }

    @Test
    void testSetNextDirectionWrongDownHave() {
        game.setNextDirection(Direction.LEFT);
        game.setNextDirection(Direction.DOWN);
        game.move();

        game.setNextDirection(Direction.UP);
        assertFalse(game.getDirection() == Direction.UP);
    }

    @Test
    void testSetNextDirectionWrongLeft() {
        game.setNextDirection(Direction.LEFT);
        game.move();
        assertEquals(Direction.LEFT, game.getDirection());

        game.setNextDirection(Direction.RIGHT);
        assertFalse(game.getDirection() == Direction.RIGHT);
    }

    @Test
    void testSetNextDirectionWrongLeftHave() {
        game.setNextDirection(Direction.UP);
        game.setNextDirection(Direction.LEFT);
        game.move();

        game.setNextDirection(Direction.RIGHT);
        assertFalse(game.getDirection() == Direction.RIGHT);
    }

    @Test
    void testSetNextDirectionWrongRight() {
        game.setNextDirection(Direction.RIGHT);
        game.move();
        assertEquals(Direction.RIGHT, game.getDirection());

        game.setNextDirection(Direction.LEFT);
        assertFalse(game.getDirection() == Direction.LEFT);
    }

    @Test
    void testSetNextDirectionWrongRightHave() {
        game.setNextDirection(Direction.UP);
        game.setNextDirection(Direction.RIGHT);
        game.move();

        game.setNextDirection(Direction.LEFT);
        assertFalse(game.getDirection() == Direction.LEFT);
    }

    @Test
    void testSetApples() throws Exception {
        game.setStatus(GameStatus.WIN);
        game.setApples();

        game.setStatus(GameStatus.IN_GAME);
        clearApples();

        game.setApples();

        boolean hasApple = false;
        for (int i = 0; i < game.getWidth(); i++) {
            for (int j = 0; j < game.getHeight(); j++) {
                if (game.getGrid()[i][j].getType() == Cell.CellType.APPLE) {
                    hasApple = true;
                }
            }
        }
        assertTrue(hasApple);
    }

    @Test
    void testMoveWait() {
        game.move();
        assertEquals(Direction.WAIT, game.getDirection());
        assertEquals(GameStatus.IN_GAME, game.getStatus());
    }

    @Test
    void testMoveCollisionWithWall() {
        game.setNextDirection(Direction.UP);
        for (int i = 0; i < 10; i++) {
            game.move();
        }
        assertEquals(GameStatus.LOOSE, game.getStatus());
    }

    @Test
    void testMoveCollisionWithSelf() {
        game.setNextDirection(Direction.RIGHT);
        game.move();
        game.setNextDirection(Direction.DOWN);
        game.move();
        game.setNextDirection(Direction.LEFT);
        game.move();
        game.setNextDirection(Direction.UP);
        game.move();
        System.out.println(game.getStatus());
        assertEquals(GameStatus.IN_GAME, game.getStatus());
    }

    @Test
    void testMoveEatApple() throws Exception {
        Cell head = game.getHead();
        int x = head.getX();
        int y = head.getYcord();

        if (x + 1 < game.getWidth()) {
            game.getGrid()[x + 1][y].setType(Cell.CellType.APPLE);
            game.setNextDirection(Direction.RIGHT);
        } else {
            game.getGrid()[x - 1][y].setType(Cell.CellType.APPLE);
            game.setNextDirection(Direction.LEFT);
        }

        int oldSize = getSnakeSize();

        game.move();

        assertEquals(oldSize + 1, getSnakeSize());
        assertEquals(GameStatus.IN_GAME, game.getStatus());
    }

    @Test
    void testReset() {
        game.reset();
        assertEquals(GameStatus.IN_GAME, game.getStatus());
        assertEquals(Direction.WAIT, game.getDirection());
        assertNotNull(game.getHead());
    }


    private void clearApples() {
        for (int i = 0; i < game.getWidth(); i++) {
            for (int j = 0; j < game.getHeight(); j++) {
                if (game.getGrid()[i][j].getType() == Cell.CellType.APPLE) {
                    game.getGrid()[i][j].setType(Cell.CellType.GRID);
                }
            }
        }
    }

    private int getSnakeSize() throws Exception {
        Field snakeField = Game.class.getDeclaredField("snake");
        snakeField.setAccessible(true);
        ArrayDeque<?> snake = (ArrayDeque<?>) snakeField.get(game);
        return snake.size();
    }
}
