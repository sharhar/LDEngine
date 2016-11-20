package engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class Keyboard implements KeyListener{
	
	volatile public static boolean[] keys;
	volatile public static boolean[] last_keys;
	
	public static void init() {
		keys = new boolean[65536];
		last_keys = new boolean[65536];
		
		Arrays.fill(keys, false);
		Arrays.fill(last_keys, false);
	}
	
	public static void tick() {
		System.arraycopy(keys, 0, last_keys, 0, keys.length);
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
}
