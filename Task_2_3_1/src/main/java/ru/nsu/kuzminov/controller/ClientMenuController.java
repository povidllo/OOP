package ru.nsu.kuzminov.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.kuzminov.game.OnlineGameClient;

import java.io.IOException;

public class ClientMenuController {

    private OnlineGameClient game;

    public void processClient(Stage curStage, OnlineGameClient game) {
        this.game = game;

        new Thread(() -> {
            try {
                game.lock.lock();
                game.gameStartCondition.await();
                game.lock.unlock();

                Platform.runLater(() -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/nsu/kuzminov/online-snake-grid.fxml"));
                        Parent root = loader.load();
                        GridController controller = loader.getController();
                        controller.setGame(game, (int) controller.getGridSize() / 20);
                        controller.initGame();

                        Scene scene = curStage.getScene();
                        scene.setOnKeyPressed(new OnlineGameLogic(game, controller));
                        scene.setRoot(root);
                        curStage.sizeToScene();
                        curStage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

}
