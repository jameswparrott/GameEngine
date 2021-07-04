package main.engine.physics.boundaries;

import main.engine.core.Vector3D;
import main.engine.physics.IntersectData;

public class BoundingSphere extends Boundary{
	
	float radius;
	
	float radiusSq;
	
	/**
	 * Constructs a bounding sphere for a physics body to use in collision detection.
	 * @param pos the position of the center of the bounding sphere
	 * @param radius the bounding sphere's radius
	 */
	public BoundingSphere(Vector3D pos, float radius) {
		
		super(pos, Type.Sphere);
		
		this.radius = radius;
		
		this.radiusSq = radius * radius;
		
	}
	
	public float getRadius() {
		
		return this.radius;
		
	}
	
	public void setRadius(float radius) {
		
		this.radius = radius;
		
		this.radiusSq = radius * radius;
		
	}
	
	public float getRadiusSq() {
		
		return this.radiusSq;
		
	}
	
	public IntersectData intersect(BoundingSphere sphere) {
		
		float distanceToCenter = getPos().sub(sphere.getPos()).length();
		
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
