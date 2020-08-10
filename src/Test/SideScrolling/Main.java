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
import Physics.Gravity;
import Physics.Physics;
import Test.SideScrolling.World.Terrain;

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

    public static Image[] loadSprites(File folder, double r, double g, double b){
        Image[] frames = new Image[(folder.listFiles().length)];
        int frame = 0;
        try {
            for (File file : folder.listFiles()) {
                Image image = new Image(file.getPath());
                image.recolor(r, g, b);
                frames[frame] = image;
                frame++;
            }
        }catch(IOException io){
            io.printStackTrace();
            System.exit(-1);
        }
        return frames;
    }

    public static ArrayList<Sprite> clouds_gen(){
        Image[] cloud_frames = loadSprites(new File("resources/sprites/world/clouds"), 1,1,1);
        ArrayList<Sprite> clouds = new ArrayList<>();
        for(int i=0;i<10;i++){
            Animation cloud_anim = new Animation(cloud_frames, Math.abs((rnd.nextInt()%30)+10));
            clouds.add(new AnimSprite(cloud_anim,Math.abs(rnd.nextInt())%1000,Math.abs(rnd.nextInt())%500+150,2));
        }
        return clouds;
    }

    public static void main(String[] args) throws IOException {
        Engine engine = new Engine(1280,720,60,60, false, "Test Game");
        Gravity g = new Gravity(0,0,1280,720, 0.0,3.0);
        Physics donald_p = new Physics(2);
        Physics ex_p = new Physics(3);

        Scene scene = new Scene("Test scene");
        Sprite bg = new Sprite("resources_sidescrolling/sprites/background.png", 0,0,0);
        Image t_image = new Image(new File("resources_sidescrolling/sprites/background2.png"));
        Terrain terrain = new Terrain(new Animation(new Image[]{t_image},0), 0,0,1);

        Image[] idle_frames_0 = loadSprites(new File("resources_sidescrolling/sprites/player/idle/"), 1,1,1);
        Image[] idle_frames_1 = loadSprites(new File("resources_sidescrolling/sprites/player/idle/"), 2,0.5,0);
        Animation idle_0 = new Animation(idle_frames_0,4);
        Animation idle_1 = new Animation(idle_frames_1, 4);

        Player donald = new Player(idle_0, 500, 560, 1, donald_p);
        Player ex = new Player(idle_1, 100, 560, 1, ex_p);
        ex.autoHitBox();
        donald.autoHitBox();
        terrain.autoHitBox();
        ArrayList<Sprite> clouds = clouds_gen();
        scene.addSprite(clouds);
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(donald);
        entities.add(terrain);
        entities.add(ex);
        scene.bulkAddEntities(entities);
        scene.addSprite(bg);
        scene.addGravity(g);
        engine.addScene(scene);
        engine.nextScene();

        KeyMap map = new KeyMap();
        Action move_up = new Action(() -> donald.moveBy( 0,20), donald::idle);
        Action move_down = new Action(() -> donald.moveBy( 0,-8), donald::idle);
        Action move_left = new Action(() -> donald.moveBy( -8,0), donald::idle);
        Action move_right = new Action(() -> donald.moveBy( 8,0), donald::idle);
        map.addKey(KeyEvent.VK_UP, move_up);
        map.addKey(KeyEvent.VK_DOWN, move_down);
        map.addKey(KeyEvent.VK_LEFT, move_left);
        map.addKey(KeyEvent.VK_RIGHT, move_right);
        Action second_move_up = new Action(() -> ex.moveBy( 0,20), ex::idle);
        Action second_move_down = new Action(() -> ex.moveBy( 0,-8), ex::idle);
        Action second_move_left = new Action(() -> ex.moveBy( -8,0), ex::idle);
        Action second_move_right = new Action(() -> ex.moveBy( 8,0), ex::idle);
        map.addKey(KeyEvent.VK_W, second_move_up);
        map.addKey(KeyEvent.VK_S, second_move_down);
        map.addKey(KeyEvent.VK_A, second_move_left);
        map.addKey(KeyEvent.VK_D, second_move_right);



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
