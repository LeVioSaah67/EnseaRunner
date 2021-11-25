package game;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;


public class WelcomeScene extends Scene {

    protected StaticThing picture;
    protected Button startBtn;

    public WelcomeScene(Group root, int w, int h) {
        super(root, w, h);
        picture = new StaticThing("welcomePicture.png", 0, 0, 0, 0, 600, 400);

        startBtn = new Button("Start running");
        startBtn.setPrefSize(200, 50);
        startBtn.setTranslateX((w - startBtn.getPrefWidth())/2);
        startBtn.setTranslateY((h - startBtn.getPrefHeight())/2);

        root.getChildren().add(picture.getSprite());
        root.getChildren().add(startBtn);

        startBtn.setOnAction( (event)-> {
            System.out.println("Le jeu démarre.");
            Main.getStage().setScene(Main.getGameScene());
            Main.getGameScene().start();
        });
    }
}
