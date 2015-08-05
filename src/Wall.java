import java.awt.Graphics2D;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.*;
import org.jbox2d.collision.shapes.*;

public class Wall extends Entity {
    private Body mBody;
    
    public Wall(World world, float x, float y, float t, float w, float h, float f) {
	super(world);
	
	BodyDef bodyDef = new BodyDef();
	bodyDef.type = BodyType.STATIC;
	bodyDef.position.set(0, 0);
	bodyDef.angle = t;

	mBody = world.createBody(bodyDef);
		    
	PolygonShape shape = new PolygonShape();
	shape.setAsBox(x, y, new Vec2(w, h), 0);

	FixtureDef fdef = new FixtureDef();
	fdef.friction = f;
	fdef.shape = shape;
	mBody.createFixture(fdef);
    }

    public void update(World world){}
    public void draw(Graphics2D g){
	g.setColor(java.awt.Color.MAGENTA);
	g.fillRect(0,0,2,5);
    }
}
