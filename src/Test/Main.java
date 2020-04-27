package Test;

import Engine.Action;
import Engine.Engine;
import Engine.Scene;
import Engine.KeyMap;

import Gfx.Animation;
import Gfx.Image;
import Gfx.Sprite;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static ArrayList<Sprite> clouds_gen() throws IOException {
        ArrayList<Sprite> clouds = new ArrayList<>();
        Random rnd = new Random();
        for(int i=0;i<10;i++){
            clouds.add(new Sprite("resources/sprites/cloud.png",Math.abs(rnd.nextInt())%1000,Math.abs(rnd.nextInt())%700+50,1));
        }
        return clouds;
    }
    public static void main(String[] args) throws InterruptedException, IOException {
        Random rnd = new Random();
        Engine engine = new Engine(1280,720, "Test Game");
        Scene scene = new Scene("Test scene");
        Sprite bg = new Sprite("resources/sprites/background.png", 0,0,2);
        Sprite terrain = new Sprite("resources/sprites/background2.png", 0,0,0);

        Image idle1 = new Image("resources/sprites/player/idle/idle_1.png");
        Image idle2 = new Image("resources/sprites/player/idle/idle_2.png");
        Image idle3 = new Image("resources/sprites/player/idle/idle_3.png");
        Image idle4 = new Image("resources/sprites/player/idle/idle_4.png");
        Image idle5 = new Image("resources/sprites/player/idle/idle_5.png");
        Image idle6 = new Image("resources/sprites/player/idle/idle_6.png");
        Image idle7 = new Image("resources/sprites/player/idle/idle_7.png");
        Image idle8 = new Image("resources/sprites/player/idle/idle_8.png");
        Image idle9 = new Image("resources/sprites/player/idle/idle_9.png");

        Animation idle = new Animation(new Image[]{idle1,idle2,idle3,idle4,idle5,idle6,idle7,idle8,idle9},2);
        Player donald = new Player(idle, 100, 150, 1);

        ArrayList<Sprite> clouds = clouds_gen();
        scene.addSprite(clouds);
        scene.addSprite(donald);
        scene.addSprite(bg);
        scene.addSprite(terrain);

        engine.addScene(scene);
        engine.nextScene();

        Action move_up = new Action((() -> donald.moveBy( 0,2)), donald::idle);
        Action move_down = new Action((() -> donald.moveBy( 0,-2)), donald::idle);
        Action move_left = new Action((() -> donald.moveBy( -2,0)), donald::idle);
        Action move_right = new Action((() -> donald.moveBy( 2,0)), donald::idle);

        KeyMap.addKey(KeyEvent.VK_UP, move_up);
        KeyMap.addKey(KeyEvent.VK_DOWN, move_down);
        KeyMap.addKey(KeyEvent.VK_LEFT, move_left);
        KeyMap.addKey(KeyEvent.VK_RIGHT, move_right);

        engine.addKeyMap(new KeyMap());

        while(true){
            for(Sprite s:clouds){
                if(s.visible())
                    s.moveBy(Math.abs(rnd.nextInt())%6,0);
                else
                    s.moveTo(-300,Math.abs(rnd.nextInt())%700+50);
            }
            engine.update();
        }
    }
}
