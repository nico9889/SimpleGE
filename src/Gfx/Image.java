package Gfx;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image{
    private final BufferedImage buffer;
    protected int w, h;

    public Image(@NotNull String path) throws IOException{
        try {
            File input = new File(path);
            buffer = ImageIO.read(input);
            w = buffer.getWidth();
            h = buffer.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Image.Image - Error while loading image: " + path);
        }
    }

    public Image(@NotNull File input) throws IOException {
        try {
            buffer = ImageIO.read(input);
            w = buffer.getWidth();
            h = buffer.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Image.Image - Error while loading image: " + input.getName());
        }
    }

    public BufferedImage getBuffer(){
        return buffer;
    }

    @Override
    public String toString(){
        return String.format("Image[%d,%d]", this.w, this.h);
    }
}

