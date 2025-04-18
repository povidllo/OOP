module ru.nsu.kuzminov {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jdk.jfr;
    requires java.desktop;

    // Add TestFX dependencies
    requires org.testfx.junit5;
    requires org.testfx.core;

    opens ru.nsu.kuzminov to javafx.fxml;
    exports ru.nsu.kuzminov;
}
