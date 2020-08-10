package Test.SideScrolling;

import Engine.Action;
import Engine.Engine;
import Engine.Scene;
import Engine.KeyMap;
import Engine.TaskHandler;

import Gfx.AnimSprite;
import Gfx.Animation;
import Gfx.Image;
import Gfx.Sprite;
import Physics.Entity;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/*
 * Side-scrolling example without collisions check to tests more or less every graphics components.
 * Clouds are randomly generated and positioned and the movement of the clouds is also randomly decided.
 */

public class Main {
    static final Random rnd = new Random();

    public static Image[] loadSprites(File folder) throws IOException {
        Image[] frames = new Image[(folder.listFiles().length)];
        int frame = 0;
        for(File file:folder.listFiles()){
            frames[frame] = new Image(file.getPath());
            frame++;
        }
        return frames;
    }

    public static ArrayList<Sprite> clouds_gen() throws IOException {
        Image[] cloud_frames = loadSprites(new File("resources/sprites/world/clouds"));
        ArrayList<Sprite> clouds = new ArrayList<>();
        for(int i=0;i<10;i++){
            Animation cloud_anim = new Animation(cloud_frames, Math.abs((rnd.nextInt()%30)+10));
            clouds.add(new AnimSprite(cloud_anim,Math.abs(rnd.nextInt())%1000,Math.abs(rnd.nextInt())%500+150,2));
        }
        return clouds;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Engine engine = new Engine(1280,720, false, "Test Game");
        Scene scene = new Scene("Test scene");
        Sprite bg = new Sprite("resources_sidescrolling/sprites/background.png", 0,0,0);
        Image t_image = new Image(new File("resources_sidescrolling/sprites/background2.png"));
        Entity terrain = new Entity(new Animation(new Image[]{t_image},0), 0,0,1);

        Image[] idle_frames = loadSprites(new File("resources_sidescrolling/sprites/player/idle/"));
        Animation idle = new Animation(idle_frames,2);
        Player donald = new Player(idle, 100, 160, 1);
        donald.autoHitBox();
        terrain.autoHitBox();
        ArrayList<Sprite> clouds = clouds_gen();
        scene.addSprite(clouds);
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(donald);
        entities.add(terrain);
        scene.bulkAddEntities(entities);
        scene.addSprite(bg);

        engine.addScene(scene);
        engine.nextScene();

        KeyMap map = new KeyMap();
        Action move_up = new Action((() -> donald.moveBy( 0,8)), donald::idle);
        Action move_down = new Action((() -> donald.moveBy( 0,-8)), donald::idle);
        Action move_left = new Action((() -> donald.moveBy( -8,0)), donald::idle);
        Action move_right = new Action((() -> donald.moveBy( 8,0)), donald::idle);
        map.addKey(KeyEvent.VK_UP, move_up);
        map.addKey(KeyEvent.VK_DOWN, move_down);
        map.addKey(KeyEvent.VK_LEFT, move_left);
        map.addKey(KeyEvent.VK_RIGHT, move_right);

        engine.addKeyMap(map);

        TaskHandler clouds_update = engine.updater(()->{
            for (Sprite s : clouds) {
                if (s.visible())
                    s.moveBy(Math.abs(rnd.nextInt()) % 5, rnd.nextInt() % 5);
                else {
                    s.moveTo(-(Math.abs(rnd.nextInt()) % 1200) , (Math.abs(rnd.nextInt()) % 300 + 300));
                }
            }
        }, "Cloud Update");
        engine.start();
    }
}
