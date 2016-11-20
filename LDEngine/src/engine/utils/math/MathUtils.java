package engine.utils.math;

public class MathUtils {
	public static void barrycentric(Vector3f data, Vector2f v1, Vector2f v2, Vector2f v3, Vector2f p) {
		float det = (v2.y - v3.y)*(v1.x - v3.x) + (v3.x - v2.x)*(v1.y - v3.y);
		float px = p.x - v3.x;
		float py = p.y - v3.y;
		
		float bc1 = ((v2.y - v3.y)*px + (v3.x - v2.x)*py)/det;
		float bc2 = ((v3.y - v1.y)*px + (v1.x - v3.x)*py)/det;
		float bc3 = 1 - bc1 - bc2;
		
		data.x = bc1;
		data.y = bc2;
		data.z = bc3;
	}
	
	public static int clamp(int input, int min, int max) {
		return input > max ? max : (input < min ? min : input);
	}
}
