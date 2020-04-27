package Engine;

public class Action {
    private final Runnable pressed;
    private final Runnable released;

    public Action(Runnable pressed, Runnable released) {
        this.pressed = pressed;
        this.released = released;
    }

    public void pressed(){
        pressed.run();
    }

    public void released(){
        released.run();
    }
}
