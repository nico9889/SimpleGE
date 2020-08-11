package Physics;

import Gfx.AnimSprite;
import Gfx.Animation;
import Gfx.Image;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Physics.Collision.Side;

public class Entity extends AnimSprite {
    public final HitBox hb = new HitBox();
    public ArrayList<Entity> collidable = new ArrayList<>();
    public Physics p = null;
    protected final ArrayList<Collision> collisions = new ArrayList<>();
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
        hb.setSprite(this);
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

    protected int[] checkHitbox(int dx, int dy){
        collisions.clear();

        for(Entity e:collidable){
            Collision c = hb.checkCollision(e.hb, dx, dy);
            if(c.x!=null || c.y != null) {
                    collisions.add(c);
                dx = c.dx;
                dy = c.dy;
            }
            if (c.y == Side.BOTTOM) {
                in_air = false;
            }
        }
        if(collisions.isEmpty() && dy > 0)
            in_air=true;
        return new int[]{dx, dy};
    }

    @Override
    public synchronized boolean moveBy(int dx, int dy){
        int[] checked = checkHitbox(dx, dy);
        dx = checked[0];
        dy = checked[1];
        x+=dx;
        y+=dy;
        hb.moveBy(dx, dy);
        return true;
    }

    @Override
    public void paintComponent(Graphics gr) {
        Graphics2D g = (Graphics2D) gr.create();
        Image image = animation.update();
        BufferedImage buffer = image.buffer;
        if (buffer != null) {
            g.drawImage(buffer, x, win_h-image.h-y, null);
        }
        if(!test)
            for(Rectangle rectangle:hb){
                g.drawRect(rectangle.x, win_h-rectangle.height-rectangle.y, rectangle.width, rectangle.height);
            }
        else{
            for(Rectangle rectangle:hb){
                g.fillRect(rectangle.x, win_h-rectangle.height-rectangle.y, rectangle.width, rectangle.height);
            }
        }
    }

    public synchronized ArrayList<Collision> getCollisions(){
        return collisions;
    }

    @Override
    public String toString(){
        return String.format("Entity@[%d,%d,%d]", x,y,z);
    }
}
