package schutzschild;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyMonitor extends KeyAdapter {
	Plane plane;

	public KeyMonitor(Plane plane) {
		this.plane = plane;

	}

	@Override
	public void keyPressed(KeyEvent e) {
		plane.addDirection(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		plane.stopDirection(e);
	}

}