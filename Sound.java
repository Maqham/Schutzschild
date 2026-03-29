package schutzschild;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

/**
 * Die Sound-Klasse verwaltet das Abspielen von Audio-Clips in einem Spiel.
 */
public class Sound {

	// Der Audio-Clip.

	Clip clip;

	// Ein Array von URLs für verschiedene Sounddateien.
	URL soundURL[] = new URL[3];

	/**
	 * Konstruiert ein Sound-Objekt und initialisiert die URLs der Sounddateien.
	 */
	public Sound() {
		// HAUPTMUSIK DES SPIELS
		soundURL[0] = getClass().getResource("/sound/hauptMusik.wav");

		// TASTEN-SOUNDEFFEKTE
		soundURL[1] = getClass().getResource("/sound/bullet.wav");

		// KOLLISIONSSOUND (TREFFER-SOUND)
		soundURL[2] = getClass().getResource("/sound/equip1.wav");
	}

	/**
	 * Setzt die abzuspielende Audiodatei basierend auf dem angegebenen Index.
	 *
	 * @param i Der Index der Sounddatei.
	 */
	public void setFile(int i) {
		try {
			System.out.println("Lade Audiodatei: " + soundURL[i]);
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			System.out.println("Audiodatei erfolgreich geladen.");
		} catch (Exception e) {
			// Die Ausnahme ausgeben oder protokollieren, um das Problem zu identifizieren
			e.printStackTrace();
		}
	}

	/**
	 * Startet das Abspielen des Audio-Clips.
	 */
	public void play() {
		clip.start();
	}

	/**
	 * Lässt den Audio-Clip kontinuierlich wiederholen.
	 */
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	/**
	 * Stoppt das Abspielen des Audio-Clips.
	 */
	public void stop() {
		clip.stop();
	}
}