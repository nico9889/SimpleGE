package Test;

import Engine.Engine;
import Engine.Scene;
import Engine.KeyMap;
import Engine.KeyMapping;
import Gfx.AnimSprite;
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
            clouds.add(new Sprite("C:/Users/Nicola/Desktop/cloud.png",Math.abs(rnd.nextInt())%1000,Math.abs(rnd.nextInt())%700+50,1));
        }
        return clouds;
    }
    public static void main(String[] args) throws InterruptedException, IOException {
        Random rnd = new Random();
        Engine engine = new Engine(1280,720, "Test Game");
        Scene scene = new Scene("Test scene");
        Sprite bg = new Sprite("C:/Users/Nicola/Desktop/background.png", 0,0,2);
        Sprite terrain = new Sprite("C:/Users/Nicola/Desktop/background2.png", 0,0,0);

        // TODO: separate object creation in different sources
        Image spraterino1 = new Image("C:/Users/Nicola/Desktop/spraterino1.png");
        Image spraterino2 = new Image("C:/Users/Nicola/Desktop/spraterino2.png");
        Image[] run_anim = {spraterino1, spraterino2};

        // FIXME: this is not Sonic now
        AnimSprite donald = new AnimSprite(spraterino1, 100,100, 1);

        ArrayList<Sprite> clouds = clouds_gen();
        scene.addSprite(clouds);
        scene.addSprite(donald);
        scene.addSprite(bg);
        scene.addSprite(terrain);

        engine.addScene(scene);
        engine.nextScene();

        RunAnimation run_up = new RunAnimation(donald, run_anim, 0,2);
        RunAnimation run_down = new RunAnimation(donald, run_anim,0,-2);
        RunAnimation run_left = new RunAnimation(donald, run_anim,-2,0);
        RunAnimation run_right = new RunAnimation(donald, run_anim,2,0);

        KeyMap up = new KeyMap(KeyEvent.VK_W, run_up);
        KeyMap down = new KeyMap(KeyEvent.VK_S, run_down);
        KeyMap left = new KeyMap(KeyEvent.VK_A, run_left);
        KeyMap right = new KeyMap(KeyEvent.VK_D, run_right);

        KeyMapping map = new KeyMapping();
        map.addKey(up);
        map.addKey(down);
        map.addKey(left);
        map.addKey(right);

        engine.addKeyMap(map);

        while(true){
            for(Sprite s:clouds){
                if(s.visible())
                    s.move_by(Math.abs(rnd.nextInt())%6,0);
                else
                    s.move_to(-300,Math.abs(rnd.nextInt())%700+50);
            }
            engine.update();
        }
    }
}
