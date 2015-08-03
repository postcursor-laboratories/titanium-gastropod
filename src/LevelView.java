import java.awt.Graphics2D;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.collision.shapes.CircleShape;

public class LevelView extends View {
    private static final Vec2 GRAVITY = new Vec2(0, -10f);
    private World mWorld;
    private Body mBody;

    public LevelView() {
	mWorld = new World(GRAVITY);
	
	BodyDef bodyDef = new BodyDef();
	bodyDef.type = BodyType.DYNAMIC;
	bodyDef.position.set(0, 20);
	bodyDef.angle = 30f;
	mBody = mWorld.createBody(bodyDef);

	//PolygonShape shape = new PolygonShape();
	CircleShape shape = new CircleShape();

	FixtureDef fdef = new FixtureDef();
	fdef.shape = shape;
	mBody.createFixture(fdef);
    }
    
    public void update() {
	mWorld.step(0.030f, 6, 2);
    }

    public void draw(Graphics2D g) {
	Vec2 pos = mBody.getPosition();
	System.out.println("pos: "+pos);
	g.setColor(java.awt.Color.WHITE);
	g.fillOval((int)pos.x, (int)pos.y, 50, 50);
    }
}
