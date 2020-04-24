package Gfx;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// Single sprite
public class Sprite extends JComponent implements Comparable<Sprite> {
    private final Image image;
    protected int x, y, z;
    protected int w, h;

    public Sprite(@NotNull String path, int x, int y, int z) throws IOException {
        image = new Image(path);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Sprite(@NotNull Image img, int x, int y, int z){
        image = img;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setWindowDim(int w, int h){
        this.w = w;
        this.h = h;
    }

    // Relative move
    public void move_by(int x, int y){
        this.x = this.x + x;
        this.y = this.y + y;
    }

    // Absolute move
    public void move_to(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void move_by(int x, int y, int z){
        this.x = this.x + x;
        this.y = this.y + y;
        this.z = this.z + z;
    }

    public boolean visible(){
        return x <= w && y <= h;
    }

    @Override
    public void paintComponent(Graphics gr) {   // This method get called at every JFrame refresh
        Graphics2D g = (Graphics2D) gr.create();
        BufferedImage buffer = image.getBuffer();
        if ( buffer != null) {
            g.drawImage(buffer, x, this.h-image.h-y, this);
        }
    }

    @Override
    public String toString(){
        return String.format("Sprite[%d,%d,%d]", this.x, this.y, this.z);
    }

    @Override
    public int compareTo(@NotNull Sprite s) {
        return this.z - s.z;
    }
}
