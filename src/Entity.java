import java.awt.Graphics2D;
import org.jbox2d.dynamics.World;

public abstract class Entity {
    public Entity(World world){}
    public abstract void update(World world);
    public abstract void draw(Graphics2D g);
}
