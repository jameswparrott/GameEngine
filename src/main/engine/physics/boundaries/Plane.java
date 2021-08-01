package main.engine.physics.boundaries;

import main.engine.core.Vector3D;
import main.engine.physics.IntersectData;

public class Plane extends Boundary{
	
	private Vector3D normal;
	
	//private boolean dxyz;
	
	//private boolean dxy, dyz, dxz;
	
	//private boolean dx, dy, dz;

	public Plane(Vector3D pos, Vector3D normal) {
		
		super(boundaryType.TYPE_PLANE, pos);
		
		this.normal = normal.getNorm();
		
	}
	
	public IntersectData intersect(Plane plane) {
		
//		float distanceToCenter = getPos().sub(plane.getPos()).length();
//		
//		boolean intersect = !getNormal().equals(plane.getNormal());
//		
//		float distanceToBoundary = intersect ? 0 : distanceToCenter;
//		
//		return new IntersectData(intersect, distanceToCenter, distanceToBoundary);
		
		return null;
		
	}
	
	public IntersectData intersect(Sphere sphere) {
		
//		float distanceToCenter = Math.abs(sphere.getPos().sub(getPos()).dot(normal));
//		
//		float distanceToBoundary = distanceToCenter - sphere.getRadius();
//		
//		boolean intersect = distanceToCenter < sphere.getRadius();
//		
//		return new IntersectData(intersect, distanceToCenter, distanceToBoundary);
		
		return null;
		
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
	
	public Vector3D getNormal() {
		
		return normal;
		
	}
	
	public void setNormal(Vector3D normal) {
		
		this.normal = normal;
		
	}
	
}
