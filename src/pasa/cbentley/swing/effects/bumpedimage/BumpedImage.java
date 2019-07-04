package pasa.cbentley.swing.effects.bumpedimage;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class BumpedImage {
	
	// position
	private int x;
	private int y;
	
	// images
	private BufferedImage image;
	private BufferedImage copy;
	private int[][] map;
	private int w;
	private int h;
	
	// bump map
	private BufferedImage bumpmap;
	private Point[][] nmap;
	
	private int softness;
	private double power;
	
	public BumpedImage(int x, int y,
			BufferedImage b1, BufferedImage b2) {
		
		// set position
		this.x = x;
		this.y = y;
		
		// get image and create copy image
		image = b1;
		w = b1.getWidth();
		h = b1.getHeight();
		copy = new BufferedImage(w, h, 1);
		
		// get the bump map
		bumpmap = b2;
		
		// initialize arrays
		map = new int[h][w];
		nmap = new Point[h][w];
		
		// get colors and create new normals
		for(int i = 0; i < h; i++) {
			for(int j = 0; j < w; j++) {
				int c = (bumpmap.getRGB(j, i) >> 16) & 0xff;
				map[i][j] = c;
				nmap[i][j] = new Point(c, c);
			}
		}
		
		// calculate normals
		for(int i = 0; i < h; i++) {
			for(int j = 0; j < w; j++) {
				if(i == 0 || i == h - 1 || j == 0 || j == w - 1)
					continue;
				nmap[i][j].x = map[i][j - 1] - map[i][j + 1];
				nmap[i][j].y = map[i - 1][j] - map[i + 1][j];
			}
		}
		
		// other
		power = 14;
		softness = (int) Math.pow(2, power);
		
	}
	
	public void update(Light l) {
		
		// for every pixel
		for(int i = 0; i < h; i++) {
			for(int j = 0; j < w; j++) {
				
				// get light info
				int lx = l.getx();
				int ly = l.gety();
				int lr = l.getr();
				
				// get normal vector
				double Ax = nmap[i][j].x;
				double Ay = nmap[i][j].y;
				
				// get light vector
				double Bx = lx - (x + j);
				double By = ly - (y + i);
				
				// calculate dot product
				double dp = Ax * Bx + Ay * By;
				
				// get original image pixel color
				int rgb = image.getRGB(j, i);
				int r = (rgb >> 16) & 0xff;
				int g = (rgb >> 8) & 0xff;
				int b = rgb & 0xff;
				
				// apply lighting to pixel color
				double dist = Math.sqrt(Bx * Bx + By * By);
				double percent = (dp + softness / 2) / softness;
				double percent2 = (dist / lr);
				
				int light = (int) (percent * 255);
				int shade = (int) (percent2 * 255);
				
				r += light;
				g += light;
				b += light;
				
				r -= shade;
				g -= shade;
				b -= shade;
				
				if(r < 0) r = 0;
				if(r > 255) r = 255;
				if(g < 0) g = 0;
				if(g > 255) g = 255;
				if(b < 0) b = 0;
				if(b > 255) b = 255;
				
				// put final pixel color in copy
				int finalrgb = (r << 16) + (g << 8) + b;
				copy.setRGB(j, i, finalrgb);
				
			}
		}
	}
	
	public void setContrast(double d) {
		power += d;
		softness = (int) Math.pow(2, power);
	}
	
	public int getx() { return x; }
	public int gety() { return y; }
	public BufferedImage getImage() {
		return copy;
	}
	
}
