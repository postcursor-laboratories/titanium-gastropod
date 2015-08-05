import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.*;
import org.jbox2d.collision.shapes.*;

public class Level {
    private ArrayList<Entity> mEntities;
    
    public Level(String name, World world) {
	mEntities = new ArrayList<Entity>();
	
	BodyDef bodyDef = new BodyDef();
	bodyDef.type = BodyType.STATIC;
	bodyDef.position.set(0, 0);
	bodyDef.angle = 0f;
	Body wall = world.createBody(bodyDef);
		
	PolygonShape shape = new PolygonShape();
	shape.setAsBox(300, 10, new Vec2(150,5), 0);
			
	FixtureDef fdef = new FixtureDef();
	fdef.friction = 1f;
	fdef.shape = shape;
	shape.setAsBox(300, 10, new Vec2(300,10), 0);
	wall.createFixture(fdef);
	shape.setAsBox(10, 300, new Vec2(10,300), 0);
	wall.createFixture(fdef);

	mEntities.add(new Jarvis(world));
    }

    public ArrayList<Entity> getEntities() {
	return mEntities;
    }

    // This should always be called before the Level gets deallocated.
    // Consider this a deconstructor.
    public void onDestroy() {}
}
