package Physics;


import java.awt.Rectangle;
import java.util.ArrayList;

public class HitBox extends ArrayList<Rectangle> {
    public HitBox(){
        super();
    }

    public void moveBy(int dx, int dy){
        this.forEach(r -> {
            r.x+=dx;
            r.y+=dy;
        });
    }

    // FIXME: Rectangle shouldn't be move in absolute way
    public void moveTo(int x, int y){
        this.forEach(r -> {
            r.x = x;
            r.y = y;
        });
    }

    public boolean checkCollision(HitBox hc){
        for(Rectangle rt:this){
            for(Rectangle rc:hc )
                if(rt.intersects(rc)){
                    return true;
                }
        }
        return false;
    }
}
