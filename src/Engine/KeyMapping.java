package Engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class KeyMapping extends HashSet<KeyMap> implements KeyListener {
    static Set<Integer> pressed = null;

    public KeyMapping(){
        pressed = new HashSet<>();
    }

    public void addKey(Character key, Runnable func){
        this.add(new KeyMap(key, func));
    }

    public void addKey(KeyMap km){
        this.add(km);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public synchronized void keyPressed(KeyEvent keyEvent) {
        int pr = keyEvent.getKeyCode();
        pressed.add(pr);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        pressed.remove(key);
    }
}
