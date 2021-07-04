package main.engine.physics;

import main.engine.core.Vector3D;

public class IntersectData {
	
	private Vector3D initialVelocityA;
	
	private Vector3D initialVelocityB;
	
	private Vector3D finalVelocityA;
	
	private Vector3D finalVelocityB;
	
	private boolean intersect;
	
	private float distanceToCenter;
	
	private float distanceToBoundary;
	
	/**
	 * Stores data about two objects, whether they intersect or not, their distance from one another, and the final
	 * velocities after collision.
	 * @param intersect
	 * @param distanceToCenter
	 * @param distanceToBoundary
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
