package game;

import constants.Modes;

import java.util.ArrayList;

public class Camera {
    protected double x;
    protected double y;

    protected Hero hero;
    protected double ax; protected double vx;
    protected double ay; protected double vy;

    protected ArrayList<Foe> foeList;

    protected double kx; protected double fx; protected double mx;
    protected double ky; protected double fy; protected double my;

    private long lastTime = 0;

    public Camera(int x, int y, Hero hero, ArrayList<Foe> foeList) {
        this.x = x;
        this.y = y;
        this.hero = hero;

        this.hero.setX_scene((int)hero.getX() - x);
        this.hero.setY_scene((int)hero.getY() - y);

        this.foeList = foeList;

        for(Foe f : foeList) {
            f.setX_scene((int)f.getX() - x);
            f.setY_scene((int)f.getY() - y);
        }

        kx = 1.2; fx = 1.2; mx = 1.0;
        ky = 10.0; fy = 2.0; my = 1.0;

        ax = -kx/mx*(hero.getX_scene() - 100) + fx/mx*vx; vx = 0.0;
        ay = 0; vy = 0;
    }

    public String toString() {
        return x + ", " + y;
    }

    public void update(long time) {
        if(time - lastTime > 16000000L) {
            if((time-lastTime)/16000000L < 10) {

                //* HERO IN SCENE */
                ax = -kx/mx*(hero.getX_scene() - 100) - fx/mx*vx;
                vx += ax * (double)(time - lastTime) / 320000000L;

                ay = +ky/my*(hero.getY_scene() - 235) - fy/my*vy;
                vy += ay * (double)(time - lastTime) / 320000000L;

                hero.MovePos_scene((vx * (double)(time - lastTime) / 320000000L), vy * (double)(time - lastTime) / 320000000L);

                /* FOES IN SCENE */
                for(Foe f : foeList) {
                    f.setSpritePos((int)(f.getX() - x), (int)(f.getY() + y + (95 - f.getSprite().getViewport().getHeight())));

                    if(f.getX_scene() < 800 && f.mode == Modes.WAITING) {
                        f.currentFrameIndex = 0;
                        f.mode = Modes.RISING;
                    }

                    /* DETECTION DES COLLISIONS */
                    if(!hero.isInvincible()) {
                        if (f.getHitBox().intersects(hero.getHitBox())) {
                            hero.takeDamages(1);
                            hero.setInvincibility();
                            System.out.println(hero.getLifePoints());
                        }
                    }
                }


                /* CAMERA */
                x = hero.getX() - hero.getX_scene(); // On modifie la getScene puis on adapte la caméra : on veut faire tendre le getscene vers la pos voulue, on aurait pu aussi faire tendre cam - getscene vers 0 mais plus galere.
                y = -hero.getY() + hero.getY_scene();

                if(Math.abs(hero.getX_scene() - 100) < 0.2) {
                    hero.setX_scene(100);
                } // Arrêt total du ressort.
                if(Math.abs(ay) < 0.3) {
                    hero.setY_scene(235);
                }
            }
            lastTime = time;
        }
    }

    /* GETTERS */
    public double getX() { return x; }
    public double getY() { return y; }
}
