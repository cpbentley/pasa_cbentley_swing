package pasa.cbentley.swing.effects.bumpedimage;

import java.awt.event.KeyEvent;

public class Keys {
	
	public static boolean keys[] = new boolean[8];
	public static boolean prevkeys[] = new boolean[8];
	
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	public static final int W = 4;
	public static final int A = 5;
	public static final int S = 6;
	public static final int D = 7;
	
	public static void setState(int i, boolean b) {
		if(i == KeyEvent.VK_UP) keys[UP] = b;
		if(i == KeyEvent.VK_LEFT) keys[LEFT] = b;
		if(i == KeyEvent.VK_DOWN) keys[DOWN] = b;
		if(i == KeyEvent.VK_RIGHT) keys[RIGHT] = b;
		if(i == KeyEvent.VK_W) keys[W] = b;
		if(i == KeyEvent.VK_A) keys[A] = b;
		if(i == KeyEvent.VK_S) keys[S] = b;
		if(i == KeyEvent.VK_D) keys[D] = b;
	}
	
	public static void update() {
		for(int i = 0; i < keys.length; i++) {
			prevkeys[i] = keys[i];
		}
	}
	
	public static boolean pressed(int i) {
		return keys[i] && !prevkeys[i];
	}
	
	public static boolean down(int i) {
		return keys[i];
	}
	
}
