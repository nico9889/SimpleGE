package Engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class KeyMap implements KeyListener {
    private static final Dictionary<Integer, Action> map = new Hashtable<Integer, Action>();
    static Set<Action> pressed = new HashSet<>();
    static Set<Action> released = new HashSet<>();

    public KeyMap(){}

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
    public synchronized void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        pressed.add(map.get(key));
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        released.add(map.get(key));
        pressed.remove(map.get(key));
    }
}
