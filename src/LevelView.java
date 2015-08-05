import java.awt.Graphics2D;
import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.*;
import org.jbox2d.collision.shapes.*;

public class LevelView extends View {
    private static final Vec2 GRAVITY = new Vec2(0, -50f);
    private World mWorld;
    private Body wall;
    private ArrayList<Entity> mEntities;
    
    public LevelView() {
    	mWorld = new World(GRAVITY);
	
	{ //Create wall
	    BodyDef bodyDef = new BodyDef();
	    bodyDef.type = BodyType.STATIC;
	    bodyDef.position.set(0, 0);
	    bodyDef.angle = 0f;
	    wall = mWorld.createBody(bodyDef);
		
	    PolygonShape shape = new PolygonShape();
	    shape.setAsBox(300, 10, new Vec2(150,5), 0);
			
	    FixtureDef fdef = new FixtureDef();
	    fdef.friction = 1f;
	    fdef.shape = shape;
	    shape.setAsBox(300, 10, new Vec2(300,10), 0);
	    wall.createFixture(fdef);
	    shape.setAsBox(10, 300, new Vec2(10,300), 0);
	    wall.createFixture(fdef);
	}

	mEntities = new ArrayList<Entity>();
	mEntities.add(new Jarvis(mWorld));
    }
    
    public void update() {
    	mWorld.step(0.030f, 6, 2);

	for (Entity e : mEntities)
	    e.update(mWorld);
    }

    public void draw(Graphics2D g) {
	for (Entity e : mEntities)
	    e.draw(g);
    }
}
