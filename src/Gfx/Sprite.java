package Gfx;

import org.jetbrains.annotations.NotNull;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

// Single sprite
public class Sprite extends JComponent implements Comparable<Sprite> {
    protected String name = "Sprite";
    private final Image image;
    public int x, y, z;
    protected static int win_w, win_h;

    public boolean test = false;

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

    // Named sprites for easier debug
    public Sprite(@NotNull String path, int x, int y, int z, @NotNull String name) throws IOException {
        image = new Image(path);
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public Sprite(@NotNull Image img, int x, int y, int z, @NotNull String name){
        image = img;
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public static void setWindowDim(int w, int h){
        Sprite.win_w = w;
        Sprite.win_h = h;
    }

    // Relative move
    public boolean moveBy(int dx, int dy){
        x+=dx;
        y+=dy;
        return true;
    }

    public boolean moveBy(int dx, int dy, int dz){
        x+=dx;
        y+=dy;
        z+=dz;  // FIXME: changing Z doesn't change the draw order on the buffer neither the collisions
        return true;
    }

    // Absolute move
    public void moveTo(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean visible(){
        return x <= win_w && y <= win_h;
    }

    public void recolor(double r, double g, double b){
        image.recolor(r,g,b);
    }

    @Override
    public void paintComponent(Graphics gr) {
        Graphics2D g = (Graphics2D) gr.create();
        BufferedImage buffer = image.buffer;
        if ( buffer != null) {
            g.drawImage(buffer, x, win_h - image.h - y, null);
        }
    }

    @Override
    public String toString(){
        return String.format("%s[%d,%d,%d]", this.name ,this.x, this.y, this.z);
    }

    @Override
    public int compareTo(@NotNull Sprite s) {
        return s.z - this.z;
    }
}
