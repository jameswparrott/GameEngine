package main.engine.physics.boundaries;

import main.engine.core.Vector3D;
import main.engine.physics.CollisionData;
import main.engine.physics.IntersectData;

public class Sphere extends Boundary{
	
	private float radius;
	
	/**
	 * Constructs a sphere for a physics body to use in collision detection.
	 * @param pos position of the center of the sphere
	 * @param radius radius of the sphere
	 */
	public Sphere(Vector3D pos, float radius) {
		
		super(boundaryType.TYPE_SPHERE, pos);
		
		this.radius = radius;
		
	}
	
	public float getRadius() {
		
		return this.radius;
		
	}
	
	public void setRadius(float radius) {
		
		this.radius = radius;
		
	}
	
	/**
	 * Returns intersect data containing information about the intersection. If the intersection occurs, the distance
	 * between the two centers, and the minimal distance between the boundaries.
	 * @param sphere sphere to test intersection against
	 * @return intersect data
	 */
	public IntersectData intersect(Sphere sphere) {
		
		float distanceToCenter = getPos().sub(sphere.getPos()).length();
		
		float distanceToBoundary = distanceToCenter - (getRadius() + sphere.getRadius());
				
		return new IntersectData(distanceToBoundary < 0, distanceToCenter, distanceToBoundary);
		
	}
	
	public CollisionData collide(Sphere sphere, boolean elasticA, boolean elasticB, float massA, float massB, Vector3D velocityA, Vector3D velocityB) {
		
		Vector3D finalVelocityA = velocityA;
		
		Vector3D finalVelocityB = velocityB;
		
		Vector3D posA = getPos();
		
		Vector3D posB = sphere.getPos();
		
		if( !elasticA || !elasticB) {
			
			//Inelastic collision
			
		} else {
			
			//Elastic collision
			
		}
		
		return new CollisionData(finalVelocityA, finalVelocityB);
		
	}
	
	/**
	 * Returns intersect data containing information about the intersection. If the intersection occurs, the distance
	 * between the two centers, and the minimal distance between the boundaries.
	 * @param plane plane to test intersection against
	 * @return intersect data
	 */
	public IntersectData intersect(Plane plane) {
		
		return new IntersectData(false, 0, 0);
		
	}
	
	/**
	 * Returns intersect data containing information about the intersection. If the intersection occurs, the distance
	 * between the two centers, and the minimal distance between the boundaries.
	 * @param aabb aabb to test intersection against
	 * @return intersect data
	 */
	public IntersectData intersect(AABB aabb) {
		
		return new IntersectData(false, 0, 0);
		
	}
	
	/**
	 * Returns intersect data containing information about the intersection. If the intersection occurs, the distance
	 * between the two centers, and the minimal distance between the boundaries.
	 * @param cmb cmb to test intersection against
	 * @return intersect data
	 */
	public IntersectData intersect(CMB cmb) {
		
		return new IntersectData(false, 0, 0);
		
	}
	
}
