package engine.rendering;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import engine.utils.math.Color;
import engine.utils.math.MathUtils;
import engine.utils.math.Matrix4f;
import engine.utils.math.Vector2f;
import engine.utils.math.Vector3f;
import engine.utils.math.Vector4f;

import game.Game;

public class Renderer {
	
	private static BufferedImage imageBuffer;
	public static Graphics imageBufferGraphics;
	public static Matrix4f projection;
	private static int thNum;
	
	volatile protected static int w, h;
	volatile protected static int[] imageBufferData;
	volatile protected static int[] depthBufferData;
	private static int clearColor;
	
	private static ExecutorService executor;
	
	static class RenderWorker implements Runnable{
		int startx, stopx, starty, stopy;
		Vector4f vf, vf1, vf2;
		Vertex v1, v2, v3;
		float m, m1, m2;
		boolean inverse;
		Texture texture;
		
		public RenderWorker(
				int startx, int starty, int stopx, int stopy, 
				Vector4f vf, Vector4f vf1, Vector4f vf2, 
				Vertex v1, Vertex v2, Vertex v3,
				float m, float m1, float m2,
				boolean inverse, Texture texture) {
			this.startx = startx;
			this.stopx = stopx;
			this.starty = starty;
			this.stopy = stopy;
			
			this.vf = vf;
			this.vf1 = vf1;
			this.vf2 = vf2;
			
			this.v1 = v1;
			this.v2 = v2;
			this.v3 = v3;
			
			this.m = m;
			this.m1 = m1;
			this.m2 = m2;
			
			this.inverse = inverse;
			this.texture = texture;
		}
		
		public void run() {
			Vector2f temp = new Vector2f();
			Vector3f bc = new Vector3f();
			int tx = 0;
			int ty = 0;
			float x = 0;
			float y = 0;
			boolean l1 = false;
			boolean l2 = false;
			boolean vf1ev1 = vf1.equals(v1);
			boolean vf2ev2 = vf2.equals(v2);
			boolean vfev3 = vf.equals(v3);
			int depth = 0;
			
			for(int xp = startx;xp < stopx;xp++) {
				for(int yp = starty;yp < stopy;yp++) {
					x = xp*2.0f/w-1;
					y = yp*2.0f/h-1;
					
					l1 = y > v2.y ? 
							(vf1ev1 ? ((x - vf1.x)*m1 < y - vf1.y) : !((x - vf1.x)*m1 < y - vf1.y)) : 
							(vf2ev2 ? ((x - vf2.x)*m2 < y - vf2.y) : !((x - vf2.x)*m2 < y - vf2.y));
					
					l2 = vfev3 ? ((x - vf.x)*m < y - vf.y) : !((x - vf.x)*m < y - vf.y);
					
					if(inverse ? !l1 && !l2 : (l1 && l2)) {
						temp.x = x;
						temp.y = y;
						MathUtils.barrycentric(bc, v1, v2, v3, temp);
						tx = ((int)((v1.uv.x * bc.x + v2.uv.x * bc.y + v3.uv.x * bc.z) * (texture.width-1)))%texture.width;
						ty = ((int)((v1.uv.y * bc.x + v2.uv.y * bc.y + v3.uv.y * bc.z) * (texture.height-1)))%texture.height;
						
						depth = (int)((v1.z * bc.x + v2.z *bc.y + v3.z * bc.z)*-1000);
						
						if(depth < depthBufferData[yp * w + xp] && (texture.data[tx][ty] & 0xff000000) != 0) {
							imageBufferData[yp * w + xp] = texture.data[tx][ty];
							depthBufferData[yp * w + xp] = depth;
						}
					}
				}
			}
		}
	}
	
	public static void init(int width, int height, int threadNum, Matrix4f projectionMatrix) {
		w = width;
		h = height;
		projection = projectionMatrix;
		
		depthBufferData = new int[w*h];
		imageBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		imageBufferData = ((DataBufferInt)imageBuffer.getRaster().getDataBuffer()).getData();
		
		setClearColor(0xffffffff);
		
		thNum = threadNum;
		executor = Executors.newFixedThreadPool(thNum);		
	}
	
