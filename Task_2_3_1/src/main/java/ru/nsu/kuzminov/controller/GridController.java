package ru.nsu.kuzminov.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.kuzminov.utils.Cell;
import ru.nsu.kuzminov.utils.GameStatus;
import ru.nsu.kuzminov.game.Game;

/**
 * Класс, предоставляющий контроллер javafx для поля.
 */
public class GridController {

    @FXML
    protected Canvas grid;
    protected Game game;
    protected int cellSize;

    /**
     * Метод для возвращения размера поля.
     *
     * @return возвращает размер поля.
     */
    public double getGridSize() {
        return grid.getHeight();
    }

    /**
     * Метод для установления объекта игры и его параметров.
     *
     * @param game     объект игры.
     * @param cellSize размер одной клетки поля в пикселях.
     */
    public void setGame(Game game, int cellSize) {
        this.game = game;
        this.cellSize = cellSize;
    }

    /**
     * Метод, инициализирующий контроллер.
     */
    public void initialize() {

    }

    /**
     * Устанавливает поле.
     * @param grid новое поле.
     */
    public void setGrid(Canvas grid) {
        this.grid = grid;
    }

    /**
     * Возвращает поле.
     *
     * @return поле.
     */
    public Canvas getGrid() {
        return grid;
    }

    /**
     * Метод, инициализирующий игру.
     */
    public void initGame() {
        updateGrid();
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (game.getStatus() != GameStatus.IN_GAME) {
                    return;
                }

                if (now - lastUpdate >= 200_000_000) {
                    game.move();
                    updateGrid();
                    lastUpdate = now;
                }
            }
        };

        timer.start();
    }

    /**
     * Метод, обновляющий отрисовка поля.
     */
    public void updateGrid() {
        int height = game.getHeight();
        int width = game.getWidth();
        Cell[][] curCell = game.getGrid();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                switch (curCell[i][j].getType()) {
                    case SNAKE_BODY -> drawGridSegment(i, j, Color.LIGHTGREEN);
                    case GRID -> drawGridSegment(i, j, Color.WHITE);
                    case APPLE -> drawGridSegment(i, j, Color.RED);
                    default -> {
                        break;
                    }
                }
            }
        }
        Cell head = game.getHead();
        drawGridSegment(head.getX(), head.getYcord(), Color.GREEN);
        drawGrid();
    }

    /**
     * Метод, отрисовывающий поле.
     */
    private void drawGrid() {
        GraphicsContext gc = grid.getGraphicsContext2D();
        gc.setStroke(Color.LIGHTGRAY);
        int height = (int) (game.getHeight() * cellSize);
        int width = (int) (game.getWidth() * cellSize);
        for (int i = 0; i <= width; i += cellSize) {
            gc.strokeLine(i, 0, i, height);
        }
        for (int i = 0; i <= height; i += cellSize) {
            gc.strokeLine(0, i, width, i);
        }

    }

    /**
     * Метод, отрисовывающий один сегмент поля.
     */
    public void drawGridSegment(int x, int y, Color color) {
        GraphicsContext gc = grid.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
    }

}
