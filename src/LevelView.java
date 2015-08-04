import java.awt.Graphics2D;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.*;
import org.jbox2d.collision.shapes.*;

public class LevelView extends View {
    private static final Vec2 GRAVITY = new Vec2(0, -50f);
    private World mWorld;
    private Body wall;
    private Body[][] jarvis;
    
    static final int jCount=13; //Bloblings 

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
		
		{ //Create jarvis
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.DYNAMIC;
			bodyDef.angle = 0f;
			
			CircleShape shape = new CircleShape();
			shape.setRadius(3);

			FixtureDef fdef = new FixtureDef();
			fdef.shape = shape;
			fdef.friction = 1f;

			
		    
			/*
			ConstantVolumeJointDef cvjd = new ConstantVolumeJointDef();

		    cvjd.frequencyHz = 10.0f;
		    cvjd.dampingRatio = 10.0f;
		    jarvis = new Body[jCount];
			//See http://shiffman.net/itp/classes/nature/box2d_2010/blob/Blob.pde for blob
			for(int i=0;i<jCount;i++){ 
				wall = mWorld.createBody(bodyDef);
				bodyDef.position.set(50+10f*(float)Math.cos(i*Math.PI*2/jCount), 50+10f*(float)Math.sin(i*Math.PI*2/jCount));
				
				jarvis[i] = mWorld.createBody(bodyDef);
				jarvis[i].createFixture(fdef);
				
				cvjd.addBody(jarvis[i]);
				
			}
			
			ConstantVolumeJoint cvj = (ConstantVolumeJoint) mWorld.createJoint(cvjd);
			*/
			
			jarvis = new Body[jCount][jCount];
			for(int i=0;i<jCount;i++){
				for(int j=0;j<=i;j++){
					wall = mWorld.createBody(bodyDef);
//					bodyDef.position.set(300+i*6, 300+j*6);
					
					bodyDef.position.set(300+3*i*(float)Math.cos(0.3*j*Math.PI*2/(i+0.3)+1), 300+3*i*(float)Math.sin(0.3*j*Math.PI*2/(i+0.3)+1));
					
					jarvis[i][j] = mWorld.createBody(bodyDef);
					jarvis[i][j].createFixture(fdef);
				}
			}
			
//			for(int i=0;i<jCount-2;i++){
//				for(int j=0;j<jCount-2;j++){
//					ConstantVolumeJointDef cvjd = new ConstantVolumeJointDef();
//
//				    cvjd.frequencyHz = 10.0f;
//				    cvjd.dampingRatio = 10.0f;
//				    
//				    cvjd.addBody(jarvis[i][j]);
//				    cvjd.addBody(jarvis[i+1][j]);
//				    cvjd.addBody(jarvis[i+2][j]);
//				    cvjd.addBody(jarvis[i+2][j+1]);
//				    cvjd.addBody(jarvis[i+2][j+2]);
//				    cvjd.addBody(jarvis[i+1][j+2]);
//				    cvjd.addBody(jarvis[i][j+2]);
//				    cvjd.addBody(jarvis[i][j+1]);
//				    
//					ConstantVolumeJoint cvj = (ConstantVolumeJoint) mWorld.createJoint(cvjd);
//				}
//			}
			
			for(int i=0;i<jCount-2;i++){
				for(int j=0;j<=i;j++){
					ConstantVolumeJointDef cvjd = new ConstantVolumeJointDef();

				    cvjd.frequencyHz = 4.0f;
				    cvjd.dampingRatio = 10.0f;
				    
				    cvjd.addBody(jarvis[i][j]);
				    cvjd.addBody(jarvis[i+1][j+1]);
				    cvjd.addBody(jarvis[i+2][j+2]);
				    cvjd.addBody(jarvis[i+2][j+1]);
				    cvjd.addBody(jarvis[i+2][j]);
				    cvjd.addBody(jarvis[i+1][j]);
				    
					ConstantVolumeJoint cvj = (ConstantVolumeJoint) mWorld.createJoint(cvjd);
				}
			}
		
		}
	
    }
    
    public void update() {
    	mWorld.step(0.030f, 6, 2);
    	
    	Keys k = Keys.inst;
    	if(k.isPressed(KeyType.LEFT)){
    		for(Body[] aB:jarvis)
    			for(Body b:aB)
    				if(b!=null) b.applyForceToCenter(new Vec2(-1000,0));
//    		jarvis[0][0].applyForceToCenter(new Vec2(-1000,0));
    	} else if (k.isPressed(KeyType.RIGHT)){
    		for(Body[] aB:jarvis)
    			for(Body b:aB)
    				if(b!=null) b.applyForceToCenter(new Vec2(1000,0));
//    		jarvis[0][0].applyForceToCenter(new Vec2(1000,0));
    	}
    }

    public void draw(Graphics2D g) {
		g.setColor(java.awt.Color.WHITE);
		g.drawLine(20, 20, 20, 600);
		g.drawLine(20, 20, 600, 20);
		
		/*int[] xP=new int[jCount], yP=new int[jCount];
		for(int i=0;i<jCount;i++){
			Vec2 pos = jarvis[i].getPosition();
			xP[i] = (int)pos.x;
			yP[i] = (int)pos.y;
		}
		g.fillPolygon(xP, yP, jCount);*/
		
		for(Body[] aB:jarvis)
			for(Body b:aB)
				if(b!=null) g.fillOval((int)b.getPosition().x-3, (int)b.getPosition().y-3, 6, 6);
    }
}
