package ru.nsu.kuzminov;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Класс, наследующийся от Application предоставляющий приложение.
 */
public class SnakeApplication extends Application {

    private FXMLLoader fxmlLoader;

    // Конструктор по умолчанию
    public SnakeApplication() {
        this.fxmlLoader = new FXMLLoader(getClass().getResource("start-menu.fxml"));
    }

    // Конструктор с возможностью передать FXMLLoader
    public SnakeApplication(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

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
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Snake - меню");

        stage.show();
    }
}
