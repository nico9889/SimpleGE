package Engine;

import Gfx.Sprite;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Engine extends TimerTask {
    private final Window window;
    private int scene = 0;
    private final ArrayList<Scene> scenes = new ArrayList<>();
    private Scene now;
    private final String name;
    private final double fps;
    private final double tickrate;
    public boolean stop = false;
    private final Timer t = new Timer();

    /**
     * @param w Width of the game window
     * @param h Height of the game window
     * @param gameName Game name, visualized in window
     */
    public Engine(int w, int h, String gameName){
        window = Window.get(w, h);
        window.setTitle(gameName);
        this.fps = 60.0;
        this.tickrate = 60.0;
        this.name = gameName;
        Sprite.setWindowDim(window.w,window.h);
    }

    /**
     * @param w Width of the game window
     * @param h Height of the game window
     * @param fps Game tick rate
     * @param gameName Game name, visualized in window
     */
    // This constructor permit to override fps value
    public Engine(int w, int h, double fps, double tickrate, String gameName){
        window = Window.get(w, h);
        window.setTitle(gameName);
        this.name = gameName;
        this.fps = fps;
        this.tickrate = tickrate;
        Sprite.setWindowDim(window.w,window.h);
    }


    /**
     * Add a Scene to the Engine so the Engine can handle element in that scene
     * @param s Scene with elements
     */
    public void addScene(Scene s){
        scenes.add(s);
        s.load();
    }

    /**
     * Add a KeyMap to the Engine so the Engine can handle the function bounded to the keys
     * @param map KeyMap with stored key
     */
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
        synchronized (Engine.class){
            if (now != null) {
                for (Sprite s : now.getSprites())
                    window.remove(s);
            }
            if (this.scene < scenes.size()) {
                now = scenes.get(this.scene);
                now.registerHitBoxes();
                window.setTitle(this.name + " - " + now.name);
                this.registerSprites();
                this.scene++;
            }
        }
    }


    /**
     * Start the Engine loop
     */
    public void start(){
        t.schedule(this, 0, (long)(1000.0/tickrate));
        TimerTask ts = new TimerTask(){
            @Override
            public void run(){
                window.repaint();
            }
        };
        t.schedule(ts,0,(long)(1000.0/fps));
    }

    public void stop(){
        t.cancel();
        this.stop=true;
    }

    /**
     * Given a Runnable function execute that in a loop synchronized with
     * the Engine timer
     * @param task Task to execute synchronized with the Engine timer
     */
    public void updater(Runnable task){
        TimerTask ts = new TimerTask(){
            @Override
            public void run(){
                synchronized (Engine.class){
                    task.run();
                }
            }
        };
        t.schedule(ts, 0, (long)(1000.0/tickrate) );
    }

    /**
     * Game loop function. This shouldn't be executed directly! Use start() method instead!
     */
    @Override
    public void run(){
        synchronized (Engine.class) {
            for (Action act : KeyMap.pressed) {
                act.pressed();
            }
            for (Action act : KeyMap.released) {
                act.released();
            }
            KeyMap.released.clear();
        }
    }
}
