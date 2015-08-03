import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;

public class Keys implements KeyListener {
	public enum KeyType { UP, DOWN, LEFT, RIGHT, JUMP };

	private HashSet<KeyType> keyMap;
	private HashSet<KeyType> keyMapFrame;

	public Keys() {
		this.keyMap = new HashSet<KeyType>();
		this.keyMapFrame = new HashSet<KeyType>();
	}

	public void keyTyped(KeyEvent e) { }

	public void keyPressed(KeyEvent e) {
		KeyType k = translateKeyEvent(e);
		if (k != null) {
			this.keyMap.add(k);
			this.keyMapFrame.add(k);
		}
	}

	public void keyReleased(KeyEvent e) {
		KeyType k = translateKeyEvent(e);
		if (k != null) {
			this.keyMap.remove(k);
		}
	}

	public void tick() {
		this.keyMapFrame.clear();
	}

	public boolean isPressed(KeyType k) {
		return this.keyMap.contains(k);
	}

	public boolean isPressedFrame(KeyType k) {
		return this.keyMapFrame.contains(k);
	}

	private KeyType translateKeyEvent(KeyEvent e) {
		switch (e.getKeyCode()) {
			case 'W':
				return KeyType.UP;
			case 'A':
				return KeyType.LEFT;
			case 'S':
				return KeyType.DOWN;
			case 'D':
				return KeyType.RIGHT;
			case ' ':
				return KeyType.JUMP;
			default:
				return null;
		}
	}
}
