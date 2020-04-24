package Engine;

import Gfx.Sprite;

import java.util.ArrayList;

public class Engine {
    private final Window window;
    private int scene = 0;
    private final ArrayList<Scene> scenes = new ArrayList<Scene>();
    private Scene now;
    private String name = "Game";
    private double fps = 60.0;
    private KeyMapping kmap;

    public Engine(int w, int h, String gameName){
        window = Window.get(w, h);
        window.setTitle(gameName);
        this.name = gameName;
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

    public void addKeyMap(KeyMapping map){
        window.addKeyMapping(map);
        this.kmap = map;
    }

    private void registerSprites(){
        for(Sprite sprite:now.getSprites()){
            System.out.println(sprite);
            sprite.setWindowDim(window.getW(), window.getH());
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
        for(KeyMap map:kmap){
            for(int key:KeyMapping.pressed)
                if(map.getKey()==key){
                    map.getFunc().run();
                }
        }
        window.repaint();
    }
}
