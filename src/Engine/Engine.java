package Engine;

import Gfx.Sprite;
import Utils.UpdaterHandler;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Engine extends TimerTask {
    private final Window window;
    private int scene = 0;
    private final ArrayList<Scene> scenes = new ArrayList<>();
    private Scene now;
    private final String name;
    private double fps = 60;
    private double tickrate = 60;
    public boolean stop = false;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    /**
     * @param w Width of the game window
     * @param h Height of the game window
     * @param gameName Game name, visualized in window
     */
    public Engine(int w, int h, boolean fullscreen, String gameName){
        window = Window.get(w, h, fullscreen);
        window.setTitle(gameName);
        this.name = gameName;
        Sprite.setWindowDim(window.w,window.h);
    }

    /**
     * This constructor permit to override fps value
     * @param w Width of the game window
     * @param h Height of the game window
     * @param fps Game tick rate
     * @param gameName Game name, visualized in window
     */
    public Engine(int w, int h, double fps, double tickrate, boolean fullscreen, String gameName){
        this(w, h, fullscreen, gameName);
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
            if (this.scene < scenes.size()) {
                if (now != null) {
                    for (Sprite s : now.getSprites())
                        window.remove(s);
                }
                now = scenes.get(scene);
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
        // t.schedule(this, 0, (long)(1000.0/tickrate));
        scheduler.scheduleAtFixedRate(window::repaint, 0, (long)(1000.0/fps), TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(this, 0, (long)(1000.0/tickrate), TimeUnit.MILLISECONDS);
        // t.schedule(ts,0,(long)(1000.0/fps));
    }

    public void stop(){
        this.stop=true;
        window.close();
    }

    /**
     * Given a Runnable function execute that in a loop synchronized with
     * the Engine timer
     * @param task Task to execute synchronized with the Engine timer
     */
    public UpdaterHandler updater(Runnable task){
        ScheduledFuture<?> ts = scheduler.scheduleAtFixedRate(task, 0, (long)(1000.0/tickrate), TimeUnit.MILLISECONDS);
        return new UpdaterHandler(ts);
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
