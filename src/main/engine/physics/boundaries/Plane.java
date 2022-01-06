package main.engine.physics.boundaries;

import main.engine.core.Vector3D;
import main.engine.physics.CollisionData;
import main.engine.physics.IntersectData;

public class Plane extends Boundary{
	
	private Vector3D normal;
	
	/**
	 * Constructs a plane for a physics body to use in collision detection.
	 * @param pos position of the plane
	 * @param normal normal vector of the plane
	 */
	public Plane(Vector3D pos, Vector3D normal) {
		
		super(boundaryType.TYPE_PLANE, pos);
		
		this.normal = normal.getNorm();
		
	}
	
	public IntersectData intersect(Boundary boundary) {
		
		switch(boundary.getType()) {
		
		case TYPE_PLANE:
			
			return intersect((Plane) boundary);
			
		case TYPE_SPHERE:
			
			return intersect((Sphere) boundary);
				
		case TYPE_CMB:
			
			return intersect((CMB) boundary);
		
		case TYPE_AABB:
			
			return intersect((AABB) boundary);
			
		default:
			
			System.err.println("Sphere attempted to intersect with an undefined boundary");
			
			return new IntersectData(false, 0, 0);
		
		}
		
	}
	
	public IntersectData intersect(Plane plane) {
		
		float distanceToCenter = getPos().sub(plane.getPos()).length();
		
		boolean intersect = !getNormal().equals(plane.getNormal());
		
		float distanceToBoundary = intersect ? 0 : distanceToCenter;
		
		return new IntersectData(intersect, distanceToCenter, distanceToBoundary);
		
	}
	
	public IntersectData intersect(Sphere sphere) {
		
		float distanceToCenter = Math.abs(sphere.getPos().sub(getPos()).dot(normal));
		
		float distanceToBoundary = distanceToCenter - sphere.getRadius();
		
		boolean intersect = distanceToBoundary < 0;
		
		return new IntersectData(intersect, distanceToCenter, distanceToBoundary);
		
	}
	
	public IntersectData intersect(AABB aabb) {
		
//		float distanceToCenter = Math.abs(aabb.getPos().sub(getPos()).dot(normal));
//		
//		float distanceToBoundary = distanceToCenter;
//		
//		if(normal.getX() != 0 && normal.getY() != 0 && normal.getZ() != 0) {
//			
//			distanceToBoundary -= aabb.getDxyz();
//			
//		} else if(normal.getX() == 0 && normal.getY() == 0) {
//			
//			distanceToBoundary -= aabb.getMaxExtend().getZ();
//			
//		} else if(normal.getX() == 0 && normal.getZ() == 0) {
//			
//			distanceToBoundary -= aabb.getMaxExtend().getY();
//			
//		} else if(normal.getY() == 0 && normal.getZ() == 0) {
//			
//			distanceToBoundary -= aabb.getMaxExtend().getX();
//			
//		} else if(normal.getX() == 0) {
//			
//			distanceToBoundary -= aabb.getDyz();
//			
//		} else if(normal.getY() == 0) {
//			
//			distanceToBoundary -= aabb.getDxz();
//			
//		} else if(normal.getZ() == 0) {
//			
//			distanceToBoundary -= aabb.getDxy();
//			
//		}
//		
//		return new IntersectData(distanceToBoundary < 0, distanceToCenter, distanceToBoundary);
		
		return null;
		
	}
	
	public CollisionData collide(Boundary boundary) {
		
		return new CollisionData(new Vector3D(0, 0, 0), new Vector3D(0, 0, 0));
		
	}
	
	public Vector3D getNormal() {
		
		return normal;
		
	}
	
	public void setNormal(Vector3D normal) {
		
		this.normal = normal;
		
	}
	
}
