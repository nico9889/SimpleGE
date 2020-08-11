package Test.SideScrolling;

import Gfx.Animation;
import Gfx.Image;
import Gfx.Sprite;
import Physics.Entity;
import Physics.Physics;

import java.io.File;

public class Player extends Entity{
    private final Image[] walk_left_anim = Main.loadSprites(new File("resources/sprites/sonic/run_left"), player,1./player,2-player);
    private final Image[] walk_right_anim = Main.loadSprites(new File("resources/sprites/sonic/run_right"), player,1./player,2-player);
    private final Image[] air_file = Main.loadSprites(new File("resources/sprites/sonic/air"), player,1./player,2-player);
    private static int player = 1;
    private final Animation walk_left = new Animation(walk_left_anim,1);
    private final Animation walk_right = new Animation(walk_right_anim,1);
    private final Animation air = new Animation(air_file, 1);
    private boolean last_air = false;
    private final Animation idle;
    private int air_distance = 0;

    public Player(Animation idle, int x, int y, int z){
        super(idle, x, y, z);
        this.idle = idle;
        player++;
    }

    public Player(Animation idle, int x, int y, int z, Physics p){
        super(idle, x, y, z, p);
        this.idle = idle;
        player++;
    }

    private void setAnimation(int dx, int dy){
        if(dx>0 && !in_air)
            super.animation = walk_right;
        else if(dx<0 && !in_air)
            super.animation = walk_left;
        else if(in_air) {
            last_air=in_air;
            super.animation = air;
        }
        if(last_air!=in_air){
            last_air=in_air;
            idle();
        }
    }

    @Override
    public boolean moveBy(int dx, int dy){
        if(air_distance>200 && dy>0)
            dy=0;
        int[] checked = checkHitbox(dx, dy);
        setAnimation(dx, dy);
        dx = checked[0];
        dy = checked[1];
        if(in_air) {
            if (dy > 0)
                air_distance = air_distance + Math.abs(dx) + dy;
        }
        else
            air_distance=0;

        x+=dx;
        y+=dy;
        hb.moveBy(dx, dy);
        return true;

    }

    public void idle(){
        super.animation = idle;
    }
}
