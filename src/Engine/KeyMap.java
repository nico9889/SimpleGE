package Engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class KeyMap implements KeyListener {
    private static final Hashtable<Integer, Action> map = new Hashtable<>();

    // TODO: check if Engine synchronization is ok
    static Set<Action> pressed = new HashSet<>();
    static Set<Action> released = new HashSet<>();

    public KeyMap(){}


    /**
     * Bind an Action to the key
     * @param key Keyboard key macro
     * @param action Action to bind to the key
     */
    public static void addKey(Integer key, Action action){
        map.put(key, action);
    }

    public static void editKey(Integer key, Action action){
        map.remove(key);
        map.put(key, action);
    }

    public static Action getKey(Integer key){
        return map.get(key);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        synchronized (Engine.class) {
            if (map.containsKey(key))
                pressed.add(map.get(key));
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        synchronized (Engine.class) {   // FIXME: can result in released key skipped!
            if (map.containsKey(key)) {
                released.add(map.get(key));
                pressed.remove(map.get(key));
            }
        }
    }
}
