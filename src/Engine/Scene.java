package Engine;

import Gfx.Sprite;
import Physics.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Scene extends ArrayList<Sprite>{
    public final String name;

    public Scene(String name){
        this.name = name;
    }

    @Override
    public boolean add(Sprite e){
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Sprite> c) {
        return false;
    }

    public void addSprite(Sprite s){
        super.add(s);
        Collections.sort(this);
    }

    public void addSprite(ArrayList<? extends Sprite> s){
        super.addAll(s);
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
