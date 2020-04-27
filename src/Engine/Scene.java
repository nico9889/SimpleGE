package Engine;

import Gfx.AnimSprite;
import Gfx.Sprite;

import java.util.ArrayList;
import java.util.Collections;

public class Scene extends ArrayList<Sprite>{
    public final String name;

    public Scene(String name){
        this.name = name;
    }

    public void addSprite(Sprite s){
        this.add(s);
        Collections.sort(this);
    }

    public void addSprite(ArrayList<Sprite> s){
        this.addAll(s);
        Collections.sort(this);
    }

    public ArrayList<Sprite> getSprites() {
        return this;
    }

    @Override
    public String toString(){
        return String.format("Scene[%s]", this.name);
    }
}
