package Engine;

public class Action {
    private final Runnable pressed;
    private final Runnable released;

    public Action(Runnable pressed, Runnable released) {
        this.pressed = pressed;
        this.released = released;
    }

    public Action(Runnable pressed){
        this.pressed = pressed;
        this.released = null;
    }

    public void pressed(){
        if(pressed!=null)
            pressed.run();
    }

    public void released(){
        if(released!=null)
            released.run();
    }
}
