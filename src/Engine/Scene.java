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

    public void load(){
        Collections.sort(this);
        Collections.sort(entities);
    }

    public void addSprite(Sprite s){
        super.add(s);
    }

    public void addSprite(Entity e){
        super.add(e);
        entities.add(e);
    }

    public void addSprite(ArrayList<? extends Sprite> s){
        super.addAll(s);
    }

    public void bulkAddEntities(ArrayList<Entity> el){
        super.addAll(el);
        entities.addAll(el);
    }

    public void registerHitBoxes(){
        // FIXME this is vastly inefficient but executed rarely
        for(Entity e:entities){
            e.removeCollidable();
            for (Entity entity : entities) {
                if (e != entity && e.z == entity.z)
                    e.addCollidable(entity);
            }
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
