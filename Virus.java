package schutzschild;

import java.awt.Graphics;
import java.awt.Image;

/**
 * @author Mohammed Al-Muliki
 *
 */
public class Virus extends GameObject {
	boolean live;
	double degree;
	public static int virusZahl = 0;

	public Virus(Image img) {
		this.img = img;
		x = 200;
		y = -20;
		width = 50;
		height = 50;
		speed = 3;
		degree = 4;
		live = true;
		virusZahl++;
	}

	@Override
	public void drawSelf(Graphics g) {
		if (live == true) {
			// y += speed * Math.sin(90);
			g.drawImage(img, (int) x, (int) y, width, height, null);

			if (x < 0 || x > Constant.GAME_WIDTH - width) {
				degree = 4;

			}
		} else {
			x =-10;
			y=-10;
		}
	}

	public void move() {
		if (live) {
			y += speed * Math.sin(90);
		}
	}

	public static Virus[] setNumberOfViruses(Image virusImage, int num) {
		Virus[] viruses = new Virus[num];

		for (int i = 0; i < viruses.length; i++) {
			viruses[i] = new Virus(virusImage);
			viruses[i].x = Math.random() * 650;
		}

		return viruses;
	}

}