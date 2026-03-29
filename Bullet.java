package schutzschild;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @author Mohammed Al-Muliki
 *
 */
public class Bullet extends GameObject {
	double degree;
	double deg;
	boolean live;

	public Bullet(int x, int y) {
		this.x = x + 10;
		this.y = y - 10;
		width = 5;
		height = 10;
		speed = 8;
		degree = 4;
		live = true;
	}

	public void draw(Graphics g) {
		if (live == true) {
			Color c = g.getColor();
			g.setColor(Color.YELLOW);
			g.fillOval((int) x, (int) y, width, height);
			y += speed * Math.sin(30);
			g.setColor(c);
		}
	}

	public static Bullet[] setNumberofBullets(int num, double x, double y) {
		Bullet[] bullets = new Bullet[num];

		for (int i = 0; i < bullets.length; i++) {
			bullets[i] = new Bullet((int) x + (i * 10) * (int) Math.pow(-1, i), (int) y);

		}
		return bullets;
	}

}
