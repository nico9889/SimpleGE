package Test;

import Gfx.AnimSprite;
import Gfx.Image;

public class RunAnimation extends Thread{
    private final AnimSprite s;
    private final Image[] frames;
    private int frame = 0;
    private final int x,y;
    private static int counter = 10;

    public RunAnimation(AnimSprite s, Image[] frames, int x, int y){
        this.s = s;
        this.x = x;
        this.y = y;
        this.frames = frames;
    }

    // FIXME: Ugly workaround to slow down the animation
    //  Missing mutual exclusion
    private static synchronized boolean update(){
        counter--;
        if(counter==0){
            counter = 10;
            return true;
        }
        return false;
    }

    @Override
    public void run(){
        s.move_by(x,y);
        // FIXME
        if(update()){
            s.updateImage(frames[frame]);
            frame++;
            if (frame > 1) {
                frame = 0;
            }
        }
    }
}
