package Gfx;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimSprite extends Sprite {
    private Image image;

    public AnimSprite(@NotNull Image img, int x, int y, int z){
        super(img, x, y, z);
        image = img;
    }

    public void updateImage(@NotNull Image img){
        this.image = img;
    }

    // TODO: is synchronized needed???
    @Override
    public synchronized void paintComponent(Graphics gr) {
        Graphics2D g = (Graphics2D) gr.create();
        BufferedImage buffer = this.image.getBuffer();
        if ( buffer != null) {
            g.drawImage(buffer, x, super.h-this.image.h-y-39, this);
        }
    }

    @Override
    public String toString(){
        return String.format("AnimSprite[%d,%d,%d]", this.x, this.y, this.z);
    }
}
