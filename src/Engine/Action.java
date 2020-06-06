package Engine;

public class Action {
    private final Runnable pressed;
    private final Runnable released;


    /**
     * Build an Action given two Runnable
     * @param pressed Runnable function executed on key pressed
     * @param released  Runnable function executed on key released
     */
    public Action(Runnable pressed, Runnable released) {
        this.pressed = pressed;
        this.released = released;
    }

    /**
     * Build an Action given one Runnable. This does nothing on key release.
     * @param pressed Runnable function executed on key pressed
     */
    public Action(Runnable pressed){
        this.pressed = pressed;
        this.released = null;
    }

    protected void pressed(){
        if(pressed!=null) {
            synchronized (Engine.class) {
                pressed.run();
            }
        }
    }

    protected void released(){
        if(released!=null) {
            synchronized (Engine.class) {
                released.run();
            }
        }
    }
}
