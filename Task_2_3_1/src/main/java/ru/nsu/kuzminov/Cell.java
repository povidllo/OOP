package ru.nsu.kuzminov;

/**
 * Класс для представления одной клетки игрового поля.
 * Каждая клетка имеет координаты (x, y) и тип, определённый в перечислении {@link CellType}.
 */

public class Cell {
    private final int xcord;
    private final int ycord;
    private CellType type;

    /**
     * Конструктор для создания новой клетки игрового поля.
     *
     * @param x    координата X.
     * @param y    координата Y.
     * @param type начальный тип клетки.
     */
    Cell(int x, int y, CellType type) {
        this.xcord = x;
        this.ycord = y;
        this.type = type;
    }

    /**
     * Возвращает текущий тип клетки.
     *
     * @return тип клетки.
     */
    public CellType getType() {
        return type;
    }

    /**
     * Возвращает координату X клетки.
     *
     * @return координата X.
     */
    public int getX() {
        return xcord;
    }

    /**
     * Возвращает координату Y клетки.
     *
     * @return координата Y.
     */
    public int getYcord() {
        return ycord;
    }

    /**
     * Устанавливает новый тип клетки.
     *
     * @param type новый тип клетки.
     */
    public void setType(CellType type) {
        this.type = type;
    }

    /**
     * Перечисление возможных типов клетки на игровом поле.
     */
    public static enum CellType {
        SNAKE_BODY,
        GRID,
        APPLE
    }
}
