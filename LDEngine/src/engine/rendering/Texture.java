package engine.rendering;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture {
	public int[][] data;
	public int width, height;
	public BufferedImage bufferedImage;
	
	public Texture(String path) {
		try {
			bufferedImage = ImageIO.read(Texture.class.getResourceAsStream(path));
		} catch (IOException e) {
			System.err.println("Failed to load image: " + path);
			return;
		}
		
		data = new int[bufferedImage.getWidth()][bufferedImage.getHeight()];
		width = bufferedImage.getWidth();
		height = bufferedImage.getHeight();
		
		for(int i = 0;i < bufferedImage.getWidth();i++) {
			for(int j = 0;j < bufferedImage.getHeight();j++) {
				data[i][j] = bufferedImage.getRGB(i, j);
			}
		}
	}
	
	public Texture(BufferedImage image) {
		bufferedImage = image;
		
		data = new int[bufferedImage.getWidth()][bufferedImage.getHeight()];
		width = bufferedImage.getWidth();
		height = bufferedImage.getHeight();
		
		for(int i = 0;i < bufferedImage.getWidth();i++) {
			for(int j = 0;j < bufferedImage.getHeight();j++) {
				data[i][j] = bufferedImage.getRGB(i, j);
			}
		}
	}
}
