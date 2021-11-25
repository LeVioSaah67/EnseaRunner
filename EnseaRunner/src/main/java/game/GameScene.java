package game;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.Random;

public class GameScene extends Scene {
    protected Camera camera;
    protected int numberOfLives;

    /* ANIMATED THINGS */
    protected Hero hero;
    protected AnimatedThing dustTrail;
    protected ArrayList<Foe> foeList;

    /* STATIC THINGS */
    protected StaticThing background1;
    protected StaticThing background2;
    protected StaticThing heart1;
    protected StaticThing heart2;
    protected StaticThing heart3;

    /* RETRY BUTTON */
    protected Button retryBtn;

    /* TIMER */
    private AnimationTimer timer = new AnimationTimer() {
        public void handle(long time) {
            hero.update(time);
            dustTrail.update(time);
            camera.update(time);
            update(time);
            for(Foe f : foeList) {
                f.update(time);
            }
        }
    };

    public GameScene(Group root, int w, int h) {
        super(root , w, h);
        numberOfLives = 3;

        /* ANIMATED THINGS */
        hero = new Hero(900, 435, "heros");
        dustTrail = new DustTrail("dustTrail", hero);

        /* STATIC THINGS */
        background1 = new StaticThing("desert2.png", 0, 0, 0, 0, 800, 620);
        background2 = new StaticThing("desert2.png", 800, 0, 0, 0, 800, 620);
        heart1 = new StaticThing("heart.png", 10, 10, 0, 0, 53, 53);
        heart2 = new StaticThing("heart.png", 10 + 53 + 5, 10, 0, 0, 53, 53);
        heart3 = new StaticThing("heart.png", 10 + 2*53 + 5*2, 10, 0, 0, 53, 53);

        /* RETRY BUTTON */
        retryBtn = new Button("Retry");
        retryBtn.setPrefSize(100, 30);
        retryBtn.setTranslateX((w-retryBtn.getPrefWidth())/2);
        retryBtn.setTranslateY((h-retryBtn.getPrefHeight())/2);
        retryBtn.setVisible(false);

        /* ROOT */
        root.getChildren().add(background1.getSprite());
        root.getChildren().add(background2.getSprite());
        root.getChildren().add(hero.getSprite());
        root.getChildren().add(heart1.getSprite());
        root.getChildren().add(heart2.getSprite());
        root.getChildren().add(heart3.getSprite());
        root.getChildren().add(retryBtn);
        root.getChildren().add(dustTrail.getSprite());

        /* FOES */
        Random random = new Random();
        ArrayList<Foe> foeList = new ArrayList<>();
        foeList.add(new Foe(900, 415, "foe"));
        for(int i = 0; i < 10; i++) {
            foeList.add(new Foe(random.nextInt(200) + i*1000 + 2000, 415, "foe"));
            root.getChildren().add(foeList.get(i).getSprite());
        }
        this.foeList = foeList;


        /* CAMERA */
        camera = new Camera(300, 200, hero, foeList);

        /* AUDIO */
        Audio.stop();
        Audio.playSound(".\\src\\snd\\inGame.wav");

        /* EVENTS */
        this.setOnMouseClicked( (event)-> {
            System.out.println("Jump");
            hero.jump();
        });

        retryBtn.setOnAction( e -> {
            System.out.println("retry");
            Main.setGameScene(new GameScene(new Group(), w, h));
            Main.getGameScene().start();
            Main.getStage().setScene(Main.getGameScene());
        });
    }

    /* TIMER */
    public void start() {
        timer.start();
    }
    public void stop() {
        timer.stop();
    }

    /* VISIBLE THINGS */
    public void setRetryBtnVisible() {
        retryBtn.setVisible(true);
    }

    public void setDustTrailVisible(boolean b) {
        this.dustTrail.getSprite().setVisible(b);
    }

    /* UPDATE */
    public void update(long time) {

        /* BACKGROUND */
        background1.SetSpritePos((int)background1.getX()-(int)camera.getX(), (int)background1.getY()+(int)camera.getY());
        background2.SetSpritePos((int)background2.getX()-(int)camera.getX(), (int)background2.getY()+(int)camera.getY());

        if(background1.getX()-(int)camera.getX() < -800) {
            background1.Move(1600, 0);
        }
        if(background2.getX()-(int)camera.getX() < -800) {
            background2.Move(1600, 0);
        }

        /* HEARTS */
        if(hero.getLifePoints() == 2) {
            heart3.getSprite().setViewport(new Rectangle2D(53, 0, 53, 53));
        }
        if(hero.getLifePoints() == 1) {
            heart2.getSprite().setViewport(new Rectangle2D(53, 0, 53, 53));
        }
        if(hero.getLifePoints() == 0) {
            heart1.getSprite().setViewport(new Rectangle2D(53, 0, 53, 53));
        }
    }
}
