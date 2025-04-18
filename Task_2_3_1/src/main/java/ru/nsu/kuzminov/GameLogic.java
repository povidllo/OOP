package ru.nsu.kuzminov;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * Класс для реализации игровой логики.
 */
public class GameLogic implements EventHandler<KeyEvent> {
    private Game game;
    private GridController gridController;

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
//                System.out.println("W");
            }
            case S -> {
                game.setNextDirection(Direction.DOWN);
//                System.out.println("S");
            }
            case A -> {
                game.setNextDirection(Direction.LEFT);
//                System.out.println("A");
            }
            case D -> {
                game.setNextDirection(Direction.RIGHT);
//                System.out.println("D");
            }
            case ENTER -> {
                game.reset();
                gridController.updateGrid();
            }
        }
    }

}