	public static void setThreadNum(int threadNum) {
		thNum = threadNum;
	}
	
	public static void setClearColor(int color) {
		clearColor = color;
	}
	
	public static void clear() {
		Arrays.fill(imageBufferData, clearColor);
		
		Arrays.fill(depthBufferData, Integer.MAX_VALUE);
		
		imageBufferGraphics = imageBuffer.getGraphics();
	}
	
	public static void drawTriSmartTextured(Vertex v1, Vertex v2, Vertex v3, Texture texture) {
		boolean v1t = v1.y > v2.y && v1.y > v3.y;
		boolean v1b = v1.y < v2.y && v1.y < v3.y;
		
		boolean v2t = v2.y > v3.y;
		boolean v2b = v2.y < v3.y;
		
		Vertex top =    v1t ? v1 : (v2t ? v2 : v3);
		Vertex bottom = v1b ? v1 : (v2b ? v2 : v3);
		
		boolean v1nm = v1.equals(top) || v1.equals(bottom);
		boolean v2nm = v2.equals(top) || v2.equals(bottom);
		Vertex middle = v1nm ? (v2nm ? v3 : v2) : v1;
		
		drawTriTextured(top, middle, bottom, texture);
	}
	
	public static void drawTriTextured(Vertex v1, Vertex v2, Vertex v3, Texture texture) {
		float m = (v1.y - v3.y)/(v1.x - v3.x);
		float m1 = (v1.y - v2.y)/(v1.x - v2.x);
		float m2 = (v2.y - v3.y)/(v2.x - v3.x);
		
		Vector4f vf = v1.x < v3.x ? v1 : v3;
		Vector4f vf1 = v1.x < v2.x ? v1 : v2;
		Vector4f vf2 = v2.x < v3.x ? v2 : v3;
		
		Vector4f vfl = vf.x < vf1.x ? vf : vf1;
		Vector4f vfr = v1.x > v2.x && v1.x > v3.x ? v1 : (v2.x > v3.x ? v2 : v3);
		
		boolean inverse = vf.equals(v3) ? !((v2.x - vf.x)*m < v2.y - vf.y) : ((v2.x - vf.x)*m < v2.y - vf.y);
		
		int startx = (int) (((vfl.x + 1)/2.0f)*w);
		int stopx =  (int) (((vfr.x + 1)/2.0f)*w);
		int starty = (int) (((v3.y + 1)/2.0f)*h);
		int stopy =  (int) (((v1.y + 1)/2.0f)*h);
		
		startx = startx < 0 ? 0 : startx;
		startx = startx > w ? w : startx;
		stopx = stopx < 0 ? 0 : stopx;
		stopx = stopx > w ? w : stopx;
		
		starty = starty < 0 ? 0 : starty;
		starty = starty > h ? h : starty;
		stopy = stopy < 0 ? 0 : stopy;
		stopy = stopy > h ? h : stopy;
		
		int xint = (stopx - startx)/4;
		
		executor.execute(new RenderWorker(startx     , starty, startx+xint, stopy, vf, vf1, vf2, v1, v2, v3, m, m1, m2, inverse, texture));
		executor.execute(new RenderWorker(startx+xint, starty, startx+xint*2, stopy, vf, vf1, vf2, v1, v2, v3, m, m1, m2, inverse, texture));
		executor.execute(new RenderWorker(startx+xint*2, starty, startx+xint*3, stopy, vf, vf1, vf2, v1, v2, v3, m, m1, m2, inverse, texture));
		executor.execute(new RenderWorker(startx+xint*3, starty, stopx, stopy, vf, vf1, vf2, v1, v2, v3, m, m1, m2, inverse, texture));
	}
	
	public static void drawTriSmart(Vertex v1, Vertex v2, Vertex v3) {
		boolean v1t = v1.y > v2.y && v1.y > v3.y;
		boolean v1b = v1.y < v2.y && v1.y < v3.y;
		
		boolean v2t = v2.y > v3.y;
		boolean v2b = v2.y < v3.y;
		
		Vertex top =    v1t ? v1 : (v2t ? v2 : v3);
		Vertex bottom = v1b ? v1 : (v2b ? v2 : v3);
		
		boolean v1nm = v1.equals(top) || v1.equals(bottom);
		boolean v2nm = v2.equals(top) || v2.equals(bottom);
		Vertex middle = v1nm ? (v2nm ? v3 : v2) : v1;
		
		drawTri(top, middle, bottom);
	}
	
