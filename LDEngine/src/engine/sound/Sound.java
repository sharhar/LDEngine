package engine.sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	
	Thread thread;
	AudioInputStream inputStream;
	Clip clip;
	
	public Sound(String path) {
		thread = null;
		
		try {
			clip = AudioSystem.getClip();
			inputStream = AudioSystem.getAudioInputStream(
					Sound.class.getResourceAsStream(path));
			clip.open(inputStream);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		thread = new Thread(() -> {
			try {
				clip.stop();
				clip.setMicrosecondPosition(0);
				clip.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		});
		thread.start();
	}
	
	@SuppressWarnings("deprecation")
	public void stop() {
		thread.stop();
	}
}
