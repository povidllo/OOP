package ru.nsu.kuzminov.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import ru.nsu.kuzminov.utils.Direction;
import ru.nsu.kuzminov.game.Game;

/**
 * Класс для реализации игровой логики.
 */
public class GameLogic implements EventHandler<KeyEvent> {
    protected Game game;
    protected GridController gridController;

    /**
     * Конструктор класса.
     *
     * @param game           объект игры.
     * @param gridController контроллер поля javafx.
     */
    GameLogic(Game game, GridController gridController) {
        this.game = game;
        this.gridController = gridController;
    }

    /**
     * Ручка для обработки клавиш с клавиатуры.
     *
     * @param event событие.
     */
    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case W -> {
                game.setNextDirection(Direction.UP);
            }
            case S -> {
                game.setNextDirection(Direction.DOWN);
            }
            case A -> {
                game.setNextDirection(Direction.LEFT);
            }
            case D -> {
                game.setNextDirection(Direction.RIGHT);
            }
            case ENTER -> {
                game.reset();
                gridController.updateGrid();
            }
            default -> {
                break;
            }
        }
    }

}
