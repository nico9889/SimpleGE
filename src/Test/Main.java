package Test;

import Engine.Engine;
import Engine.Scene;
import Gfx.Sprite;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static ArrayList<Sprite> nuvole(){
        ArrayList<Sprite> clouds = new ArrayList<>();
        Random rnd = new Random();
        for(int i=0;i<10;i++){
            clouds.add(new Sprite("C:/Users/Nicola/Desktop/cloud.png",Math.abs(rnd.nextInt())%1000+100,Math.abs(rnd.nextInt())%700+50,1));
        }
        return clouds;
    }
    public static void main(String[] args) throws InterruptedException {
        Random rnd = new Random();
        Engine engine = new Engine(1280,720);
        Scene scene = new Scene("Test scene");
        Sprite bg = new Sprite("C:/Users/Nicola/Desktop/background.png", 0,0,2);
        Sprite terrain = new Sprite("C:/Users/Nicola/Desktop/background2.png", 0,0,0);
        Sprite sonic = new Sprite("C:/Users/Nicola/Desktop/sonic_sprite.png", 0,150, 1);
        ArrayList<Sprite> clouds = nuvole();
        scene.addSprite(clouds);
        scene.addSprite(sonic);
        scene.addSprite(bg);
        scene.addSprite(terrain);

        engine.addScene(scene);
        engine.nextScene();

        while(true){
            if(sonic.visible())
                sonic.move_by(1, 0);
            else
                sonic.move_to(-82,150);
            for(Sprite s:clouds){
                if(s.visible())
                    s.move_by(Math.abs(rnd.nextInt())%3,0);
                else
                    s.move_to(-300,Math.abs(rnd.nextInt())%700+50);
            }
            engine.update();
        }
    }
}
