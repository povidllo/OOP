//package ru.nsu.kuzminov;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import javafx.scene.canvas.Canvas;
//import javafx.scene.paint.Color;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.testfx.framework.junit5.ApplicationTest;
//
//class GridControllerTest extends ApplicationTest {
//    GridController controller;
//    Game game;
//    Canvas grid;
//
//    @BeforeEach
//    public void start() {
//        game = new Game(10, 10, 5, 5);
//        grid = new Canvas();
//        controller = new GridController();
//
//        controller.setGame(game, 20);
//        controller.setGrid(grid);
//    }
//
//    @Test
//    void testGetGridSize() {
//        grid.setHeight(500);
//
//        assertEquals(500, controller.getGridSize());
//    }
//
//    @Test
//    void testSetGame() {
//        Game newGame = new Game(15, 15, 7, 7);
//
//        controller.setGame(newGame, 30);
//        assertNotNull(controller);
//    }
//
//    @Test
//    void testUpdateGrid() {
//        assertDoesNotThrow(() -> controller.updateGrid());
//    }
//
//    @Test
//    void testInitGame() {
//        controller.initGame();
//        assertDoesNotThrow(() -> controller.updateGrid());
//    }
//
//    @Test
//    void testSetGrid() {
//        Canvas newGrid = new Canvas();
//        controller.setGrid(newGrid);
//        assertEquals(newGrid, controller.getGrid());
//    }
//
//    @Test
//    void testDrawGridSegment() {
//        controller.drawGridSegment(1, 1, Color.RED);
//        assertDoesNotThrow(() -> controller.drawGridSegment(1, 1, Color.RED));
//    }
//}
