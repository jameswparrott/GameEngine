package main.engine.rendering.meshLoading;

import main.engine.core.Vector3D;

public class Sphere {
	
	private Vector3D center;
	
	private float radiusSq;
	
	public Sphere(Vector3D center, float radiusSq) {
		
		this.center = center;
		
		this.radiusSq = radiusSq;
		
	}
	
	public boolean inSphere(Vector3D test) {
		
		return test.sub(center).lengthSq() < radiusSq;
		
	}

	public Vector3D getCenter() {
		return center;
	}

	public void setCenter(Vector3D center) {
		this.center = center;
	}

	public void setRadiusSq(float radiusSq) {
		this.radiusSq = radiusSq;
	}

	public float getRadiusSq() {
		return radiusSq;
	}

}
