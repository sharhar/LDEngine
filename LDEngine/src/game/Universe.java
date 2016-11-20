package game;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Universe {
	public static final float G = 100;
	
	public static ArrayList<Body> bodies = new ArrayList<Body>();
	private static ExecutorService executor;
	
	public static void init() {
		executor = Executors.newFixedThreadPool(8);
	}
	
	public static void tick(float time) {
		for(Body b:bodies) {
			executor.execute(() -> {
				b.tick(time);
			});
		}
		
		executor.shutdown();
		while(!executor.isTerminated()) {}
		executor = Executors.newFixedThreadPool(8);
	}
	
	public static void render() {
		for(Body b:bodies) {
			b.draw();
		}
	}
}