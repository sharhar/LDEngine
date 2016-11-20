package engine.utils.math;

public class Vector2f {
	public float x, y;
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f() {
		this.x = 0;
		this.y = 0;
	}
	
	public void add(Vector2f other) {
		x += other.x;
		y += other.y;
	}
	
	public boolean equals(Vector2f other) {
		return this.x == other.x && this.y == other.y;
	}
	
	public String toString() {
		return "[ " + x + " , " + y + " ]";
	}
	
	public void mult(float alpha) {
		this.x *= alpha;
		this.y *= alpha;
	}
}
