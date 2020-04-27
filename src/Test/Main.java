package Test;

import Engine.Action;
import Engine.Engine;
import Engine.Scene;
import Engine.KeyMap;

import Gfx.AnimSprite;
import Gfx.Animation;
import Gfx.Image;
import Gfx.Sprite;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {



    public static Image[] loadSprites(File folder) throws IOException {
        Image[] frames = new Image[(folder.listFiles().length)];
        int frame = 0;
        for(File file:folder.listFiles()){
            System.out.println(file.getName());
            frames[frame] = new Image(file);
            frame++;
        }
        return frames;
    }

    public static ArrayList<Sprite> clouds_gen() throws IOException {
        Image[] cloud_frames = loadSprites(new File("resources/sprites/world/clouds"));
        Animation cloud_anim = new Animation(cloud_frames, 60);
        ArrayList<Sprite> clouds = new ArrayList<>();
        Random rnd = new Random();
        for(int i=0;i<10;i++){
            clouds.add(new AnimSprite(cloud_anim,Math.abs(rnd.nextInt())%1000,Math.abs(rnd.nextInt())%500+150,Math.abs(rnd.nextInt()%5)));
        }
        return clouds;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Random rnd = new Random();
        Engine engine = new Engine(1280,720, "Test Game");
        Scene scene = new Scene("Test scene");
        Sprite bg = new Sprite("resources/sprites/background.png", 0,0,2);
        Sprite terrain = new Sprite("resources/sprites/background2.png", 0,0,0);

        Image[] idle_frames = loadSprites(new File("resources/sprites/player/idle/"));
        Animation idle = new Animation(idle_frames,2);
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

        int[] speeds = new int[clouds.size()];
        for(int i = 0;i<speeds.length;i++)
            speeds[i] = Math.abs(rnd.nextInt()%5)+5;
        int i = 0;
        while(true){
            i = 0;
            for(Sprite s:clouds){
                if(s.visible())
                    s.moveBy(Math.abs(rnd.nextInt())%(speeds[i]),0);
                else {
                    s.moveTo(-300, Math.abs(rnd.nextInt()) % 700 + 50);
                    speeds[i] = Math.abs(rnd.nextInt()%10)+5;
                }
                i++;
            }
            engine.update();
        }
    }
}
