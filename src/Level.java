import java.io.BufferedReader;
import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.*;
import org.jbox2d.collision.shapes.*;

public class Level {
	private ArrayList<Entity> mEntities;

	public Level(String name, World world) {
		System.out.println("Loading Level " + name);
		mEntities = new ArrayList<Entity>();

		try {
			BufferedReader in = Utils.getFileInputStream("levels/" + name + ".lsf");

			String line;
			int lineno = 0;
			while ((line = in.readLine()) != null) {
				lineno++;
				line = line.trim();
				if (line.isEmpty())
					continue;

				String[] words = line.split("\\s+");
				switch (words[0]) {
				case "#": // comment
					break;

				case "RECTWALL": {
					if (words.length != 8) {
						throw new RuntimeException("RECTWALL line has wrong number of words: `" + line + "'");
					}

					float x = Float.parseFloat(words[1]);
					float y = Float.parseFloat(words[2]);
					float t = Float.parseFloat(words[3]);
					float w = Float.parseFloat(words[4]);
					float h = Float.parseFloat(words[5]);
					float e = Float.parseFloat(words[6]);
					float f = Float.parseFloat(words[7]);

					mEntities.add(new Wall(world, x, y, t, w, h, e, f));
					break;
				}
				

				case "POLYWALL": {
					if (words.length != 4) {
						throw new RuntimeException("POLYWALL line has wrong number of words: `" + line + "'");
					}
					
					String[] coords = words[1].split(",");
					if (coords.length%2 != 0) {
						throw new RuntimeException("POLYWALL line needs even number of coordinates, found "+coords.length+": `" + line +"'");
					}
					
					int edgeCount = coords.length/2;
					Vec2[] verts = new Vec2[edgeCount];
					for(int j=0;j<edgeCount;j++){
						verts[j]=new Vec2(Float.valueOf(coords[2*j]),Float.valueOf(coords[2*j+1]));
					}
					
					float e = Float.parseFloat(words[2]);
					float f = Float.parseFloat(words[3]);

					mEntities.add(new Wall(world, verts, e, f));
					break;
				}
				
				case "JARVIS": {
					mEntities.add(new Jarvis(world));
					break;
				}

				default:
					throw new RuntimeException("Unknown LSF line type `" + words[0] + "' in line `" + line + "'");
				}
			}
		} catch (java.io.IOException e) {
			throw new RuntimeException("Could not load level `" + name + "': " + e.getMessage());
		}

		/*
		 * BodyDef bodyDef = new BodyDef(); bodyDef.type = BodyType.STATIC;
		 * bodyDef.position.set(0, 0); bodyDef.angle = 0f; Body wall =
		 * world.createBody(bodyDef);
		 * 
		 * PolygonShape shape = new PolygonShape(); shape.setAsBox(300, 10, new
		 * Vec2(150,5), 0);
		 * 
		 * FixtureDef fdef = new FixtureDef(); fdef.friction = 1f; fdef.shape =
		 * shape; shape.setAsBox(300, 10, new Vec2(300,10), 0);
		 * wall.createFixture(fdef); shape.setAsBox(10, 300, new Vec2(10,300),
		 * 0); wall.createFixture(fdef);
		 * 
		 * mEntities.add(new Jarvis(world));
		 */
	}

	public ArrayList<Entity> getEntities() {
		return mEntities;
	}

	// This should always be called before the Level gets deallocated.
	// Consider this a deconstructor.
	public void onDestroy() {
	}
}
