module ru.nsu.kuzminov {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jdk.jfr;
    requires java.desktop;

    opens ru.nsu.kuzminov to javafx.fxml;
    exports ru.nsu.kuzminov;
}