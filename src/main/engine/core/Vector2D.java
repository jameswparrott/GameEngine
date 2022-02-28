package main.engine.core;

public class Vector2D {

	private float x;
	
	private float y;
	
	public static final Vector2D ZERO = new Vector2D(0, 0);
	
	public Vector2D(float x, float y) {
		
		this.x = x;
		
		this.y = y;
		
	}
	
	public void set(float x, float y) {
		
		this.x = x;
		
		this.y = y;
		
	}
	
	public float length() {
		
		return (float) Math.sqrt(x * x + y * y);
		
	}
	
	public float lengthSq() {
		
		return x * x + y * y;
		
	}
	
	public float dot(Vector2D v) {
		
		return x * v.getX() + y * v.getY();
		
	}
	
	public Vector2D abs() {
		
		x = Math.abs(x);
		
		y = Math.abs(y);
		
		return this;
		
	}
	
	public Vector2D getAbs() {
		
		return new Vector2D(Math.abs(x), Math.abs(y));
		
	}
	
	public float max() {
		
		return Math.max(x, y);
		
	}
	
	public Vector2D lerp(Vector2D v, float lerpFactor) {
		
		return v.sub(this).getScaled(lerpFactor).add(this);
		
	}
	
	public float cross(Vector2D v) {
		
		return x * v.getY() - y * v.getX();
		
	}
	
	public Vector2D normalize() {
		
		float length = length();
		
		if (length != 0) {
			
			x /= length;
			
			y /= length;
			
		}
		
		return this;
		
	}
	
	public Vector2D getNorm() {
		
		float length = length();
		
		if (length != 0) {
			
			return new Vector2D(x/length, y/length);
			
		}
		
		else return new Vector2D(x, y);
		
	}
	
	public Vector2D rotate(float alpha) {
		
		double rad = Math.toRadians(alpha);
		
		float oldx = this.x;
		
		this.x = (float)(x * Math.cos(rad) - y * Math.sin(rad));
		
		this.y = (float)(oldx * Math.sin(rad) + y * Math.cos(rad));
		
		return this;
		
	}
	
	public Vector2D getRotated(float alpha) {
		
		double rad = Math.toRadians(alpha);
		
		return new Vector2D((float)(x * Math.cos(rad) - y * Math.sin(rad)),(float)(x * Math.sin(rad) + y * Math.cos(rad)));
		
	}
	
	public Vector2D add(Vector2D v) {
		
		return new Vector2D(x + v.getX(),y + v.getY());
		
	}
	
	public Vector2D sub(Vector2D v) {
		
		return new Vector2D(x - v.getX(), y - v.getY());
		
	}
	
	public Vector2D mul(Vector2D v) {
		
		return new Vector2D(x * v.getX(), y * v.getY());
		
	}
	
	public Vector2D scale(float c) {
		
		x *= c;
		
		y *= c;
		
		return this;
		
	}
	
	public Vector2D getScaled(float c) {
		
		return new Vector2D(x * c,y * c);
		
	}
	
	public Vector2D compAdd(float c) {
		
		return new Vector2D(x + c, y + c);
		
	}
	
	public String toString() {
		
		return "[" + x + "," + y + "]";
		
	}

	public float getY() {
		
		return y;
	
	}

	public void setY(float y) {
		
		this.y = y;
		
	}

	public float getX() {
		
		return x;
		
	}

	public void setX(float x) {
		
		this.x = x;
		
	}
	
	public boolean equals(Vector2D v) {
		
		return (x == v.getX()) && (y == v.getY());
		
	}
	
}
