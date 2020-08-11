package Physics;

import java.awt.Rectangle;
import java.util.ArrayList;

import Gfx.Sprite;
import Physics.Collision.*;

public class HitBox extends ArrayList<Rectangle> {
    private Sprite s;

    public HitBox(){
        super();
    }

    protected void setSprite(Sprite s){
        this.s = s;
    }

    public void moveBy(int dx, int dy){
        this.forEach(r -> {
            r.x+=dx;
            r.y+=dy;
        });
    }

    public Collision checkCollision(HitBox hc, int dx, int dy){
        for(Rectangle rt:this){
            int next_x = rt.x + dx;
            int next_y = rt.y + dy;
            for(Rectangle rc:hc ) {
                if (next_x < rc.x+rc.width && rc.x < next_x+rt.width)
                    if (next_y < rc.y+rc.height && rc.y < next_y+rt.height) {
                        Side hit_x = null;
                        Side hit_y = null;
                        int left_x=0, left_y=0;
                        if(rt.y + rt.height <= rc.y && dy>0) {
                            hit_y = Side.TOP;
                            // left_y = rc.y - (next_y + rt.height);    FIXME
                        }
                        else if(rt.y >= rc.y+rc.height && dy<0) {
                            hit_y = Side.BOTTOM;
                            left_y = (rc.y + rc.height) - next_y;
                        }
                        if(rt.x < rc.x+rc.width && dx>0) {
                            hit_x = Side.RIGHT;
                            left_x = rc.x - (next_x +rt.width);
                        }
                        else if(rt.x + rt.width > rc.x && dx<0) {
                            hit_x = Side.LEFT;
                            left_x = (rc.x + rc.width) - next_x;
                        }
                        if(left_x==-dx) left_x = 0;
                        if(left_y==-dy) left_y = 0;
                        return new Collision(hit_x, hit_y, hc.s, left_x, left_y);
                    }
            }
        }
        return new Collision(null, null, s, dx, dy);
    }
}
