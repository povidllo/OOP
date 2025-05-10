package ru.nsu.kuzminov.game;

import ru.nsu.kuzminov.utils.Cell;
import ru.nsu.kuzminov.utils.Direction;
import ru.nsu.kuzminov.utils.GameStatus;

import static ru.nsu.kuzminov.utils.Cell.CellType.APPLE;
import static ru.nsu.kuzminov.utils.Cell.CellType.GRID;
import static ru.nsu.kuzminov.utils.Cell.CellType.SNAKE_BODY;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

/**
 * Класс предоставляющий игру.
 */
public class Game {
    protected Cell[][] grid;
    protected ArrayDeque<Cell> snake;
    protected ArrayList<Cell> apples;
    protected final int width;
    protected final int height;
    protected Direction direction;
    protected ArrayDeque<Direction> nextDirection;
    protected Random random;
    protected GameStatus status;
    protected int score;

    /**
     * Конструктор игры.
     *
     * @param height      высота поля.
     * @param width       ширина поля.
     * @param snakeStartX начальное положение змейки по x.
     * @param snakeStartY начальное положение змейки по y.
     */
    public Game(int height, int width, int snakeStartX, int snakeStartY) {
        this.grid = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new Cell(i, j, GRID);
            }
        }
        this.width = width;
        this.height = height;
        this.direction = Direction.WAIT;
        this.nextDirection = new ArrayDeque<>();
        this.status = GameStatus.IN_GAME;

        this.random = new Random();
        this.status = GameStatus.IN_GAME;
        this.snake = new ArrayDeque<>();
        this.apples = new ArrayList<>();
        this.score = 0;

        this.grid[snakeStartX][snakeStartY].setType(SNAKE_BODY);
        this.snake.add(grid[snakeStartX][snakeStartY]);
        setApples();
    }

    /**
     * Метод для получения поля.
     *
     * @return возвращает поле.
     */
    public Cell[][] getGrid() {
        return this.grid;
    }

    /**
     * Метод для получения высоты поля.
     *
     * @return возвращает высоту поля.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Метод для получения ширины поля.
     *
     * @return возвращает ширину поля.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Метод для получения статуса игры.
     *
     * @return возвращает статус игры
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Метод для постановки статуса игры.
     *
     * @param status новый статус игры из enum класса GameStatus.
     */
    public void setStatus(GameStatus status) {
        this.status = status;
    }

    /**
     * Метод для постановки следующего направления змейки.
     *
     * @param dir следующее направление змейки из enum класса Direction.
     */
    public void setNextDirection(Direction dir) {
        if (nextDirection.isEmpty()) {
            if ((direction == Direction.UP && dir == Direction.DOWN)
                    || (direction == Direction.DOWN && dir == Direction.UP)
                    || (direction == Direction.LEFT && dir == Direction.RIGHT)
                    || (direction == Direction.RIGHT && dir == Direction.LEFT)) {
                return;
            }
        } else {
            if ((nextDirection.getFirst() == Direction.UP && dir == Direction.DOWN)
                    || (nextDirection.getFirst() == Direction.DOWN && dir == Direction.UP)
                    || (nextDirection.getFirst() == Direction.LEFT && dir == Direction.RIGHT)
                    || (nextDirection.getFirst() == Direction.RIGHT && dir == Direction.LEFT)) {
                return;
            }
        }

        nextDirection.add(dir);
    }

    /**
     * Метод для выставления яблока на поле.
     */
    public void setApples() {
        if (getStatus() != GameStatus.IN_GAME) {
            return;
        }
        int xApple = random.nextInt(this.width);
        int yApple = random.nextInt(this.height);
        while (grid[xApple][yApple].getType() != GRID) {
            xApple = random.nextInt(this.width);
            yApple = random.nextInt(this.height);
        }
        grid[xApple][yApple].setType(APPLE);
    }

    /**
     * Метод для движения змейки по полю.
     */
    public void move() {

        if (!nextDirection.isEmpty()) {
            direction = nextDirection.removeFirst();
        }

        Cell head = snake.peekLast();
        int x = head.getX();
        int y = head.getYcord();

        int newX = x;
        int newY = y;

        switch (direction) {
            case UP -> newY--;
            case DOWN -> newY++;
            case LEFT -> newX--;
            case RIGHT -> newX++;
            default -> {
                return;
            }
        }
        if (newX == -1 || newX == width || newY == -1 || newY == height
                || grid[newX][newY].getType() == SNAKE_BODY) {
            setStatus(GameStatus.LOOSE);
            return;
        }
        if (grid[newX][newY].getType() == APPLE) {
            snake.addLast(grid[newX][newY]);
            grid[newX][newY].setType(SNAKE_BODY);
            if (snake.size() == width * height) {
                setStatus(GameStatus.WIN);
            }
            this.score++;
            System.out.printf("\rscore: " + score);
            setApples();

        } else {
            snake.addLast(grid[newX][newY]);
            grid[newX][newY].setType(SNAKE_BODY);

            Cell tail = snake.removeFirst();
            grid[tail.getX()][tail.getYcord()].setType(GRID);
        }
    }

    /**
     * Данный метод перезагружает игру.
     */
    public void reset() {
        this.grid = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new Cell(i, j, GRID);
            }
        }
        this.nextDirection = new ArrayDeque<>();
        this.status = GameStatus.IN_GAME;
        this.direction = Direction.WAIT;

        this.random = new Random();
        this.status = GameStatus.IN_GAME;
        this.snake = new ArrayDeque<>();
        this.apples = new ArrayList<>();
        this.score = 0;

        this.grid[this.width / 2][this.height / 2].setType(SNAKE_BODY);
        this.snake.add(grid[this.width / 2][this.height / 2]);
        setApples();
    }

    /**
     * Метод, возвращающий голову змейки.
     *
     * @return возвращает голову змейки.
     */
    public Cell getHead() {
        return snake.peekLast();
    }

    /**
     * Метод, возвращающий очки.
     *
     * @return score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Метод, возвращающий текущее направление движения змейки.
     *
     * @return возвращает текущее направление движения змейки.
     */
    public Direction getDirection() {
        return direction;
    }
}
