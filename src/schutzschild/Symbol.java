package schutzschild;

import java.awt.Graphics;
import java.awt.Image;

/**
 * @author Mohammed Al-Muliki
 *
 */
public class Symbol extends GameObject {
	boolean live;
	static Symbol[] symbolsList;

	public Symbol(Image img) {
		this.img = img;
		x = Math.random() * 660;
		y = -20;
		width = 50;
		height = 50;
		speed = 10;
		live = true;
	}

	@Override
	public void drawSelf(Graphics g) {
		if (live == true) {
			y += speed;
			g.drawImage(img, (int) x, (int) y, width, height, null);

		}
	}

}
