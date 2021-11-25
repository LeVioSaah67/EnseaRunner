package game;

public class Foe extends AnimatedThing {

    protected String fileName;
    protected final double x;
    protected final double y;

    public Foe(int x, int y, String fileName) {
        super(fileName);
        this.fileName = fileName;
        this.x = x;
        this.y = y;
    }

    /* GETTERS */
    public double getX() { return x; }
    public double getY() { return y; }
}
