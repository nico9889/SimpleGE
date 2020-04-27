package Engine;

import Gfx.Sprite;

import java.util.ArrayList;

public class Engine {
    private final Window window;
    private int scene = 0;
    private final ArrayList<Scene> scenes = new ArrayList<Scene>();
    private Scene now;
    private String name;
    private double fps = 60.0;
    private KeyMap kmap;

    public Engine(int w, int h, String gameName){
        window = Window.get(w, h);
        window.setTitle(gameName);
        this.name = gameName;
        Sprite.setWindowDim(window.getW(),window.getH());
    }

    // This constructor permit to override fps value
    public Engine(int w, int h, double fps, String gameName){
        window = Window.get(w, h);
        window.setTitle(gameName);
        this.name = gameName;
        this.fps = fps;
    }

    public void addScene(Scene s){
        scenes.add(s);
    }

    public void addKeyMap(KeyMap map){
        window.addKeyMapping(map);
        this.kmap = map;
    }

    private void registerSprites(){
        for(Sprite sprite:now.getSprites()){
            window.add(sprite);
            window.revalidate();
        }
    }

    public void nextScene(){
        if(this.scene<scenes.size()) {
            now = scenes.get(this.scene);
            window.setTitle(this.name + " - " + now.name);
            this.registerSprites();
            this.scene++;
        }
    }

    public void update() throws InterruptedException {
        Thread.sleep((long)((1.0/this.fps)*1000.0));
        for(Action act: KeyMap.pressed) {
            act.pressed();
        }
        for(Action act: KeyMap.released) {
            act.released();
        }
        KeyMap.released.clear();
        window.repaint();
    }
}
