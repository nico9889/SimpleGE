package Test.TileSetGame;

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


public class Main {
    private static final Random rnd = new Random();
    private static Image[] tree_frame;
    private static int level = 0;
    private static Engine engine;
    private static Entity portal;

    private static ArrayList<Sprite> clouds;
    private static Entity player;

    /*
    * Utility functions starts here
    */
    public static Image[] loadSprites(File folder){
        Image[] frames = new Image[(folder.listFiles().length)];
        int frame = 0;
        for (File file : folder.listFiles()) {
            try {
                frames[frame] = new Image(file);
            }catch(IOException io){
                io.printStackTrace();
                System.exit(-1);
            }
            frame++;
        }
        return frames;
    }

    public static ArrayList<Sprite> clouds_gen(){
        Image[] cloud_frames = loadSprites(new File("resources/sprites/world/clouds"));
        ArrayList<Sprite> clouds = new ArrayList<>();
        for(int i=0;i<5;i++){
            Animation cloud_anim = new Animation(cloud_frames, Math.abs((rnd.nextInt()%30)+10));
            clouds.add(new AnimSprite(cloud_anim,Math.abs(rnd.nextInt())%1000,Math.abs(rnd.nextInt())%500,2));
        }
        return clouds;
    }

    public static ArrayList<Sprite> tileGrass(){
        ArrayList<Sprite> sprites = new ArrayList<>();
        try {
            Image grass = new Image("resources/sprites/world/grass/grass.png");
            for(int i=0;i<1280;i+=32){
                for(int j=0;j<704;j+=32){
                    sprites.add(new Sprite(grass, i, j, 0, "Grass"));
                }
            }
        }catch(IOException io){
            io.printStackTrace();
            System.exit(-1);
        }
        return sprites;
    }

    public static ArrayList<Entity> tileTrees(Animation tree_anim){
        ArrayList<Entity> sprites = new ArrayList<>();
        for(int i=0;i<1280;i+=32){
            for(int j=0;j<704;j+=32){
                if(i<96 || i>= 1216 || j<96 || j>= 640) {
                    Entity tree = new Entity(tree_anim, i, j, 1);
                    tree.autoHitBox();
                    sprites.add(tree);
                }
            }
        }
        return sprites;
    }

    public static ArrayList<Entity> lake(int x, int y,int w, int h){
        ArrayList<Entity> sprites = new ArrayList<>();
        Image[] water_frames = loadSprites(new File("resources/sprites/world/water"));
        Animation water_anim = new Animation(water_frames, 60);
        for(int i=x*32;i<(x*32+w*32);i+=32) {
            for (int j = y*32; j < (y*32+h * 32); j += 32) {
                {
                    Entity water = new Entity(water_anim, i, j, 1);
                    water.autoHitBox();
                    sprites.add(water);
                }
            }
        }
        return sprites;
    }

    public static ArrayList<Entity> treeMaze(int x, int y){
        Animation tree_anim = new Animation(tree_frame,0);
        return treeMaze(tree_anim, x,y);
    }

    public static ArrayList<Entity> treeMaze(Animation tree_anim, int x, int y){
        ArrayList<Entity> sprites = new ArrayList<>();
        Maze m = new Maze(x,y);
        int[][] maze = m.getMaze();
        for(int i=0;i<m.gridDimensionY;i++){
            for(int j=0;j<m.gridDimensionX;j++){
                if(maze[j][i]==1) {
                    Entity tree = new Entity(tree_anim, (j+3) * 32, (i+3) * 32, 1);
                    tree.autoHitBox();
                    sprites.add(tree);
                }
            }
        }
        return sprites;
    }

    public static void newLevel(){
        ArrayList<Entity> tree_maze = treeMaze(16,8);
        Scene s = new Scene("Level: " + level++);
        s.addSprite(tileGrass());
        s.bulkAddEntities(tree_maze);
        s.bulkAddEntities(ocean());
        s.addSprite(clouds);
        s.addSprite(player);
        s.addSprite(portal);
        engine.addScene(s);
    }

