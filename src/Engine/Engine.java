package Engine;

import Gfx.Sprite;

import javax.swing.*;
import java.util.ArrayList;

public class Engine {
    private Window window;
    private int scene = 0;
    private ArrayList<Scene> scenes = new ArrayList<Scene>();
    private Scene now;
    private String name = "Game";
    private double fps = 60.0;

    public Engine(int w, int h){
        window = Window.get(w, h);
        window.setTitle("Game Window");
    }

    public Engine(int w, int h, double fps){
        window = Window.get(w, h);
        window.setTitle("Game Window");
        this.fps = 60.0;
    }

    public void addScene(Scene s){
        scenes.add(s);
    }

    private void register_sprites() throws InterruptedException {
        for(Sprite sprite:now.getSprites()){
            System.out.println(sprite);
            sprite.setWindowDim(window.getW(), window.getH());
            window.add(sprite);
            window.revalidate();
        }
        update();
    }
    public void nextScene() throws InterruptedException {
        if(this.scene<scenes.size()) {
            now = scenes.get(this.scene);
            window.setTitle(this.name + " - " + now.name);
            this.register_sprites();
        }
    }

    public void update() throws InterruptedException {
        Thread.sleep((long)((1.0/this.fps)*1000.0));
        window.repaint();
    }
}
