package Engine;

// This class is needed to bind keys to threaded function
public class KeyMap{
    private final int key;
    private final Runnable func;

    public KeyMap(int key, Runnable func) {
        this.key = key;
        this.func = func;
    }

    public Runnable getFunc(){
        return func;
    }

    public int getKey(){
        return key;
    }
}
