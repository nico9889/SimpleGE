package Gfx;

public class Animation {
    private final Image[] frames;
    private int frame = 0;
    private int counter = 0;
    private final int update;

    public Animation(Image[] frames, int update) {
        this.frames = frames;
        this.update = update;
    }

    // Get the next Image if the counter get to zero
    public Image update(){
        if(update > 0) {
            if (counter == 0) {
                counter = update;
                frame = frame + 1;
                if (frame >= frames.length) {
                    frame = 0;
                }
                return frames[frame];
            }
            counter--;
        }
        return frames[frame];
    }

    // Forcefully get frame with optional frame update
    public Image getFrame(boolean update){
        if(update){
            frame = frame + 1;
            if(frame>=frames.length) {
                frame = 0;
            }
        }
        return frames[frame];
    }

    // Reset animation to the first frame. If this is not called the animation starts from the last frame showed by
    //  update FIXME: I may need to improve my english.
    public void reset(){
        counter = 0;
        frame = 0;
    }
}
