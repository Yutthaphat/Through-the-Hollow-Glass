package JavaLearn;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound() {

		try {
			soundURL[0] = getClass().getResource("/sound/BlueBoyAdventure.wav");
			soundURL[1] = getClass().getResource("/sound/coin.wav");
			soundURL[2] = getClass().getResource("/sound/powerup.wav");
			soundURL[3] = getClass().getResource("/sound/unlock.wav");
			soundURL[4] = getClass().getResource("/sound/fanfare.wav");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setFile(int i) {
		
		try {
			if (soundURL[i] == null) {
				System.out.println("Sound resource not found for index: " + i);
				clip = null;
				return;
			}
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
		}catch(Exception e) {
			System.out.println("Failed to load sound for index: " + i + ", error: " + e.getMessage());
			clip = null;
		}
	}
	public void play() {
		
		if (clip != null) {
			clip.start();
		} else {
			System.out.println("Clip is null in play()");
		}
	}
	public void loop() {
		
		if (clip != null) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} else {
			System.out.println("Clip is null in loop()");
		}
	}
	public void stop() {
		
		if (clip != null) {
			clip.stop();
		} else {
			System.out.println("Clip is null in stop()");
		}
	}
}
