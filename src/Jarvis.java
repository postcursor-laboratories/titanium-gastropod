import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.*;
import org.jbox2d.collision.shapes.*;

public class Jarvis extends Entity {

	static final int jCount = 13; // Bloblings
	static final boolean WEDGE = true;

	private Body[][] mJarvis;
	private Body[] mSkin;
	
	public Jarvis(World world) {
		super(world);

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.angle = 0f;

		CircleShape shape = new CircleShape();
		shape.setRadius(0.1f);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.friction = 1f;

		mJarvis = new Body[jCount][jCount];
//		mSkin = new Body[4*jCount];

		if(WEDGE){//Wedge shaped jarvis
			
			for (int i = 0; i < jCount; i++) { //Create bloblets
				for(int j=0;j<=i;j++){
					bodyDef.position.set(11+0.25f*i*(float)Math.cos(0.1*j*Math.PI*2/(i+0.01)+1.5), 10+0.25f*i*(float)Math.sin(0.1*j*Math.PI*2/(i+0.01)+1.5));
					mJarvis[i][j] = world.createBody(bodyDef);
					mJarvis[i][j].createFixture(fdef);
				}
			}

			for (int i = 0; i < jCount - 2; i++) { //Local constant volume
				for (int j = 0; j <= i; j++) {
					ConstantVolumeJointDef cvjd = new ConstantVolumeJointDef();

					cvjd.frequencyHz = 5f;
					cvjd.dampingRatio = 0.5f;

					cvjd.addBody(mJarvis[i][j]);
					cvjd.addBody(mJarvis[i + 1][j + 1]);
					cvjd.addBody(mJarvis[i + 2][j + 2]);
					cvjd.addBody(mJarvis[i + 2][j + 1]);
					cvjd.addBody(mJarvis[i + 2][j]);
					cvjd.addBody(mJarvis[i + 1][j]);

					if(Math.random() > 0)
						world.createJoint(cvjd);
				}
			}
			
			{ //Total body constant volume. Prevents local minima -- pieces getting "folded inside"
				ConstantVolumeJointDef cvjd = new ConstantVolumeJointDef();
	
				cvjd.frequencyHz = 1000.0f;
				cvjd.dampingRatio = 10.0f;
	
				for (int i = 0; i < jCount - 2; i++)
					cvjd.addBody(mJarvis[i][0]);
				for (int j = 1; j <= jCount - 1; j++)
					cvjd.addBody(mJarvis[jCount - 1][j]);
				for (int i = jCount - 2; i > 0; i--)
					cvjd.addBody(mJarvis[i][i]);
	
				world.createJoint(cvjd);
			}
			
		} else { //Square jarvis
			
			for (int i = 0; i < jCount; i++) {
				for (int j = 0; j < jCount; j++) {
					bodyDef.position.set(10 + i*0.2f, 10 + j*0.2f);
					mJarvis[i][j] = world.createBody(bodyDef);
					mJarvis[i][j].createFixture(fdef);
				}
			}
			
			for (int i = 0; i < jCount - 2; i++) {
				for (int j = 0; j < jCount - 2; j++) {
					ConstantVolumeJointDef cvjd = new ConstantVolumeJointDef();

					cvjd.frequencyHz = 12.0f;
					cvjd.dampingRatio = 10.0f;

					cvjd.addBody(mJarvis[i][j]);
				    cvjd.addBody(mJarvis[i+1][j]);
				    cvjd.addBody(mJarvis[i+2][j]);
				    cvjd.addBody(mJarvis[i+2][j+1]);
				    cvjd.addBody(mJarvis[i+2][j+2]);
				    cvjd.addBody(mJarvis[i+1][j+2]);
				    cvjd.addBody(mJarvis[i][j+2]);
				    cvjd.addBody(mJarvis[i][j+1]);

					world.createJoint(cvjd);
				}
			}
		}

	}

	public void update(World world) {
		Keys k = Keys.inst;
		if (k.isPressed(KeyType.LEFT)) {
			for (Body[] aB : mJarvis)
				for (Body b : aB)
					if (b != null)
						b.applyForceToCenter(new Vec2(-10, 0));
			// mJarvis[0][0].applyForceToCenter(new Vec2(-1000,0));
		} else if (k.isPressed(KeyType.RIGHT)) {
			for (Body[] aB : mJarvis)
				for (Body b : aB)
					if (b != null)
						b.applyForceToCenter(new Vec2(10, 0));
			// mJarvis[0][0].applyForceToCenter(new Vec2(1000,0));
		}
	}

	public void draw(Graphics2D g) {
//		g.setColor(java.awt.Color.WHITE);
//		for (Body[] aB : mJarvis)
//			for (Body b : aB)
//				if (b != null)
//					g.fillOval(
//							Utils.toPixelCoords(b.getPosition().x) - 3,
//							Utils.toPixelCoords(b.getPosition().y) - 3,
//							6, 6);
		
		ArrayList<Vec2> edgePoints = new ArrayList<>();
		for (int i = 0; i < jCount - 2; i++)
			edgePoints.add(mJarvis[i][0].getPosition());
		for (int j = 1; j <= jCount - 1; j++)
			edgePoints.add(mJarvis[jCount - 1][j].getPosition());
		for (int i = jCount - 2; i > 0; i--)
			edgePoints.add(mJarvis[i][i].getPosition());
		Path2D.Float outline = new Path2D.Float();

	    outline.moveTo(Utils.toPixelCoords(edgePoints.get(edgePoints.size()-1).x),Utils.toPixelCoords(edgePoints.get(edgePoints.size()-1).y));
	    for(Vec2 p : edgePoints){
	    	outline.lineTo(Utils.toPixelCoords(p.x),Utils.toPixelCoords(p.y));
	    }
	    
	    g.setColor(java.awt.Color.GRAY);
	    g.fill(outline);
	    
		g.setColor(java.awt.Color.WHITE);
	    g.draw(outline);
	}
}
