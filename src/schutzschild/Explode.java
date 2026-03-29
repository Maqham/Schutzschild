package schutzschild;

import java.awt.Graphics;
import java.awt.Image;

public class Explode {

	double x, y;

	static Image[] imgs = new Image[16];

	static {
		for (int i = 0; i < 16; i++) {
			imgs[i] = GameUtil.getImage("explode/e" + (i + 1) + ".gif");
			imgs[i].getWidth(null);
		}
	}

	int count;

	public void draw(Graphics g) {
		if (count <= 15) {
			g.drawImage(imgs[count], (int) x, (int) y, null);
			count++;
		}
	}

	public Explode(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public static Explode[] setNumberOfExplode(int num, double x, double y) {
		Explode[] explodes = new Explode[num];

		for (int i = 0; i < explodes.length; i++) {
//			bullets[i] = new Bullet();
			explodes[i] = new Explode(x, y);
		}

		return explodes;
	}

}
