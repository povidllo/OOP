module ru.nsu.kuzminov.task_2_3_1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jdk.jfr;
    requires java.desktop;

    opens ru.nsu.kuzminov to javafx.fxml;
    exports ru.nsu.kuzminov;
}