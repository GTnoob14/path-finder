package music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BeepPlayer {
	
	private Clip clip;
	private AudioInputStream inputStream;
	
	public BeepPlayer() {
		try {
			clip = AudioSystem.getClip();
			inputStream = AudioSystem.getAudioInputStream(
	        		getClass().getResourceAsStream("beep.wav"));
			clip.open(inputStream);
		} catch (Exception e) {}
	}
	
	public void start() {
		try {
			inputStream = AudioSystem.getAudioInputStream(
	        		getClass().getResourceAsStream("beep.wav"));
			clip.open(inputStream);
		} catch (Exception e) {}
		clip.start();
	}
	
	public void stop() {
		clip.close();
	}

}
