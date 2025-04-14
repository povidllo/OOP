package ru.nsu.kuzminov;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Класс, наследующийся от Application предоставляющий приложение.
 */
public class SnakeApplication extends Application {
    /**
     * main метод, запускающий приложение.
     *
     * @param args аргументы командной строки.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Метод, начинающий инициализацию приложения.
     *
     * @param stage главная окно.
     * @throws IOException исключение может выкинуться при загрузке файла fxml.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start-menu.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Snake - меню");

        stage.show();

    }
}
