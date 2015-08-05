import java.awt.Graphics2D;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.*;
import org.jbox2d.collision.shapes.*;

public class Jarvis extends Entity {

	private Body[][] jarvis;
	static final int jCount = 13; // Bloblings
	
	static final boolean WEDGE = true;

	public Jarvis(World world) {
		super(world);

		{ // Create wall
			BodyDef bodyDef = new BodyDef();//
			bodyDef.type = BodyType.STATIC;
			bodyDef.position.set(0, 0);
			bodyDef.angle = 0f;
			Body wall = world.createBody(bodyDef);

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(300, 10, new Vec2(150, 5), 0);

			FixtureDef fdef = new FixtureDef();
			fdef.friction = 0.6f;
			fdef.shape = shape;
			shape.setAsBox(300, 10, new Vec2(300, 10), 0);
			wall.createFixture(fdef);
			shape.setAsBox(10, 300, new Vec2(10, 300), 0);
			wall.createFixture(fdef);
		}

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.angle = 0f;

		CircleShape shape = new CircleShape();
		shape.setRadius(3);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.friction = 1f;

		jarvis = new Body[jCount][jCount];

		if(WEDGE){
			for (int i = 0; i < jCount; i++) {
			 for(int j=0;j<=i;j++){ // Uncomment for great wedges
					bodyDef.position.set(300+8*i*(float)Math.cos(0.1*j*Math.PI*2/(i+0.01)+1), 300+8*i*(float)Math.sin(0.1*j*Math.PI*2/(i+0.01)+1));
					jarvis[i][j] = world.createBody(bodyDef);
					jarvis[i][j].createFixture(fdef);
				}
			}

			for (int i = 0; i < jCount - 2; i++) {
				for (int j = 0; j <= i; j++) {
					ConstantVolumeJointDef cvjd = new ConstantVolumeJointDef();

					cvjd.frequencyHz = 12.0f;
					cvjd.dampingRatio = 10.0f;

					cvjd.addBody(jarvis[i][j]);
					cvjd.addBody(jarvis[i + 1][j + 1]);
					cvjd.addBody(jarvis[i + 2][j + 2]);
					cvjd.addBody(jarvis[i + 2][j + 1]);
					cvjd.addBody(jarvis[i + 2][j]);
					cvjd.addBody(jarvis[i + 1][j]);

					world.createJoint(cvjd);
				}
			}
			
		} else {
			for (int i = 0; i < jCount; i++) {
				for (int j = 0; j < jCount; j++) {
					bodyDef.position.set(300 + i * 6, 300 + j * 6);
					jarvis[i][j] = world.createBody(bodyDef);
					jarvis[i][j].createFixture(fdef);
				}
			}
			
			for (int i = 0; i < jCount - 2; i++) {
				for (int j = 0; j < jCount - 2; j++) {
					ConstantVolumeJointDef cvjd = new ConstantVolumeJointDef();

					cvjd.frequencyHz = 12.0f;
					cvjd.dampingRatio = 10.0f;

					cvjd.addBody(jarvis[i][j]);
				    cvjd.addBody(jarvis[i+1][j]);
				    cvjd.addBody(jarvis[i+2][j]);
				    cvjd.addBody(jarvis[i+2][j+1]);
				    cvjd.addBody(jarvis[i+2][j+2]);
				    cvjd.addBody(jarvis[i+1][j+2]);
				    cvjd.addBody(jarvis[i][j+2]);
				    cvjd.addBody(jarvis[i][j+1]);

					world.createJoint(cvjd);
				}
			}
		}

	}

	public void update(World world) {
		Keys k = Keys.inst;
		if (k.isPressed(KeyType.LEFT)) {
			for (Body[] aB : jarvis)
				for (Body b : aB)
					if (b != null)
						b.applyForceToCenter(new Vec2(-1000, 0));
			// jarvis[0][0].applyForceToCenter(new Vec2(-1000,0));
		} else if (k.isPressed(KeyType.RIGHT)) {
			for (Body[] aB : jarvis)
				for (Body b : aB)
					if (b != null)
						b.applyForceToCenter(new Vec2(1000, 0));
			// jarvis[0][0].applyForceToCenter(new Vec2(1000,0));
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(java.awt.Color.WHITE);
		g.drawLine(20, 20, 20, 600);
		g.drawLine(20, 20, 600, 20);

		for (Body[] aB : jarvis)
			for (Body b : aB)
				if (b != null)
					g.fillOval((int) b.getPosition().x - 3, (int) b.getPosition().y - 3, 6, 6);
	}
}
