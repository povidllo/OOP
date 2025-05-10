package ru.nsu.kuzminov.controller;

import javafx.scene.input.KeyEvent;
import ru.nsu.kuzminov.game.Game;
import ru.nsu.kuzminov.utils.Direction;

public class OnlineGameLogic extends GameLogic{

    /**
     * Конструктор класса.
     *
     * @param game           объект игры.
     * @param gridController контроллер поля javafx.
     */
    OnlineGameLogic(Game game, GridController gridController) {
        super(game, gridController);
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
                this.game.setNextDirection(Direction.UP);
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
            default -> {
                break;
            }
        }
    }

}
