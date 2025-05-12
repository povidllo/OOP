package ru.nsu.kuzminov.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import ru.nsu.kuzminov.game.Game;

import java.io.IOException;

/**
 * Класс, предоставляющий контроллер при старте игры.
 */
public class StartGameController {

    @FXML
    TextArea widthArea;

    @FXML
    TextArea heightArea;

    /**
     * Обработчик нажатия на кнопку startButton.
     *
     * @param event событие.
     * @throws IOException исключение может выкинуться при загрузке файла fxml.
     */
    @FXML
    public void startGame(ActionEvent event) throws IOException {
        int width = Integer.parseInt(widthArea.getText());
        int height = Integer.parseInt(heightArea.getText());

        Game game = initGame(width, height);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/nsu/kuzminov/snake-grid.fxml"));
        Parent root = loader.load();
        GridController controller = loader.getController();
        controller.setGame(game, (int) controller.getGridSize() / Math.max(height, width));
        controller.initGame();


        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(new GameLogic(game, controller));
        stage.setScene(scene);
        stage.setTitle("Snake");

        stage.show();
        scene.getRoot().requestFocus();

        ((Stage) widthArea.getScene().getWindow()).close();
    }

    /**
     * Инициирует игру.
     *
     * @param width  ширина поля.
     * @param height длина поля.
     * @return игру.
     */
    public Game initGame(int width, int height) {
        if (!valid(width, height)) {
            throw new IllegalArgumentException("Некорректные входные данные");
        }

        int snakeX = width / 2;
        int snakeY = height / 2;

        Game game = new Game(height, width, snakeX, snakeY);
        return game;
    }

    /**
     * Проверяет, подходит ли размер или нет.
     *
     * @param width  ширина.
     * @param height высота.
     * @return true если подходит, иначе false.
     */
    public boolean valid(int width, int height) {
        return width >= 10 && height >= 10 && width <= 40 && height <= 40;
    }

}
