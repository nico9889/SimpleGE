package Engine;

import Gfx.Sprite;
import Physics.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Scene extends ArrayList<Sprite>{
    public final String name;
    protected final ArrayList<Entity> entities = new ArrayList<>();

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

    public void addSprite(Entity e){
        super.add(e);
        entities.add(e);
        Collections.sort(entities);
    }

    public void addSprite(ArrayList<? extends Sprite> s){
        super.addAll(s);
        Collections.sort(this);
    }

    // FIXME need several optimizations and MORE tests
    public void registerHitBoxes(){
        for(Entity e:entities){
            e.removeCollidable();
        }
        int start=0;
        int last_z = entities.get(0).z;
        int end = 0;
        while(end<entities.size()){
            if(last_z != entities.get(end).z || end==entities.size()-1){
                List<Entity> sublist = entities.subList(start, end);
                for(int j = 0;j<sublist.size(); j++){
                    for(int k = 0;k<sublist.size();k++) {
                        if(k!=j) sublist.get(j).addCollidable(sublist.get(k));
                    }
                }
                start=end;
            }
            last_z=entities.get(end).z;
            end++;
        }
    }
    public ArrayList<Sprite> getSprites() {
        return this;
    }

    @Override
    public String toString(){
        return String.format("Scene[%s]", this.name);
    }
}
