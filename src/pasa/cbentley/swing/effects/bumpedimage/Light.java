package pasa.cbentley.swing.effects.bumpedimage;

public class Light {
	
	private int x;
	private int y;
	private int r;
	
	public Light(int i1, int i2, int i3) {
		x = i1;
		y = i2;
		r = i3;
	}
	
	public void translate(int i1, int i2) {
		x += i1;
		y += i2;
	}
	public void setr(int i) { r = i; }
	public void addr(int i) { r += i; }
	
	public int getx() { return x; }
	public int gety() { return y; }
	public int getr() { return r; }
	
}
