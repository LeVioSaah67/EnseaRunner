package game;

public class DustTrail extends AnimatedThing {

    protected final long framePerSecond;
    protected Hero hero;

    private long lastTime = 0;

    public DustTrail(String fileName, Hero hero) {
        super(fileName);
        framePerSecond = 20L;
        this.hero = hero;
    }

    public void update(long time) {
        super.update(time);
        if(time - lastTime > 1000000000L / framePerSecond) {
            this.getSprite().setX(hero.getX_scene() - 50);
            this.getSprite().setY(hero.getY_scene() + 55); // EFFACER QUAND HEROS SAUTE
        }
    }

    public Hero getHero() { return hero; }
}
