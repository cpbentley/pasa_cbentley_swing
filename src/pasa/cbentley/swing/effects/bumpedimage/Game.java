package pasa.cbentley.swing.effects.bumpedimage;

import javax.swing.JFrame;

public class Game {
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Bump Map Test");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(new GamePanel());
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		
	}
	
}
