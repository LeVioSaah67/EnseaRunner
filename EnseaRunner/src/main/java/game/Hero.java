package game;

import constants.Modes;
import javafx.scene.image.Image;

public class Hero extends AnimatedThing {
    private final String fileName;
    private String currentName;

    private double x;
    private double y;

    private int lifePoints;
    private double invincibility;

    protected final double gravity = 230;
    protected final double vyRush = 280;
    protected double ay; protected double vy;
    protected double ax; protected double vx;

    protected long lastTime;

    public Hero(int x, int y, String fileName) {
        super(fileName);
        this.fileName = fileName + ".png";
        currentName = fileName;

        this.x = x;
        this.y = y;

        lifePoints = 3;

        ax = 0; vx = 50;
        ay = 0.0; vy = 0.0;

    }


    public void update(long time) {
        super.update(time);
        if(time - lastTime > 16000000L) {
            if((time-lastTime)/16000000L > 5) {
                lastTime = time;
            }

            // Decrease invincibility :
            if(isInvincible()) {
                invincibility -= (time - lastTime);

                /* BLINKING DURING INVINCIBILITY */
                if(currentName.equals("heros.png")) { // VERIFIER LE .PNG
                    currentName = "herosHurt.png";
                }
                else {
                    currentName = "heros.png";
                }
                sprite.setImage(new Image("file:.\\src\\img\\" + currentName));
            }

            if(invincibility < 0) {
                invincibility = 0;
                sprite.setImage(new Image("file:.\\src\\img\\" + fileName));
            }

            /* X */
            vx += ax*(time-lastTime)/160000000L;
            x += vx*(time-lastTime)/160000000L;

            if(isDead()) {
                if(mode != Modes.DYING) { // Pour la première fois où on y rente.
                    if(mode != Modes.JUMPING_UP && mode != Modes.JUMPING_DOWN) {
                        ay = gravity;
                        vy -= 200;
                    }
                    mode = Modes.DYING;
                    ax = -3;
                }

                vy += ay*(double)(time - lastTime)/320000000L;
                y += vy*(double)(time - lastTime)/320000000L;

                if(vx < 0) {
                    vx = 0;
                    Main.getGameScene().stop();
                    Main.getGameScene().setRetryBtnVisible();
                }
            }

            /* Y */
            if(mode == Modes.JUMPING_UP || mode == Modes.JUMPING_DOWN || mode == Modes.DYING) {
                vy += ay * (double) (time - lastTime) / 320000000L;
                if(Math.abs(y - 435) < 0.01) { // Jump has just started
                    this.MovePos_scene(0, vy * (double) (time - lastTime) / 320000000L);
                }
                y += vy * (double) (time - lastTime) / 320000000L;

                if(vy > -5 && mode != Modes.DYING) {
                    mode = Modes.JUMPING_DOWN;
                }
            }

            if(y > 435.1) {
                ay = 0;
                vy = 0;
                y = 435; // Can't be under the ground.
                Main.getGameScene().setDustTrailVisible(true);

                if(mode != Modes.DYING)
                    mode = Modes.RUNNING; // not any effect of gravity : hero starts running again.
            }

            lastTime = time;
        }
    }

    public void jump() {
        if(mode == Modes.RUNNING) {
            Main.getGameScene().setDustTrailVisible(false);
            Audio.playSound(".\\src\\snd\\jump.wav");
            mode = Modes.JUMPING_UP;
            ay = gravity;
            vy = -vyRush;
        }
    }

    public void takeDamages(int d) {
        lifePoints -= d;
        if(lifePoints < 0) {
            lifePoints = 0;
        }
        Audio.playSound(".\\src\\snd\\hurt.wav");
    }

    public boolean isInvincible() {
        return (invincibility > 0);
    }

    public boolean isDead() {
        return lifePoints <= 0;
    }

    /* GETTERS */
    public double getX() { return x; }
    public double getY() { return y; }
    public int getLifePoints() { return lifePoints; }

    /* SETTERS */
    public void setInvincibility() {
        invincibility = 1000000000L;
    }
}

