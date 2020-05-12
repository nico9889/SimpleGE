package Gfx;

import org.jetbrains.annotations.NotNull;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class AnimSprite extends Sprite {
    protected Animation animation;

    public AnimSprite(@NotNull Animation animation, int x, int y, int z){
        super(animation.getFrame(false), x, y, z);
        this.animation = animation;
    }

    public AnimSprite(@NotNull Animation animation, int x, int y, int z, String name){
        super(animation.getFrame(false), x, y, z);
        this.animation = animation;
        super.name = name;
    }


    // TODO: is synchronized needed???
    @Override
    public synchronized void paintComponent(Graphics gr) {
        Graphics2D g = (Graphics2D) gr.create();
        Image image = animation.update();
        BufferedImage buffer = image.buffer;
        if ( buffer != null) {
            g.drawImage(buffer, x, win_h-image.h-y, this);
        }
    }

    @Override
    public String toString(){
        return String.format("AnimSprite(%s)[%d,%d,%d]", super.name, this.x, this.y, this.z);
    }
}
