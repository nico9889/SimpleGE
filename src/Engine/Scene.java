package Engine;

import Gfx.Sprite;
import Physics.Entity;
import Physics.ForceField;
import Physics.Physics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Scene extends ArrayList<Sprite>{
    public final String name;
    protected final ArrayList<Entity> entities = new ArrayList<>();
    protected final ArrayList<ForceField> fields = new ArrayList<>();
    protected final ArrayList<Physics> physicsEntity = new ArrayList<>();

    public Scene(String name){
        this.name = name;
    }

    public void addGravity(ForceField g){
        fields.add(g);
    }

    protected void updatePhysics(){
        for(Physics p: physicsEntity){
            Entity pe = p.getEntity();
            for(ForceField g: fields){
                if(pe.x >= g.x && pe.x <= g.x+g.width && pe.y >= g.y && pe.y <= g.y+g.height){
                    pe.moveBy((int)(g.forceX*p.weight), (int)(g.forceY*p.weight));
                }
            }
        }
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
        for(Entity e:entities){
            if(e.p != null)
                physicsEntity.add(e.p);
        }
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
        entities.clear();
    }

    public ArrayList<Sprite> getSprites() {
        return this;
    }



    @Override
    public String toString(){
        return String.format("Scene[%s]", this.name);
    }
}
