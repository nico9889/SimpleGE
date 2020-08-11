package Physics;

import Gfx.Sprite;

public class Collision {
    public enum Side{
        BOTTOM,
        LEFT,
        RIGHT,
        TOP
    }
    public final Sprite sprite;
    public final Side x, y;
    public final int dx,dy;

    public Collision(Side x, Side y, Sprite sprite, int dx, int dy){
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public String toString(){
        return String.format("Collision[%s]: (%s, %s) (%d, %d)", sprite, x, y, dx, dy);
    }
}
