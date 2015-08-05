import java.awt.Graphics2D;
import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class LevelView extends View {
	private static final Vec2 GRAVITY = new Vec2(0, -50f);
	private World mWorld;
	private Level mLevel;
	private ArrayList<Entity> mEntities;

	public LevelView() {
		mWorld = new World(GRAVITY);
		mLevel = new Level("WOP WOP", mWorld);
		mEntities = mLevel.getEntities();
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
