package main.engine.physics;

import main.engine.core.Vector3D;

public class CollisionData {
	
	//Final velocities
	private Vector3D v_a;
	
	private Vector3D v_b;
	
	//Final angular momenta
	private Vector3D l_a;
	
	private Vector3D l_b;
	
	public CollisionData(PhysicsBody a, PhysicsBody b) {
		
		Vector3D[] velocities = new Vector3D[2];
		
		velocities = calcCollision(a, b);
		
		v_a = velocities[0];
		
		v_b = velocities[1];
		
	}
	
	private static Vector3D[] calcCollision(PhysicsBody a, PhysicsBody b) {
		
		Vector3D[] velocities = new Vector3D[2];
		
		Vector3D dx_i = a.getPos().sub(b.getPos());
		
		Vector3D dx_j = b.getPos().sub(a.getPos());
		
		Vector3D v_i = a.getVelocity();
		
		Vector3D v_j = b.getVelocity();
		
		Vector3D dv_i = v_i.sub(v_j);
		
		Vector3D dv_j = v_j.sub(v_i);
		
		float c_r = 0.5f * (a.getMaterial().getRestitution() + b.getMaterial().getRestitution());
		
		float m_i = (-2.0f * b.getMass()) / (a.getMass() + b.getMass());
		
		float m_j = (-2.0f * a.getMass()) / (a.getMass() + b.getMass());
		
		float dot_i = dv_i.dot(dx_i);
		
		float dot_j = dv_j.dot(dx_j);
		
		float len_sq = dx_i.lengthSq();
		
		float s_i = (m_i * dot_i * c_r) / len_sq;
		
		float s_j = (m_j * dot_j * c_r) / len_sq;
		
		velocities[0] = v_i.add(dx_i.getScaled(s_i));
		
		velocities[1] = v_j.add(dx_j.getScaled(s_j));
		
		return velocities;
		
	}

	public Vector3D getV_a() {
		
		return v_a;
		
	}

	public Vector3D getV_b() {
		
		return v_b;
		
	}

	public Vector3D getL_a() {
		
		return l_a;
		
	}

	public Vector3D getL_b() {
		
		return l_b;
		
	}

}
