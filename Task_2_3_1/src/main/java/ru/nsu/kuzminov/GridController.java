package ru.nsu.kuzminov;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Класс, предоставляющий контроллер javafx для поля.
 */
public class GridController {

    @FXML
    private Canvas Grid;
    private Game game;
    private int cellSize;

    /**
     * Метод для возвращения размера поля.
     *
     * @return возвращает размер поля.
     */
    public double getGridSize() {
        return Grid.getHeight();
    }

    /**
     * Метод для установления объекта игры и его параметров.
     *
     * @param game     объект игры.
     * @param cellSize размер одной клетки поля в пикселях.
     */
    void setGame(Game game, int cellSize) {
        this.game = game;
        this.cellSize = cellSize;
    }

    /**
     * Метод, инициализирующий контроллер.
     */
    public void initialize() {

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

                if (now - lastUpdate >= 300_000_000) { // 200ms
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
                    case SNAKE_BODY -> drawGridSegment(i, j, Color.GREEN);
                    case GRID -> drawGridSegment(i, j, Color.WHITE);
                    case APPLE -> drawGridSegment(i, j, Color.RED);
                }
            }
        }
        drawGrid();
    }

    /**
     * Метод, отрисовывающий поле.
     */
    private void drawGrid() {
        GraphicsContext gc = Grid.getGraphicsContext2D();
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
    private void drawGridSegment(int x, int y, Color color) {
        GraphicsContext gc = Grid.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
    }

}
