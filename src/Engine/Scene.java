package Engine;

import Gfx.Sprite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Scene {
    public String name;
    private final ArrayList<Sprite> sprites;

    public Scene(String name){
        sprites = new ArrayList<>();
        this.name = name;
    }

    public void addSprite(Sprite s){
        sprites.add(s);
        Collections.sort(sprites);
    }

    public void addSprite(ArrayList<Sprite> s){
        sprites.addAll(s);
        Collections.sort(sprites);
    }

    public ArrayList<Sprite> getSprites() {
        return sprites;
    }

    @Override
    public String toString(){
        return String.format("Scene[%s]", this.name);
    }
}
