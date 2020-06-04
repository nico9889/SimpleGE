package Engine;

import Gfx.Sprite;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Engine extends Thread{
    private final Window window;
    private int scene = 0;
    private final ArrayList<Scene> scenes = new ArrayList<>();
    private Scene now;
    private final String name;
    private double fps = 60.0;
    static ReentrantLock lock = new ReentrantLock();
    public boolean stop = false;


    public Engine(int w, int h, String gameName){
        window = Window.get(w, h);
        window.setTitle(gameName);
        this.name = gameName;
        Sprite.setWindowDim(window.w,window.h);
    }

    // This constructor permit to override fps value
    public Engine(int w, int h, double fps, String gameName){
        window = Window.get(w, h);
        window.setTitle(gameName);
        this.name = gameName;
        this.fps = fps;
        Sprite.setWindowDim(window.w,window.h);
    }

    public void addScene(Scene s){
        scenes.add(s);
    }

    public void addKeyMap(KeyMap map){
        window.addKeyMapping(map);
    }

    private void registerSprites() {
        for(Sprite sprite:now.getSprites()){
            window.add(sprite);
            window.revalidate();
        }
    }

    public void nextScene(){
        if(now!=null){
            for(Sprite s:now.getSprites())
                window.remove(s);
        }
        if(this.scene<scenes.size()) {
            now = scenes.get(this.scene);
            now.load();
            now.registerHitBoxes();
            window.setTitle(this.name + " - " + now.name);
            this.registerSprites();
            this.scene++;
        }
        System.out.println(now);
    }

    private void update() throws InterruptedException {
        Thread.sleep((long)((1.0/this.fps)*1000.0));
        try {
            lock.lock();
            for (Action act : KeyMap.pressed) {
                act.pressed();
            }
            for (Action act : KeyMap.released) {
                act.released();
            }
            KeyMap.released.clear();
            window.repaint();
        }
        finally{
            lock.unlock();
        }
    }

    @Override
    public void run(){
        while(!stop){
            try {
                update();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
