package ru.nsu.kuzminov.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import ru.nsu.kuzminov.game.Game;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

public class OnlineGridController extends GridController {
    @FXML
    public Label player1Label;

    @FXML
    public Label player2Label;

    @FXML
    public Label player3Label;

    @FXML
    public Label player4Label;

    private AtomicInteger[] scores;

    public void updateScore() {
        try {
            Class<?> clazz = this.game.getClass();
            Method method = clazz.getDeclaredMethod("getScores");

            scores = (AtomicInteger[]) method.invoke(this.game);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread thread = new Thread(() -> {
            while (true) {
                Platform.runLater(() -> {
                    if (this.scores != null) {
                        if (scores.length > 0) player1Label.setText(String.valueOf(scores[0].get()));
                        if (scores.length > 1) player2Label.setText(String.valueOf(scores[1].get()));
                        if (scores.length > 2) player3Label.setText(String.valueOf(scores[2].get()));
                        if (scores.length > 3) player4Label.setText(String.valueOf(scores[3].get()));
                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Метод для установления объекта игры и его параметров.
     *
     * @param game     объект игры.
     * @param cellSize размер одной клетки поля в пикселях.
     */
    @Override
    public void setGame(Game game, int cellSize) {
        this.game = game;
        this.cellSize = cellSize;
        this.updateScore();
    }



}
