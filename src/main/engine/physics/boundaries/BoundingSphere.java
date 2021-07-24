package main.engine.physics.boundaries;

import main.engine.core.Vector3D;
import main.engine.physics.IntersectData;

public class BoundingSphere{
	
	private Vector3D pos;
	
	private float radius;
	
	/**
	 * Constructs a bounding sphere for a physics body to use in collision detection.
	 * @param pos the position of the center of the bounding sphere
	 * @param radius the bounding sphere radius
	 */
	public BoundingSphere(Vector3D pos, float radius) {
		
		this.pos = pos;
		
		this.radius = radius;
		
	}
	
	public Vector3D getPos() {
		
		return this.pos;
		
	}
	
	public void setPos(Vector3D pos) {
		
		this.pos = pos;
		
	}
	
	public float getRadius() {
		
		return this.radius;
		
	}
	
	public void setRadius(float radius) {
		
		this.radius = radius;
		
	}
	
	/**
	 * Returns intersect data. Intersect data contains a boolean, does intersect
	 * @param sphere sphere to test intersection with
	 * @return intersect data
	 */
	public IntersectData intersect(BoundingSphere sphere) {
		
		float distanceToCenter = pos.sub(sphere.getPos()).length();
		
		float distanceToBoundary = distanceToCenter - (getRadius() + sphere.getRadius());
				
		return new IntersectData(distanceToBoundary < 0, distanceToCenter, distanceToBoundary);
		
	}
	
	public IntersectData intersect(BoundaryPlane plane) {
		
		return new IntersectData(false, 0, 0);
		
	}
	
	public IntersectData intersect(AABB aabb) {
		
		return new IntersectData(false, 0, 0);
		
	}
	
	public IntersectData intersect(CMB cmb) {
		
		return new IntersectData(false, 0, 0);
		
	}
	
}
