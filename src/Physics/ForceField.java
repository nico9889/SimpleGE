package Physics;

public class ForceField {
    public int x, y;
    public int width, height;
    public double forceX, forceY;

    public ForceField(int x, int y, int width, int height, double forceX, double forceY){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.forceX = -forceX;
        this.forceY = -forceY;
    }
}
