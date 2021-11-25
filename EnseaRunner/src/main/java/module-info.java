module game {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;


    opens game to javafx.fxml;
    exports game;
    exports constants;
    opens constants to javafx.fxml;
}