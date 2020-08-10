package Physics;

public class Physics{
    private Entity e;
    public double weight;

    public Physics(double weight){
        this.weight = weight;
    }

    public void setEntity(Entity e){
        this.e = e;
    }

    public Entity getEntity(){
        if(e!=null){
            return e;
        }else{
            throw new NullPointerException("Entity hasn't been setted");
        }
    }
}
