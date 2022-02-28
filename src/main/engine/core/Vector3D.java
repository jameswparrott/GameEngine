package main.engine.core;

public class Vector3D {

	private float x;
	
	private float y;
	
	private float z;
	
	public static final Vector3D ZERO = new Vector3D(0, 0, 0);
	
	public Vector3D(float x, float y, float z) {
		
		this.x = x;
		
		this.y = y;
		
		this.z = z;
		
	}
	
	public void set(float x, float y, float z) {
		
		this.x = x;
		
		this.y = y;
		
		this.z = z;
		
	}
	
	public float length() {
		
		return (float) Math.sqrt(x * x + y * y + z * z);
		
	}
	
	public float lengthSq() {
		
		return x * x + y * y + z * z;
		
	}
	
	public float dot(Vector3D v) {
		
		return x * v.getX() + y * v.getY() + z * v.getZ();
		
	}
	
	public Vector3D abs() {
		
		x = Math.abs(x);
		
		y = Math.abs(y);
		
		z = Math.abs(z);
		
		return this;
		
	}
	
	public Vector3D getAbs() {
		
		return new Vector3D(Math.abs(x), Math.abs(y), Math.abs(z));
		
	}
	
	public float max() {
		
		return Math.max(x, Math.max(y, z));
		
	}
	
	public Vector3D getMax(Vector3D v) {
		
		return new Vector3D(x > v.getX() ? x : v.getX(), y > v.getY() ? y : v.getY(), z > v.getZ() ? z : v.getZ());
		
	}
	
	public float min() {
		
		return Math.min(x, Math.min(y, z));
		
	}
	
	public Vector3D getMin(Vector3D v) {
		
		return new Vector3D(x < v.getX() ? x : v.getX(), y < v.getY() ? y : v.getY(), z < v.getZ() ? z : v.getZ());
		
	}
	
	public Vector3D lerp(Vector3D v, float lerpFactor) {
		
		//x - (y - x) * lerp
		
		return this.add((v.sub(this)).getScaled(lerpFactor));
		
	}
	
	public Vector3D cross(Vector3D v) {
		
		float x1 = y * v.getZ() - z * v.getY();
		
		float y1 = z * v.getX() - x * v.getZ();
		
		float z1 = x * v.getY() - y * v.getX();
		
		return new Vector3D(x1, y1, z1);
		
	}
	
	public Vector3D normalize() {
		
		float length = length();
		
		if(length != 0) {
			
			x /= length;
			
			y /= length;
			
			z /= length;
		
		}
		
		return this;
		
	}
	
	public Vector3D getNorm() {
		
		float length = length();
		
		if(length != 0) {
			
			return new Vector3D(x/length, y/length, z/length);
		
		}
		
		else return new Vector3D(x, y, z);
		
	}
	
	public Vector3D rotate(Vector3D axis, float angle) {
		
		return this.rotate(new Quaternion(axis, angle));
		
	}
	
	public Vector3D rotate(Quaternion rotation) {
		
		Quaternion conjugate = rotation.conjugate();
		
		Quaternion q = rotation.mult(this).mult(conjugate);
		
		this.x = q.getX();
		
		this.y = q.getY();
		
		this.z = q.getZ();
		
		return this;
		
	}
	
	public Vector3D getRotated(Vector3D axis, float angle) {
		
		return this.getRotated(new Quaternion(axis, angle));
		
	}
	
	public Vector3D getRotated(Quaternion rotation) {
		
		Quaternion conjugate = rotation.conjugate();
		
		Quaternion q = rotation.mult(this).mult(conjugate);
		
		return new Vector3D(q.getX(), q.getY(), q.getZ());
		
	}
	
	public Vector3D add(Vector3D v) {
		
		return new Vector3D(x + v.getX(),y + v.getY(),z + v.getZ());
		
	}
	
	public Vector3D sub(Vector3D v) {
		
		return new Vector3D(x - v.getX(),y - v.getY(),z - v.getZ());
		
	}
	
	public Vector3D mul(Vector3D v) {
		
		return new Vector3D(x * v.getX(), y * v.getY(), z * v.getZ());
		
	}
	
	public Vector3D scale(float c) {
		
		x *= c;
		
		y *= c;
		
		z *= c;
		
		return this;
		
	}
	
	public Vector3D getScaled(float c) {
		
		return new Vector3D(x * c, y * c, z * c);
		
	}
	
	public Vector3D compAdd(float c) {
		
		return new Vector3D(x + c,y + c,z + c);
		
	}
	
	public String toString() {
		
		return "[" + x + "," + y + "," + z + "]";
		
	}

	public float getX() {
		
		return x;
	
	}

	public void setX(float x) {
		
		this.x = x;
		
	}

	public float getY() {
		
		return y;
		
	}

	public void setY(float y) {
		
		this.y = y;
		
	}

	public float getZ() {
		
		return z;
		
	}

	public void setZ(float z) {
		
		this.z = z;
		
	}
	
	public Vector2D getXY() {
		
		return new Vector2D(x, y);
		
	}
	
	public Vector2D getXZ() {
		
		return new Vector2D(x, z);
		
	}
	
	public Vector2D getYX() {
		
		return new Vector2D(y, x);
		
	}
	
	public Vector2D getYZ() {
		
		return new Vector2D(y, z);
		
	}
	
	public Vector2D getZX() {
		
		return new Vector2D(z, x);
		
	}
	
	public Vector2D getZY() {
		
		return new Vector2D(z, y);
		
	}
	
	public void set(Vector3D v) {
		
		this.x = v.getX();
		
		this.y = v.getY();
		
		this.z = v.getZ();
		
	}
	
	public boolean equals(Vector3D v) {
		
		return (x == v.getX()) && (y == v.getY()) && (z == v.getZ());
		
	}
	
}