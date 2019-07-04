package pasa.cbentley.swing.effects.bumpedimage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {
	
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	
	private Thread thread;
	private boolean running;
	private final int FPS = 30;
	private final int TARGET_TIME = 1000 / FPS;
	private int averageFPS;
	
	private BufferedImage image;
	private Graphics2D g;
	
	private GameState gameState;
	
	private Color color;
	private Font font;
	
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			addKeyListener(this);
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void run() {
		
		init();
		
		long start;
		long elapsed;
		long wait;
		
		averageFPS = FPS;
		int frameCount = 0;
		long total = 0;
		
		while(running) {
			
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			frameCount++;
			
			elapsed = System.nanoTime() - start;
			
			wait = TARGET_TIME - elapsed / 1000000;
			if(wait < 0) wait = TARGET_TIME;
			
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			total += System.nanoTime() - start;
			if(total > 1000000000) {
				averageFPS = frameCount;
				frameCount = 0;
				total -= 1000000000;
			}
			
		}
		
	}
	
	public void init() {
		running = true;
		image = new BufferedImage(WIDTH, HEIGHT, 1);
		g = image.createGraphics();
		gameState = new GameState();
		color = new Color(255, 0, 0);
		font = new Font("Arial", Font.BOLD, 14);
	}
	
	private void update() {
		gameState.update();
		Keys.update();
	}
	
	private void draw() {
		gameState.draw(g);
		g.setColor(color);
		g.setFont(font);
		g.drawString("FPS: " + averageFPS, 10, 20);
	}
	
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
	}
		
	public void keyPressed(KeyEvent key) {
		Keys.setState(key.getKeyCode(), true);
	}
	public void keyReleased(KeyEvent key) {
		Keys.setState(key.getKeyCode(), false);
	}
	public void keyTyped(KeyEvent key) {}
	
}
