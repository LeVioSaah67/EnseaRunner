package game;

import constants.Sprites;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage stage;
    private static WelcomeScene welcomeScene;
    private static GameScene gameScene;

    public void start(Stage primaryStage){

        stage = primaryStage;

        Sprites.Init();

        primaryStage.setTitle("MORTAL RUNNER");
        primaryStage.setResizable(false);
        Group welcome = new Group();
        gameScene = new GameScene(new Group(), 600, 400);
        welcomeScene = new WelcomeScene(welcome, 600, 400);
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

    /* GETTERS */
    public static Stage getStage() { return stage; }
    public static GameScene getGameScene() { return gameScene; }

    /* SETTERS */
    public static void setGameScene(GameScene newGameScene) {
        gameScene = newGameScene;
    }
}
