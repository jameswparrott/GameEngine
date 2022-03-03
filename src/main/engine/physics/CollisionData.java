package main.engine.physics;

import main.engine.core.Vector3D;

public class CollisionData {
	
	//Final velocities
	Vector3D[] velocities = new Vector3D[2];
	
	//Final angular momenta
	Vector3D[] angularVelocities = new Vector3D[2];
	
	public CollisionData(PhysicsBody a, PhysicsBody b, float delta) {
		
		rollBack(a, b, delta);
		
		velocities = calcCollision(a, b);
		
	}
	
	private void rollBack(PhysicsBody a, PhysicsBody b, float delta) {
		
		IntersectData intersect = a.getBoundary().intersect(b.getBoundary());
		
		Vector3D velocity_a = a.getVelocity();
		
		Vector3D velocity_b = b.getVelocity();
		
		System.out.println("Velocity A: " + velocity_a);
		
		System.out.println("Velocity B: " + velocity_b);
		
		float velocities = velocity_a.length() + velocity_b.length();
		
		float distanceToBoundary = intersect.getDistanceToBoundary();
		
		System.out.println("Sum of velocities: " + velocities);
		
		System.out.println("Distance to boundary: " + distanceToBoundary);
		
		float dt = distanceToBoundary / velocities;
		
		System.out.println("Time of delta:" + delta);
		
		System.out.println("Time of collision: " + dt);
		
		a.setPos(a.getPos().add(a.getVelocity().getScaled(dt)));
		
		b.setPos(b.getPos().add(b.getVelocity().getScaled(dt)));
		
		//v(t) = (a(t + dt) - a(t))/dt
		
		//p(t) = (v(t + dt) - v(t))/dt
		
		//TODO: find time since last delta passed to the point where the actual collision happened
		
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
	
	public Vector3D[] getVelocities() {
		
		return this.velocities;
		
	}
	
	public Vector3D[] getAngularVelocities() {
		
		return this.angularVelocities;
		
	}

}
