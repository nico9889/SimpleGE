package Physics;

import Gfx.AnimSprite;
import Gfx.Animation;
import Gfx.Image;
import Gfx.Sprite;
import org.jetbrains.annotations.NotNull;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Entity extends AnimSprite {
    public final HitBox hb = new HitBox();
    public ArrayList<Entity> collidable = new ArrayList<>();
    public final ArrayList<Sprite> collisions = new ArrayList<>();

    public Entity(@NotNull Animation animation, int x, int y, int z) {
        super(animation, x, y, z);
    }

    public void autoHitBox(){
        Image img = animation.getFrame(false);
        Rectangle box = new Rectangle(x, y, img.w, img.h);
        hb.add(box);
    }

    public void addCollidable(ArrayList<Entity> collidable){
        this.collidable.addAll(collidable);
    }

    public void removeCollidable(ArrayList<Entity> collidable){
        this.collidable.removeAll(collidable);
    }

    @Override
    public void moveBy(int dx, int dy){
        hb.moveBy(dx, dy);
        for(Entity c:collidable){
            if(hb.checkCollision(c.hb)) collisions.add(c);
        }
        if(collisions.isEmpty()){
            super.moveBy(dx, dy);
        }
        else{
            hb.moveTo(x, y);
        }
        collisions.clear();
    }
}
