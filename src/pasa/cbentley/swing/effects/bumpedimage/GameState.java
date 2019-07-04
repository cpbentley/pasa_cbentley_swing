package pasa.cbentley.swing.effects.bumpedimage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class GameState {
	
	private BufferedImage image;
	private Light light;
	private BumpedImage bumpedImage;
	private BufferedImage bumpmap;
	
	public GameState() {
		try {
			image = ImageIO.read(
				getClass().getResourceAsStream("/logo.gif")
			);
			bumpmap = ImageIO.read(
				getClass().getResourceAsStream("/bumpmap9.png")
			);
			bumpedImage = new BumpedImage(0, 0, image, bumpmap);
			light = new Light(0, 0, 120);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		bumpedImage.update(light);
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(
			bumpedImage.getImage(),
			bumpedImage.getx(),
			bumpedImage.gety(),
			null
		);
	}
	
	private void handleInput() {
		if(Keys.down(Keys.UP)) {
			light.translate(0, -3);
		}
		if(Keys.down(Keys.LEFT)) {
			light.translate(-3, 0);
		}
		if(Keys.down(Keys.DOWN)) {
			light.translate(0, 3);
		}
		if(Keys.down(Keys.RIGHT)) {
			light.translate(3, 0);
		}
		if(Keys.down(Keys.W)) {
			light.addr(2);
		}
		if(Keys.down(Keys.S)) {
			light.addr(-2);
		}
		if(Keys.down(Keys.A)) {
			bumpedImage.setContrast(-0.1);
		}
		if(Keys.down(Keys.D)) {
			bumpedImage.setContrast(0.1);
		}
	}
	
}
