package gdd.sprite;

public class Alien2 extends Enemy{

    public Alien2(int x, int y) {
        super(x, y);
    }

    @Override
    public void act(int direction) {
        this.x += direction;
        this.y += 1;
    }

}