	public static void drawTri(Vertex v1, Vertex v2, Vertex v3) {
		float m = (v1.y - v3.y)/(v1.x - v3.x);
		float m1 = (v1.y - v2.y)/(v1.x - v2.x);
		float m2 = (v2.y - v3.y)/(v2.x - v3.x);
		
		Vector4f vf = v1.x < v3.x ? v1 : v3;
		Vector4f vf1 = v1.x < v2.x ? v1 : v2;
		Vector4f vf2 = v2.x < v3.x ? v2 : v3;
		
		Vector4f vfl = vf.x < vf1.x ? vf : vf1;
		Vector4f vfr = v1.x > v2.x && v1.x > v3.x ? v1 : (v2.x > v3.x ? v2 : v3);
		
		boolean inverse = vf.equals(v3) ? !((v2.x - vf.x)*m < v2.y - vf.y) : ((v2.x - vf.x)*m < v2.y - vf.y);
		
		Vector2f temp = new Vector2f();
		
		float wr = 1.0f/w;
		float hr = 1.0f/h;
		
		int startx = (int) (((vfl.x + 1)/2.0f)*w);
		int stopx =  (int) (((vfr.x + 1)/2.0f)*w);
		int starty = (int) (((v3.y + 1)/2.0f)*h);
		int stopy =  (int) (((v1.y + 1)/2.0f)*h);
		
		startx = startx < 0 ? 0 : startx;
		startx = startx > w ? w : startx;
		stopx = stopx < 0 ? 0 : stopx;
		stopx = stopx > w ? w : stopx;
		
		starty = starty < 0 ? 0 : starty;
		starty = starty > h ? h : starty;
		stopy = stopy < 0 ? 0 : stopy;
		stopy = stopy > h ? h : stopy;
		
		Vector3f bc = new Vector3f();
		
		for(int xp = startx;xp < stopx;xp++) {
			for(int yp = starty;yp < stopy;yp++) {
				float x = xp*wr*2-1;
				float y = yp*hr*2-1;
				
				boolean l1 = y > v2.y ? 
						(vf1.equals(v1) ? ((x - vf1.x)*m1 < y - vf1.y) : !((x - vf1.x)*m1 < y - vf1.y)) : 
						(vf2.equals(v2) ? ((x - vf2.x)*m2 < y - vf2.y) : !((x - vf2.x)*m2 < y - vf2.y));
				boolean l2 = vf.equals(v3) ? ((x - vf.x)*m < y - vf.y) : !((x - vf.x)*m < y - vf.y);
				
				if(inverse ? !l1 && !l2 : (l1 && l2)) {
					temp.x = x;
					temp.y = y;
					MathUtils.barrycentric(bc, v1, v2, v3, temp);
					imageBuffer.setRGB(xp, yp, new Color(
							v1.color.r * bc.x + v2.color.r * bc.y + v3.color.r * bc.z,
							v1.color.g * bc.x + v2.color.g * bc.y + v3.color.g * bc.z,
							v1.color.b * bc.x + v2.color.b * bc.y + v3.color.b * bc.z
							).getIntRGB());
				}
			}
		}
	}
	
	public static BufferedImage getImageBuffer() {
		return imageBuffer;
	}
	
	public static void addToExecutor(Runnable runnable) {
		executor.execute(runnable);
	}
	
	public static void flushExecutor() {
		executor.shutdown();
		while(!executor.isTerminated()) {}
		executor = Executors.newFixedThreadPool(thNum);
	}
	
	public static void drawBuffer(Graphics g) {
		imageBufferGraphics.dispose();
		executor.shutdown();
		while(!executor.isTerminated()) {}
		g.drawImage(imageBuffer, 0, Game.HEIGHT, Game.WIDTH, 0, 0, 0, w, h, null);
		executor = Executors.newFixedThreadPool(thNum);
	}
}