    public static ArrayList<Entity> ocean(){
        ArrayList<Entity> sprites = new ArrayList<>();
        Image[] water_frames = loadSprites(new File("resources/sprites/world/water"));
        Animation water_anim = new Animation(water_frames, 30);
        for(int i=0;i<1280;i+=32){
            for(int j=0;j<704;j+=32){
                if(i<64 || i>= 1184 || j<64 || j>= 672) {
                    Entity water = new Entity(water_anim, i, j, 1);
                    water.autoHitBox();
                    sprites.add(water);
                }
            }
        }
        return sprites;
    }

    /*
     * Utility functions ends here
    */
    public static void main(String[] args) throws IOException{
        engine = new Engine(1280, 704, false, "Maze");          // Creating a new Engine
        Scene scene = new Scene("Main");                                           // Creating a new Scene
                                                                                         // Adding elements to the scene
        clouds = clouds_gen();
        Image[] player_image = {new Image("resources/sprites/player/player.png")};

        Animation player_anim = new Animation(player_image, 0);
        player = new Entity(player_anim, 20*32,10*32,1);
        player.autoHitBox();

        Image[] frames = loadSprites(new File("resources/sprites/world/portal"));
        Animation portal_frames = new Animation(frames, 4);
        portal = new Entity(portal_frames, 10*32, 10*32, 1);
        portal.autoHitBox();

        tree_frame = new Image[]{new Image("resources/sprites/world/tree/tree.png")};
        Animation tree_anim = new Animation(tree_frame,0);

        Entity tree = new Entity(tree_anim, 32*10, 32*10, 1);
        tree.autoHitBox();
        scene.addSprite(portal);
        scene.addSprite(tileGrass());
        scene.bulkAddEntities(tileTrees(tree_anim));
        scene.addSprite(clouds);
        scene.addSprite(player);
        ArrayList<Entity> lake = lake(25,5,10,5);
        scene.bulkAddEntities(lake);
        engine.addScene(scene);
        engine.nextScene();                                                              // Loading scene

        final int speed = 8;                                                             // Binding keys to functions
        KeyMap map = new KeyMap();
        engine.addKeyMap(map);
        Action move_up = new Action((() -> player.moveBy( 0,speed)));
        Action move_down = new Action((() -> player.moveBy( 0,-speed)));
        Action move_left = new Action((() -> player.moveBy( -speed,0)));
        Action move_right = new Action((() -> player.moveBy( speed,0)));
        Action swim = new Action(()-> player.removeCollidable(lake), ()->player.addCollidable(lake));
        Action stop = new Action(engine::stop);

        map.addKey(KeyEvent.VK_UP, move_up);
        map.addKey(KeyEvent.VK_DOWN, move_down);
        map.addKey(KeyEvent.VK_LEFT, move_left);
        map.addKey(KeyEvent.VK_RIGHT, move_right);
        map.addKey(KeyEvent.VK_E, swim);
        map.addKey(KeyEvent.VK_Q, stop);



        TaskHandler clouds_update = engine.updater(()->{
                for (Sprite s : clouds) {
                    if (s.visible())
                        s.moveBy(Math.abs(rnd.nextInt()) % 5, Math.abs(rnd.nextInt()) % 5);
                    else {
                        s.moveTo((Math.abs(rnd.nextInt()) % 1200) - 300, -(Math.abs(rnd.nextInt()) % 300));
                    }
                }
        }, "Cloud Update");
        Action clouds_stop = new Action(clouds_update::stop);
        Action cloud_start = new Action(clouds_update::start);
        map.addKey(KeyEvent.VK_C, clouds_stop);
        map.addKey(KeyEvent.VK_P, cloud_start);


        newLevel();

        TaskHandler game = engine.updater( () -> {
            ArrayList<Sprite> c = player.getCollisions();
            if (c.contains(portal)) {
                portal.moveBy(-(portal.x - 1088), -(portal.y - 128));
                player.moveBy(-(player.x - 128), -(player.y - 576));    // FIXME hitbox glitch
                engine.nextScene();
                newLevel();
            }
        }, "Game");
        engine.start();
    }
}
