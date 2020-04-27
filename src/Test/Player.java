package Test;

import Gfx.AnimSprite;
import Gfx.Animation;
import Gfx.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends AnimSprite {
    private final Image spr1 = new Image("resources/sprites/spraterino1.png");
    private final Image spr2 = new Image("resources/sprites/spraterino2.png");
    private final Animation walk = new Animation(new Image[]{spr1, spr2},2);
    private final Animation idle = new Animation(new Image[]{spr1},0);
    public String status = "idle";

    public Player(Animation idle, int x, int y, int z) throws IOException {
        super(idle, x, y, z);
    }

    @Override
    public void moveBy(int dx, int dy){
        super.animation = walk;
        x+=dx;
        y+=dy;
    }

    public void idle(){
        super.animation = idle;
    }
}
