import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel {
    public static final String NAME = "Titanium Gastropod v0.0";
    public static final int WINDOW_W = 800, WINDOW_H = 600;

    private BufferedImage mScreenBuffer;

    public Game() {
	mScreenBuffer = new BufferedImage(WINDOW_W, WINDOW_H, BufferedImage.TYPE_INT_ARGB);
	
	setFocusable(true);
	setPreferredSize(new Dimension(WINDOW_W, WINDOW_H));

	Thread updateThread = new Thread("Game update thread") {
		@Override
		public void run() {
		    while (true) {
			Graphics2D g = (Graphics2D) mScreenBuffer.getGraphics();
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WINDOW_W, WINDOW_H);

			// do game drawing here

			repaint();
			try {
			    Thread.sleep(30);
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
		    }
		}
	    };
	updateThread.start();
    }

    public void paint(Graphics g) {
	g.drawImage(mScreenBuffer, 0, 0, this);
    }
    
    public static void main(String args[]) {
	final JFrame frame = new JFrame(NAME);
	frame.getContentPane().add(new Game());
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.pack();
	frame.setVisible(true);
    }
}
