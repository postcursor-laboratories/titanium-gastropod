import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.Arrays;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.collision.shapes.*;

public class Wall extends Entity {
	
	private Body mBody;
	private Path2D.Float mPolygon;
	
	public Wall(World world, float x, float y, float t, float w, float h, float elasticity, float friction) {
		super(world);

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position.set(0, 0);

		mBody = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(w, h, new Vec2(x, y), t); //Just calling "getVertices()" returns a bunch of 0s.
		buildPolygon(shape.m_vertices[0], shape.m_vertices[1], shape.m_vertices[2], shape.m_vertices[3]);

		FixtureDef fdef = new FixtureDef();
		fdef.friction = friction;
		fdef.restitution = elasticity;
		fdef.shape = shape;
		mBody.createFixture(fdef);
	}
	
	public Wall(World world, Vec2[] verts, float elasticity, float friction){
		super(world);

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position.set(0, 0);

		mBody = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.set(verts, verts.length);
		buildPolygon(verts);

		FixtureDef fdef = new FixtureDef();
		fdef.friction = friction;
		fdef.restitution = elasticity;
		fdef.shape = shape;
		mBody.createFixture(fdef);
	}
	
	private void buildPolygon(Vec2... verts){
		System.out.println(Arrays.toString(verts));
	    mPolygon=new Path2D.Float();
	    
	    mPolygon.moveTo(Utils.toPixelCoords(verts[0].x),Utils.toPixelCoords(verts[0].y));
	    for(int i=1;i<verts.length;i++){
	    	mPolygon.lineTo(Utils.toPixelCoords(verts[i].x),Utils.toPixelCoords(verts[i].y));
	    }
	    mPolygon.lineTo(Utils.toPixelCoords(verts[0].x),Utils.toPixelCoords(verts[0].y));	
	}

	public void update(World world) {
	}

	public void draw(Graphics2D g) {
		g.setColor(java.awt.Color.MAGENTA);
		g.fill(mPolygon);
	}
}
