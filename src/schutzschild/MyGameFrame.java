package schutzschild;

/**
 * @author Mohammed Al-Muliki
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("serial")
public class MyGameFrame extends Frame implements MouseListener {

	Image planeImage = GameUtil.getImage("images/rocket.png");
	Image bg = GameUtil.getImage("images/Background.jpg");
	Image playButtonImage = GameUtil.getImage("images/Play Button.png");
	Image tryAgainImage = GameUtil.getImage("images/Try Again.png");
	Image menuButtonImage = GameUtil.getImage("images/Main Menu.png");
	Image title = GameUtil.getImage("images/bgT.jpg");
	Image upArrow = GameUtil.getImage("images/Up-Arrow.png");
	Image downArrow = GameUtil.getImage("images/Down-Arrow.png");
	Image virusImage = GameUtil.getImage("images/virus.png");

	Button playButton = new Button(playButtonImage, 277.5, 500);
	Button tryAgainButton = new Button(tryAgainImage, 300, 400);
	Button menuButton = new Button(menuButtonImage, 300, 480);
	Button upButton = new Button(upArrow, 345, 350);
	Button downButton = new Button(downArrow, 345, 450);
	Button[] banner;

	Plane plane = new Plane(planeImage, 450, 630);
	Virus[] viruses;
	Bullet[] bullets;
	Explode explosion;

	Date startTime;
	Date startTime1;
	Date endTime;

	static int level = 1;
	static int newCount = 1;
	static int[] valuIndex = new int[10];

	static boolean iIsNull = true;
	boolean firstTry = true;
	boolean crash = false;
	boolean take = false;
	static boolean newSymbolsArray = false;
	static boolean bulletIsLive = true;

	private Symbol[] symbols = new Symbol[10]; // Array für 10 Symbole
	private int symbolCount = 0; // Anzahl der derzeit angezeigten Symbole
	private Timer symbolTimer;
	KeyMonitor keyMonitor = new KeyMonitor(plane);
	// SOUND
	Sound music = new Sound();
	Sound soundEffect = new Sound();

	public MyGameFrame() {
		creatSymbols();
		playMusic(0);

	}

	/**
	 * Wiederholt die angegebene Musikspur.
	 *
	 * @param i Der Index der abzuspielenden Musikspur.
	 */
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}

	/**
	 * Beendet die aktuell abgespielte Musik.
	 */
	public void stopMusic() {
		music.stop();
	}

	/**
	 * Spielt den angegebenen Soundeffekt ab.
	 *
	 * @param i Der Index des abzuspielenden Soundeffekts.
	 */
	public void playSE(int i) {
		soundEffect.setFile(i);
		soundEffect.play();
	}

	public void creatSymbols() {
		// Initialisierungen...

		// Timer einrichten
		symbolTimer = new Timer();
		symbolTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (playButton.isPlay()) {
					if (symbolCount < symbols.length && plane.live) {

						symbols[symbolCount] = new Symbol(
								GameUtil.getImage("Symbole/numbers/Number" + (symbolCount + 1) + ".png")); // Erstellen Sie
																											// ein neues
																											// Symbol
						symbolCount++;
						repaint(); // Fordern Sie eine Neumalerei des Fensters an
					} else if (!plane.live) {
						this.cancel(); // Stoppen Sie den Timer, wenn das Flugzeug nicht mehr lebt
					}
				}
			}
		}, 0, 10000); // Start sofort, wiederhole alle 5000 Millisekunden
		newSymbolsArray = false; // Setzen Sie newSymbolsArray auf false, um sicherzustellen, dass keine weiteren
									// Symbole erstellt werden

	}

	public void resetSymbols() {
		symbolTimer.cancel();
		symbolTimer.purge();
		for (int i = 0; i < symbols.length; i++) {
			symbols[i] = null;
		}

		symbolCount = 0;
		newSymbolsArray = true;
		creatSymbols();

		iIsNull = true;
		for (int j = 0; j < valuIndex.length; j++) {
			valuIndex[j] = 0;
		}
	}

	@Override
	public void paint(Graphics g) {

		Color c = g.getColor();
		g.drawImage(bg, 0, 0, null);

		if (!playButton.isPlay()) {
			g.drawImage(title, 0, 0, null);
			playButton.drawSelf(g);
			upButton.drawSelf(g);
			downButton.drawSelf(g);

			if (banner == null) {
				createBanner();
			}

			banner[level - 1].drawSelf(g);
		}

		if (playButton.isPlay()) {
			playGame(g, c);

		}
	}

	// Zeichnen Sie die Symbole
	public void crashSymbol(Graphics g) {
		for (int i = 0; i < symbolCount; i++) {
			if (symbols[i] != null) {
				symbols[i].drawSelf(g); // Angenommen, die Symbolklasse hat eine drawSelf-Methode

			}

			System.out.println(i + "SymbolIdex");

			take = symbols[i].getRect().intersects(plane.getRect());

			numberBullets(take, i);

		}

		take = false;
		System.out.println(take + "hhhh");

	}

	public int numberBullets(boolean take, int i) {

		boolean iReapt = false;
		if (take) {
			symbols[i].live = false;
			if (i == 0 && iIsNull == true) {
				newCount = 2;
				iIsNull = false;
				playSE(2);
			}
			for (int element : valuIndex) {
				if (element == i) {
					iReapt = true;
				}
			}
			if (iReapt != true) {
				valuIndex[i] = i;
				playSE(2);
				return newCount++;
			}
		}
		return newCount;
	}

	public void playGame(Graphics g, Color c) {

		if (startTime == null) {
			if (bulletIsLive == true)
				playSE(1);
			startTime = new Date();
			startTime1 = new Date();
			viruses = Virus.setNumberOfViruses(virusImage, level);
			bullets = Bullet.setNumberofBullets(newCount, plane.x, plane.y);
		}
		plane.drawSelf(g);

		Date currentTime = new Date();
		double elapsedTime = currentTime.getTime() - startTime.getTime();
		double elapsedTime1 = currentTime.getTime() - startTime1.getTime();

		if (elapsedTime > 2000 && plane.live) {
			if (bulletIsLive == true)
				playSE(1);
			// Überprüfe, ob mehr als 2000 Millisekunden (2 Sekunden) vergangen sind
			// Passt das Intervall nach Bedarf an (1000 ms = 1 Sekunde)
			if (elapsedTime1 < 90000) {
				// Rufe die Methode auf, um zwei Bullets zu erhalten
				Virus[] newViruses = Virus.setNumberOfViruses(virusImage, level);
				// Füge die neuen Bullets dem aktuellen bullets-Array hinzu
				Virus[] extendedViruses = new Virus[viruses.length + newViruses.length];
				System.arraycopy(viruses, 0, extendedViruses, 0, viruses.length);
				System.arraycopy(newViruses, 0, extendedViruses, viruses.length, newViruses.length);

				viruses = extendedViruses;

			}
			if (elapsedTime1 < 98000) {
				Bullet[] newBullets;
				if (level == 3) {
					newBullets = Bullet.setNumberofBullets(newCount + 1, plane.x, plane.y);

				} else {
					newBullets = Bullet.setNumberofBullets(newCount, plane.x, plane.y);

				}
				// Füge die neuen Bullets dem aktuellen bullets-Array hinzu
				Bullet[] extendedBullets = new Bullet[bullets.length + newBullets.length];
				System.arraycopy(bullets, 0, extendedBullets, 0, bullets.length);
				System.arraycopy(newBullets, 0, extendedBullets, bullets.length, newBullets.length);
				bullets = extendedBullets;
				// Aktualisiere die Startzeit für das nächste Intervall
				startTime = new Date();
			}
			if (elapsedTime1 > 98000 && plane.live == true) {
				plane.live = false;
			}

		}

		crashSymbol(g);
		crashViruses(g);
		endgameCheck(elapsedTime1, g);

		g.setColor(c);

		for (int i = 0; i < bullets.length; i++) {
			if (plane.live) {
				bullets[i].draw(g);
				bullets[i].draw(g);
			}

			for (int j = 0; j < viruses.length; j++) {
				boolean attack = bullets[i].getRect().intersects(viruses[j].getRect());
				if (attack) {
					viruses[j].live = false;

					// write to file
					WriteFile.write();
					// read file
					ReadFile.readFile();

				}

			}
		}

		g.setColor(c);

	}

	// TODO Auto-generated method stub
	private void endgameCheck(double elapsedTime1, Graphics g) {
		if (!plane.live && elapsedTime1 < 98000) {
			newCount = 0;
			g.setColor(Color.red);
			Font f = new Font("Serif", Font.BOLD, 50);
			g.setFont(f);
			g.drawString("Game Over!", 220, 260);
			tryAgainButton.drawSelf(g);
			menuButton.drawSelf(g);
			bulletIsLive = false;

		} else if (!plane.live && elapsedTime1 > 98000) {
			newCount = 0;
			plane.live = false;
			g.setColor(Color.yellow);
			Font f = new Font("Serif", Font.BOLD, 50);
			g.setFont(f);
			g.drawString("Glückwunsch!", 190, 150);
			bulletIsLive = false;
			g.drawString("Du hast gewonnen", 135, 200);
			tryAgainButton.drawSelf(g);
			menuButton.drawSelf(g);
		}

	}

	private void crashViruses(Graphics g) {
		// TODO Auto-generated method stub
		for (int i = 0; i < viruses.length; i++) {
			viruses[i].drawSelf(g);
			if (plane.live) {
				viruses[i].move();
			}
			if (viruses[i].y > 650) {
				System.out.println(viruses[i].y);
				plane.live = false;
			}

			crash = viruses[i].getRect().intersects(plane.getRect());

			if (crash) {
				plane.live = false;
				if (explosion == null) {
					explosion = new Explode(plane.x, plane.y);
					endTime = new Date();
				}

				explosion.draw(g);
				// write to file
				WriteFile.write();
				// read file
				ReadFile.readFile();

			}
		}
	}

	class PaintThread extends Thread {
		@Override
		public void run() {
			while (true) {
				repaint(); // repaint window
				try {
					Thread.sleep(40);// fps 1s=1000ms
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void createBanner() {
		banner = new Button[3];
		for (int i = 0; i < 3; i++) {
			banner[i] = new Button((GameUtil.getImage("images/Level-" + (i + 1) + ".png")), 310, 400);
		}
	}

	public void launchFrame() {
		// title
		setTitle("Programmieren Projekt");
		// set to visible to true
		setVisible(true);
		if (firstTry) {
			// set size
			setSize(720, 720);
			// set location;
			setLocation(400, 30);
			// disable maximize button
			setResizable(false);

			createBanner();

			addMouseListener(this);

			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			new PaintThread().start();// active repaint window

			addKeyListener(keyMonitor);// give window add key monitor

		}

	}

	// fix flicker for Frame
	private Image offScreenImage = null;

	public void update(Graphics g) {
		if (offScreenImage == null)
			offScreenImage = this.createImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);// width and height

		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!playButton.isPlay()) {
			if (playButton.contains(e.getX(), e.getY())) {
				playButton.setPlay(true);
			}
			if (upButton.contains(e.getX(), e.getY())) {
				if (level < 3) {
					level++;
				}
			}

			if (downButton.contains(e.getX(), e.getY())) {
				if (level > 1) {
					level--;
				}
			}
		}

		if (!plane.live) {
			if (tryAgainButton.contains(e.getX(), e.getY())) {
				firstTry = false;
				plane.live = true;
				newCount = 1;
				plane.x = 450;
				plane.y = 630;
				bulletIsLive = true;
				explosion = null;
				startTime = null;
				resetSymbols(); // Hier wird die Methode aufgerufen, um die Symbole zurückzusetzen
				launchFrame();
				WriteFile.setWrite(true);
				ReadFile.setRead(true);
				ReadFile.resetTop();
				ReadFile.readFile();
				if (ReadFile.getResults().size() > 10) {
					UpdateFile.update(ReadFile.getResults());
				}
			}

			if (menuButton.contains(e.getX(), e.getY())) {
				firstTry = false;
				plane.live = true;
				newCount = 1;
				plane.x = 450;
				plane.y = 630;
				bulletIsLive = true;
				explosion = null;
				startTime = null;
				resetSymbols(); // Hier wird die Methode aufgerufen, um die Symbole zurückzusetzen
				playButton.setPlay(false);
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the level
	 */
	public static int getLevel() {
		return level;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

}