package Gfx;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public class Image{
    public final BufferedImage buffer;
    public final int w, h;

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

    public void recolor(double r_value, double g_value, double b_value){
        int width = buffer.getWidth();
        int height = buffer.getHeight();
        WritableRaster raster = buffer.getRaster();

        assert(r_value>=0 && r_value <256);
        assert(g_value>=0 && g_value <256);
        assert(b_value>=0 && b_value <256);
        Function<Double, Integer> trim = (pixel) -> {
            if(pixel<0)
                return 0;
            else if(pixel>255)
                return 255;
            return pixel.intValue();
        };
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int[] pixels = raster.getPixel(x, y, (int[]) null);
                pixels[0] = trim.apply(pixels[0] * r_value);
                pixels[1] = trim.apply(pixels[1] * g_value);
                pixels[2] = trim.apply(pixels[2] * b_value);
                raster.setPixel(x, y, pixels);
            }
        }
    }
    @Override
    public String toString(){
        return String.format("Image[%d,%d]", this.w, this.h);
    }
}

