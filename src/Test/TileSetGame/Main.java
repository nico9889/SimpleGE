package Test.TileSetGame;

import Engine.Action;
import Engine.Engine;
import Engine.Scene;
import Engine.KeyMap;

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
 * This is a tileset game like Pokémon. This is used to tests hitbox and collisions.
 */
public class Main {
    public static final Random rnd = new Random();
    public static Image[] loadSprites(File folder) throws IOException {
        Image[] frames = new Image[(folder.listFiles().length)];
        int frame = 0;
        for(File file:folder.listFiles()){
            frames[frame] = new Image(file);
            frame++;
        }
        return frames;
    }

    public static ArrayList<Sprite> clouds_gen() throws IOException {
        Image[] cloud_frames = loadSprites(new File("resources/sprites/world/clouds"));
        ArrayList<Sprite> clouds = new ArrayList<>();
        for(int i=0;i<5;i++){
            Animation cloud_anim = new Animation(cloud_frames, Math.abs((rnd.nextInt()%30)+10));
            clouds.add(new AnimSprite(cloud_anim,Math.abs(rnd.nextInt())%1000,Math.abs(rnd.nextInt())%500,2));
        }
        return clouds;
    }

    public static ArrayList<Sprite> tileGrass() throws IOException {
        ArrayList<Sprite> sprites = new ArrayList<>();
        Image grass = new Image("resources/sprites/world/grass/grass.png");
        for(int i=0;i<1280;i+=32){
            for(int j=0;j<704;j+=32){
                sprites.add(new Sprite(grass, i, j, 0, "Grass"));
            }
        }
        return sprites;
    }

    public static ArrayList<Entity> tileTrees() throws IOException{
        ArrayList<Entity> sprites = new ArrayList<>();
        Image[] tree_frame = {new Image("resources/sprites/world/tree/tree.png")};
        Animation tree_anim = new Animation(tree_frame,0);
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

    public static ArrayList<Entity> lake(int x, int y,int w, int h) throws IOException{
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


    public static void main(String[] args) throws IOException, InterruptedException {
        Engine engine = new Engine(1280, 704, "Pokémon");
        Scene scene = new Scene("Main");
        ArrayList<Sprite> clouds = clouds_gen();
        Image[] player_image = {new Image("resources/sprites/player/player.png")};

        Animation player_anim = new Animation(player_image, 0);
        Entity player = new Entity(player_anim, 20*32,10*32,1);
        player.autoHitBox();
        scene.addSprite(tileGrass());
        ArrayList<Entity> trees = tileTrees();
        ArrayList<Sprite> tree_sprites = new ArrayList<>(trees);
        scene.addSprite(tree_sprites);
        scene.addSprite(clouds);
        scene.addSprite(player);
        ArrayList<Entity> lake = lake(25,5,10,5);
        scene.addSprite(lake);
        engine.addScene(scene);
        engine.nextScene();

        // workaround
        player.addCollidable(trees);
        player.addCollidable(lake);

        Action move_up = new Action((() -> player.moveBy( 0,8)) ,null);
        Action move_down = new Action((() -> player.moveBy( 0,-8)),null);
        Action move_left = new Action((() -> player.moveBy( -8,0)), null);
        Action move_right = new Action((() -> player.moveBy( 8,0)),null);
        Action swim = new Action (()-> player.removeCollidable(lake), ()->player.addCollidable(lake));
        KeyMap.addKey(KeyEvent.VK_UP, move_up);
        KeyMap.addKey(KeyEvent.VK_DOWN, move_down);
        KeyMap.addKey(KeyEvent.VK_LEFT, move_left);
        KeyMap.addKey(KeyEvent.VK_RIGHT, move_right);
        KeyMap.addKey(KeyEvent.VK_E, swim);

        engine.addKeyMap(new KeyMap());
        while(true){
            for(Sprite s:clouds){
                if(s.visible())
                    s.moveBy(Math.abs(rnd.nextInt())%5,Math.abs(rnd.nextInt())%5);
                else {
                    s.moveTo((Math.abs(rnd.nextInt()) % 1200)-300, -(Math.abs(rnd.nextInt()) % 300) );
                }
            }
            engine.update();
        }
    }
}
