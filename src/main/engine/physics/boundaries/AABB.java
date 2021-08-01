package main.engine.physics.boundaries;

import main.engine.core.Vector3D;
import main.engine.physics.IntersectData;

public class AABB extends Boundary{
	
	private Vector3D maxExtend;
	
	private float dxyz, dxy, dyz, dxz;
	
	/**
	 * Constructs an Axis-Aligned Bounding Box for a physics body to use in collision detection.
	 * @param pos position of the center of the aabb
	 * @param maxExtend from the center, the distances in the positive x y and z directions
	 */
	public AABB(Vector3D pos, Vector3D maxExtend) {
		
		super(boundaryType.TYPE_AABB, pos);
		
		this.maxExtend = maxExtend;
		
		resize(maxExtend);
		
	}
	
	private void resize(Vector3D maxExtend) {
		
		dxyz = maxExtend.length();
		
		dxy = maxExtend.getXY().length();
		
		dyz = maxExtend.getYZ().length();
		
		dxz = maxExtend.getXZ().length();
		
	}
	
	public IntersectData intersect(AABB aabb) {
		
		float distanceToCenter = getPos().sub(aabb.getPos()).length();
		
		//float distanceToBoundary = getPos().add(getMinExtend()).sub(aabb.getPos().add(aabb.getMaxExtend())).getMax(getPos().add(getMaxExtend()).sub(aabb.getPos().add(aabb.getMinExtend()))).max();
		
		float distanceToBoundary = aabb.getMin().sub(getMax()).getMax(getMin().sub(aabb.getMax())).max();
		
		return new IntersectData(distanceToBoundary < 0, distanceToCenter, distanceToBoundary);
		
	}
	
	public IntersectData intersect(Plane plane) {
		
		float distanceToCenter = 0;
		
		float distanceToBoundary = 0;
		
		boolean intersect = false;
		
		return new IntersectData(intersect, distanceToCenter, distanceToBoundary);
		
	}
	
	public IntersectData intersect(Sphere sphere) {
		
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
		
		return maxExtend.getScaled(-1.0f);
		
	}
	
	public Vector3D getMax() {
		
		return getPos().add(getMaxExtend());
		
	}
	
	public Vector3D getMin() {
		
		return getPos().add(getMinExtend());
		
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
