package main.engine.physics.boundaries;

import main.engine.core.Transform;
import main.engine.core.Vector3D;
import main.engine.physics.IntersectData;

public class AABB{
	
	private Vector3D maxExtend;
	
	private Vector3D minExtend;
	
	private float dxyz, dxy, dyz, dxz;
	
	/**
	 * Axis-Aligned Bounding Box for physics bodies to detect collisions with other physics
	 * bodies.
	 * @param pos
	 * @param maxExtend
	 */
	public AABB(Vector3D pos, Vector3D maxExtend) {
		
		this.maxExtend = maxExtend;
		
		resize(maxExtend);
		
	}
	
	private void resize(Vector3D maxExtend) {
		
		minExtend = maxExtend.getScaled(-1.0f);
		
		dxyz = maxExtend.length();
		
		dxy = maxExtend.getXY().length();
		
		dyz = maxExtend.getYZ().length();
		
		dxz = maxExtend.getXZ().length();
		
	}
	
	public IntersectData intersect(AABB aabb) {
		
		//float distanceToCenter = getPos().sub(aabb.getPos()).length();
		
		//float distanceToBoundary = getPos().add(minExtend).sub(aabb.getPos().add(aabb.getMaxExtend())).getMax(getPos().add(maxExtend).sub(aabb.getPos().add(aabb.getMinExtend()))).max();
		
		//return new IntersectData(distanceToBoundary < 0, distanceToCenter, distanceToBoundary);
		
		return null;
		
	}
	
	public IntersectData intersect(BoundaryPlane plane) {
		
		float distanceToCenter = 0;
		
		float distanceToBoundary = 0;
		
		boolean intersect = false;
		
		return new IntersectData(intersect, distanceToCenter, distanceToBoundary);
		
	}
	
	public IntersectData intersect(BoundingSphere sphere) {
		
		return new IntersectData(false, 0, 0);
		
	}
	
	public IntersectData intersect(CMB cmb) {
		
		return new IntersectData(false, 0, 0);
		
	}
	
	public Vector3D getMaxExtend() {
		
		return maxExtend;
		
	}
	
	public void setMaxExtend(Vector3D maxExtend) {
		
		this.maxExtend = maxExtend;
		
		resize(maxExtend);
		
	}
	
	public Vector3D getMinExtend() {
		
		return minExtend;
		
	}

	public float getDxyz() {
		
		return dxyz;
		
	}

	public float getDxy() {
		
		return dxy;
		
	}

	public float getDyz() {
		
		return dyz;
		
	}

	public float getDxz() {
		
		return dxz;
		
	}

}
