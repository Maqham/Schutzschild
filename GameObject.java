/**
 * 
 */
package schutzschild;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

/**
 * @author Mohammed Al-Muliki
 *
 */
public class GameObject {
	double x, y;
	int speed;
	int width, height;
	Image img;

	public void drawSelf(Graphics g) {
		g.drawImage(img, (int) x, (int) y, null);
	}

	/**
	 * @param x
	 * @param y
	 * @param speed
	 * @param width
	 * @param height
	 * @param img
	 */
	public GameObject(double x, double y, int speed, int width, int height, Image img) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.img = img;
	}

	/**
	 * @param x
	 * @param y
	 * @param img
	 */
	public GameObject(double x, double y, Image img) {
		this.x = x;
		this.y = y;
		this.img = img;
	}

	public GameObject() {

	}

	public Rectangle getRect() {
		return new Rectangle((int) x, (int) y, width, height);
	}

}