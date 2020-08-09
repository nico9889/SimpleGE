package Engine;

import Gfx.Sprite;

import java.util.ArrayList;

public class Engine{
    private final Task main, repaint;
    private final Window window;
    private int scene = 0;
    private final ArrayList<Scene> scenes = new ArrayList<>();
    private Scene now;
    private final String name;
    private double fps = 60, tickrate = 60;
    public boolean stop = false;
    private final ArrayList<Task> pool = new ArrayList<>();

    protected static class Task{
        private final Thread t;
        private final Runnable r;
        private final double tick;
        private final int id;
        private static int counter = 0;
        private final String name;
        private boolean stop = false;

        public Task(Runnable r, double tick, String name){
            this.t = new Thread(() -> {
                try {
                    while(!this.stop) {
                        synchronized(Engine.class) {
                            r.run();
                        }
                        Thread.sleep((long) (1000.0 / tick));
                    }
                } catch (InterruptedException e) {
                    System.out.println(this + " interrupted by " + e.toString());
                }
            });
            this.r = r;
            this.tick = tick;
            this.id = counter++;
            this.name = name;
            System.out.println(this);
        }

        public void start(){
            if(t.getState()==Thread.State.NEW) t.start();
        }

        public Task resume(){
            Task renew = new Task(r, tick, name);
            renew.start();
            return renew;
        }

        public void stop(){
            this.stop=true;
        }

        public boolean isStopped(){
            return this.stop;
        }

        @Override
        public String toString(){
            return "Task#" + id + ": " + name;
        }
    }
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
        this.main = new Task(this::run, tickrate, "Engine::Main loop");
        this.repaint = new Task(window::repaint, fps, "Engine::Window repaint loop");
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
        synchronized(Engine.class){     // Stop every update while changing scene
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
     * Start the Engine loop and user loop
     */
    public void start(){
        for(Task t: pool){
            t.start();
        }
        main.start();
        repaint.start();
    }

    public void stop(){
        this.stop=true;
        main.stop();
        repaint.stop();
        for(Task t:pool)
            t.stop();
        window.close();
    }

    /**
     * Given a Runnable function execute that in a loop synchronized with
     * the Engine timer
     * @param task Task to execute synchronized with the Engine timer
     */
    public TaskHandler updater(Runnable task, String name){
        Task t = new Task(task, tickrate, "User::" + name);
        pool.add(t);
        return new TaskHandler(t);
    }

    /**
     * Game loop function
     */
    private void run(){
        for (Action act : window.km.pressed) {
            act.pressed();
        }
        for (Action act : window.km.released) {
            act.released();
        }
        window.km.released.clear();
    }
}
