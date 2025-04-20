package ru.nsu.kuzminov;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Класс, предоставляющий контроллер при старте игры.
 */
public class StartGameController {

    @FXML
    private TextArea widthArea;

    @FXML
    private TextArea heightArea;

    /**
     * Обработчик нажатия на кнопку startButton.
     *
     * @param event событие.
     * @throws IOException исключение может выкинуться при загрузке файла fxml.
     */
    @FXML
    private void startGame(ActionEvent event) throws IOException {
        int width = Integer.parseInt(widthArea.getText());
        int height = Integer.parseInt(heightArea.getText());

        if (!valid(width, height)) {
            throw new IllegalArgumentException("Некорректные входные данные");
        }

        int snakeX = width / 2;
        int snakeY = height / 2;

        Game game = new Game(height, width, snakeX, snakeY);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("snake-grid.fxml"));
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
     * Проверяет, подходит ли размер или нет.
     *
     * @param width  ширина.
     * @param height высота.
     * @return true если подходит, иначе false.
     */
    private boolean valid(int width, int height) {
        return width >= 10 && height >= 10 && width <= 40 && height <= 40;
    }
}
