package game;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StaticThing {
    protected ImageView sprite;
    protected double x;
    protected double y;

    public StaticThing(String fileName, double x, double y, int x_vp, int y_vp, int w_vp, int h_vp) {
        this.x = x;
        this.y = y;

        Image spriteSheet = new Image("file:.\\src\\img\\" + fileName);
        sprite = new ImageView(spriteSheet);
        sprite.setViewport(new Rectangle2D(x_vp, y_vp, w_vp, h_vp));
        sprite.setX(x);
        sprite.setY(y);
    }

    public void SetSpritePos(int x, int y) {
        sprite.setX(x);
        sprite.setY(y);
    }

    public void Move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    // Getters :
    public ImageView getSprite() {
        return sprite;
    }
    public double getX() { return x; }
    public double getY() { return y; }
}
