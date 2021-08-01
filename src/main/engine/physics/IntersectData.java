package main.engine.physics;

import main.engine.core.Vector3D;

public class IntersectData {
	
	private float massA;
	
	private float massB;
	
	private Vector3D velocityA;
	
	private Vector3D velocityB;
	
	private Vector3D finalVelocityA;
	
	private Vector3D finalVelocityB;
	
	private boolean intersect;
	
	private float distanceToCenter;
	
	private float distanceToBoundary;
	
	/**
	 * Stores intersect data about two boundaries. 
	 * @param intersect true if the boundaries intersect, false otherwise
	 * @param distanceToCenter distance between the centers of the two boundaries
	 * @param distanceToBoundary minimal distance between the boundaries
	 */
	public IntersectData(boolean intersect, float distanceToCenter, float distanceToBoundary) {
		
		this.intersect = intersect;
		
		this.distanceToCenter = distanceToCenter;
		
		this.distanceToBoundary = distanceToBoundary;
		
	}
	
	public boolean getIntersect() {
		
		return intersect;
		
	}
	
	public float getDistanceToCenter() {
		
		return distanceToCenter;
		
	}
	
	public float getDistanceToBoundary() {
		
		return distanceToBoundary;
		
	}
	
	public Vector3D getVelocityA() {
		
		return finalVelocityA;
		
	}
	
	public Vector3D getVelocityB() {
		
		return finalVelocityB;
		
	}

}
