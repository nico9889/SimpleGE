package Test.SideScrolling;

import Gfx.Animation;
import Gfx.Image;
import Physics.Entity;

import java.io.File;
import java.io.IOException;

public class Player extends Entity {
    private final Image[] walk_left_anim = Main.loadSprites(new File("resources/sprites/player/run_left"));
    private final Image[] walk_right_anim = Main.loadSprites(new File("resources/sprites/player/run_right"));
    private final Animation walk_left = new Animation(walk_left_anim,2);
    private final Animation walk_right = new Animation(walk_right_anim,2);
    private final Animation idle;

    public Player(Animation idle, int x, int y, int z) throws IOException {
        super(idle, x, y, z);
        this.idle = idle;
    }

    @Override
    public void moveBy(int dx, int dy){
        if(dx>0)
            super.animation = walk_right;
        else if(dx<0)
            super.animation = walk_left;
        collisions.clear(); // FIXME missing mutual exclusion, collisions cleared before reading
        hb.moveBy(dx, dy);
        for(Entity c:collidable){
            if(hb.checkCollision(c.hb)) collisions.add(c);
        }
        if(collisions.isEmpty()){
            super.moveBy(dx, dy);
        }
        else{
            hb.moveBy(-dx, -dy);
        }
    }

    public void idle(){
        super.animation = idle;
    }
}
