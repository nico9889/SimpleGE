package Physics;

import Gfx.AnimSprite;
import Gfx.Animation;
import Gfx.Image;
import Gfx.Sprite;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Entity extends AnimSprite {
    public final HitBox hb = new HitBox();
    public ArrayList<Entity> collidable = new ArrayList<>();
    public Physics p = null;
    protected final ArrayList<Sprite> collisions = new ArrayList<>();
    protected boolean in_air = false;

    public Entity(@NotNull Animation animation, int x, int y, int z) {
        super(animation, x, y, z);
    }

    public Entity(@NotNull Animation animation, int x, int y, int z, @NotNull Physics p){
        this(animation, x, y, z);
        this.p = p;
        p.setEntity(this);
    }

    public void autoHitBox(){
        Image img = animation.getFrame(false);
        Rectangle box = new Rectangle(x, y, img.w, img.h);
        hb.add(box);
    }

    public void addCollidable(ArrayList<Entity> collidable){
        this.collidable.addAll(collidable);
    }

    public void addCollidable(Entity e){
        this.collidable.add(e);
    }

    public void removeCollidable(){this.collidable.clear();}

    public void removeCollidable(ArrayList<Entity> collidable){
        this.collidable.removeAll(collidable);
    }

    protected void checkHitbox(int dx, int dy){
        collisions.clear();
        hb.moveBy(dx, dy);
        for(Entity c:collidable){
            if(hb.checkCollision(c.hb)) {
                collisions.add(c);
                if(c.y<this.y){
                    in_air=false;
                }
            }
        }
        if(collisions.isEmpty() && dy != 0)
            in_air=true;
    }
    @Override
    public synchronized boolean moveBy(int dx, int dy){
        checkHitbox(dx, dy);
        if(collisions.isEmpty()){
            super.moveBy(dx, dy);
            return true;
        }
        else{
            hb.moveBy(-dx, -dy);
            return false;
        }
    }

    @Override
    public void paintComponent(Graphics gr) {
        Graphics2D g = (Graphics2D) gr.create();
        Image image = animation.update();
        BufferedImage buffer = image.buffer;
        if ( buffer != null) {
            g.drawImage(buffer, x, win_h-image.h-y, null);
        }
        for(Rectangle rectangle:hb){
            g.drawRect(rectangle.x, win_h-rectangle.height-rectangle.y, rectangle.width, rectangle.height);
        }
    }

    public synchronized ArrayList<Sprite> getCollisions(){
        return collisions;
    }

    @Override
    public String toString(){
        return String.format("Entity@[%d,%d,%d]", x,y,z);
    }
}
