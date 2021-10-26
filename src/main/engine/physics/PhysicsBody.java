package main.engine.physics;

import main.engine.core.Vector3D;
import main.engine.physics.boundaries.Boundary;
import main.engine.physics.materials.PhysicsMaterial;

public class PhysicsBody {
	
	private float mass;
	
	private Boundary boundary;
	
	private PhysicsMaterial material;
	
	private Vector3D acceleration;
	
	private Vector3D velocity;
	
	private Vector3D pos;
	
	/**
	 * Constructs a physics body.
	 * @param mass mass of the physics body
	 * @param boundary boundary of the physics body
	 * @param material physics material of the physics body
	 */
	public PhysicsBody(float mass, Boundary boundary, PhysicsMaterial material) {
		
		this(mass, boundary, material, new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), new Vector3D(0, 0, 0));
		
	}
	
	/**
	 * Constructs a physics body.
	 * @param mass mass of the physics body
	 * @param boundary boundary of the physics body
	 * @param material physics material of the physics body
	 * @param pos initial position of the physics body
	 */
	public PhysicsBody(float mass, Boundary boundary, PhysicsMaterial material, Vector3D pos) {
		
		this(mass, boundary, material, new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), pos);
		
	}
	 
	/**
	 * Constructs a physics body.
	 * @param mass mass of the physics body
	 * @param boundary boundary of the physics body
	 * @param material physics material of the physics body
	 * @param acceleration initial acceleration of the physics body
	 * @param velocity initial velocity of the physics body
	 * @param pos initial position of the physics body
	 */
	public PhysicsBody(float mass, Boundary boundary, PhysicsMaterial material, Vector3D acceleration, Vector3D velocity, Vector3D pos) {
		
		this.mass = mass;
		
		this.boundary = boundary;
		
		this.material = material;
		
		this.acceleration = acceleration;
		
		this.velocity = velocity;
		
		this.pos = pos;
		
	}
	
	public void integrate(float delta) {
		
		//Velocity changes linearly wrt time
		velocity = velocity.add(acceleration.getScaled(delta));
		
		pos = pos.add(velocity.getScaled(delta));
		
	}
	
	public float getMass() {
		
		return mass;
		
	}
	
	public void setMass(float mass) {
		
		this.mass = mass;
		
	}
	
	public Vector3D getPos() {
		
		return pos;
		
	}
	
	public void setPos(Vector3D pos) {
		
		this.pos = pos;
		
	}
	
	public Vector3D getVelocity() {
		
		return velocity;
		
	}
	
	public void setVelocity(Vector3D velocity) {
		
		this.velocity = velocity;
		
	}
	
	public Vector3D getAcceleration() {
		
		return acceleration;
		
	}
	
	public void setAcceleration(Vector3D acceleration) {
		
		this.acceleration = acceleration;
		
	}
	
	public Boundary getBoundary() {
		
		return boundary;
		
	}
	
	public PhysicsMaterial getMaterial() {
		
		return material;
		
	}
}
