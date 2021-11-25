package game;

import constants.Modes;
import constants.Sprites;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class AnimatedThing {
    protected double x_scene;
    protected double y_scene; // Position par rapport à l'écran
    protected ImageView sprite;
    protected int mode;

    protected Rectangle2D hitBox;

    protected int currentFrameIndex;
    protected final long framePerSecond;
    protected int maxFrameIndex;
    protected int frameOffset;

    private long lastTime = 0;

    private String type;

    public AnimatedThing(String fileName) {

        type = fileName;

        x_scene = -100;
        y_scene = -100;

        Image spriteSheet = new Image("file:.\\src\\img\\" + fileName + ".png");
        sprite = new ImageView(spriteSheet);
        sprite.setViewport(new Rectangle2D(15,0,75,100));
        sprite.setX(x_scene);
        sprite.setY(y_scene);

        hitBox = new Rectangle2D(0, 0, 0, 0);

        currentFrameIndex = 0;
        framePerSecond = 13L;
        maxFrameIndex = 5;
        frameOffset = 84;

        if(fileName.equals("heros")) {
            System.out.println("coucou");
            mode = Modes.RUNNING;
        }
        else if(fileName.equals("foe")) {
            mode = Modes.WAITING;
            sprite.setScaleX(1.3);
            sprite.setScaleY(1.3);
        }
    }

    public void update(long time) {
        if(time - lastTime > 1000000000L / framePerSecond) {

            if(type.equals("heros")) {
                if (mode == Modes.RUNNING) {
                    currentFrameIndex++;
                    if (currentFrameIndex >= Sprites.heroRects.get(0).size()) {
                        currentFrameIndex = 0;
                    }
                    sprite.setViewport(Sprites.heroRects.get(0).get(currentFrameIndex));
                    hitBox = new Rectangle2D(x_scene, y_scene, Sprites.heroRects.get(0).get(currentFrameIndex).getWidth(), Sprites.heroRects.get(0).get(currentFrameIndex).getHeight());
                } else if (mode == Modes.JUMPING_UP) {
                    sprite.setViewport(Sprites.heroRects.get(1).get(0));
                    hitBox = new Rectangle2D(x_scene, y_scene, Sprites.heroRects.get(1).get(0).getWidth(), Sprites.heroRects.get(1).get(0).getHeight());
                } else if (mode == Modes.JUMPING_DOWN) {
                    sprite.setViewport(Sprites.heroRects.get(1).get(1));
                    hitBox = new Rectangle2D(x_scene, y_scene, Sprites.heroRects.get(1).get(1).getWidth(), Sprites.heroRects.get(1).get(1).getHeight());
                } else if(mode == Modes.DYING) {
                    sprite.setViewport(Sprites.heroRects.get(2).get(0));
                }
            }
            else if(type.equals("foe")) {
                if (mode == Modes.STANDING) {
                    currentFrameIndex++;
                    if (currentFrameIndex >= Sprites.foeRects.get(1).size()) {
                        currentFrameIndex = 0;
                    }
                    sprite.setViewport(Sprites.foeRects.get(1).get(currentFrameIndex));
                    hitBox = new Rectangle2D(x_scene, y_scene, Sprites.foeRects.get(1).get(currentFrameIndex).getWidth(), Sprites.foeRects.get(1).get(currentFrameIndex).getHeight());
                } else if (mode == Modes.RISING) {
                    currentFrameIndex++; // Mettre à -1 quand on passe au rising !
                    if (currentFrameIndex >= Sprites.foeRects.get(0).size()) {
                        currentFrameIndex = -1;
                        mode = Modes.STANDING;
                    }
                    else {
                        sprite.setViewport(Sprites.foeRects.get(0).get(currentFrameIndex));
                    }
                } else if (mode == Modes.DYING) {
                    currentFrameIndex++; // Mettre à 0 quand il meurt
                    if (currentFrameIndex >= Sprites.foeRects.get(2).size()) {
                        currentFrameIndex = 0; // Arreter l'animation
                    }
                    sprite.setViewport(Sprites.foeRects.get(2).get(currentFrameIndex));
                    hitBox = new Rectangle2D(x_scene, y_scene, Sprites.foeRects.get(2).get(currentFrameIndex).getWidth(), Sprites.foeRects.get(2).get(currentFrameIndex).getHeight());
                }
            }
            else if(type.equals("dustTrail")) {
                currentFrameIndex++;
                if (currentFrameIndex >= Sprites.dustTrailRects.size()) {
                    currentFrameIndex = 0;
                }
                sprite.setViewport(Sprites.dustTrailRects.get(currentFrameIndex));
            }

            lastTime = time;
        }
    }

    // Getters :
    public ImageView getSprite() { return sprite; }
    public double getX_scene() { return x_scene; }
    public double getY_scene() { return y_scene; }

    // Setters :
    public void setX_scene(int x) {
        x_scene = x;
        sprite.setX(x_scene);
    }
    public void setY_scene(int y) {
        y_scene = y;
        sprite.setY(y_scene);
    }
    public void setSpritePos(int x, int y) {
        //???
        x_scene = x;
        y_scene = y;

        sprite.setX(x);
        sprite.setY(y);
    }
    public void MovePos_scene(double x, double y) {
        x_scene += (int)x;
        sprite.setX(x_scene);
        y_scene -= (int)y;
        sprite.setY(y_scene);
    }

    public Rectangle2D getHitBox() {
        return hitBox;
    }

}
