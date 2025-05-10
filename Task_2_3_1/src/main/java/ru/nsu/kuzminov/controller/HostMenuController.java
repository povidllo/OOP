package ru.nsu.kuzminov.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.kuzminov.game.OnlineGameHost;

import java.io.IOException;

public class HostMenuController {

    OnlineGameHost game;

    @FXML
    public void startBtn(ActionEvent event) {
        game.lock.lock();
        System.out.println("login");
        game.setStartGame();
        game.gameStatusCondition.signalAll();
        game.lock.unlock();

        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/nsu/kuzminov/snake-grid.fxml"));
//            Parent gameRoot = loader.load();
//
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            Scene scene = stage.getScene();
//            scene.setRoot(gameRoot);
//            stage.sizeToScene();
//
//            stage.show();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/nsu/kuzminov/online-snake-grid.fxml"));
            Parent root = loader.load();
            GridController controller = loader.getController();
            controller.setGame(game, (int) controller.getGridSize() / 20);
            controller.initGame();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = stage.getScene();
            scene.setOnKeyPressed(new OnlineGameLogic(game, controller));
            scene.setRoot(root);
            stage.sizeToScene();

            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setGame(OnlineGameHost game) {
        this.game = game;
    }
}
