package Test.SideScrolling.World;

import Gfx.Animation;
import Physics.Entity;
import Physics.World;
import org.jetbrains.annotations.NotNull;

public class Terrain extends Entity implements World {
    public Terrain(@NotNull Animation animation, int x, int y, int z){
        super(animation, x, y, z);
    }
}
