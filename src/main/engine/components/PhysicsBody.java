package main.engine.components;

import main.engine.core.Vector2D;
import main.engine.core.Vector3D;
import main.engine.physics.boundaries.Boundary;
import main.engine.physics.materials.PhysicsMaterial;
import main.game.Door;
import main.game.Level;

public class PhysicsBody extends GameComponent {
	
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
	
	@Override
	public void input(float delta) {
		
		//TODO: take some input 
		
	}
	
	@Override
	public void update(float delta) {
		
		getTransform().setPos(pos);
		
		boundary.setPos(pos);
		
	}
	
	public void integrate(float delta) {
		
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
	
	public Vector3D levelCheck(Level level, Vector3D oldPos, Vector3D newPos, float objectWidth, float objectLength) {
		
		//TODO: Expand, make a method for actual terrain and for bitmap generated levels etc.
		
		Vector2D collisionVector = new Vector2D(1, 1);

		Vector2D blockSize = new Vector2D(1, 1);
		
		Vector2D objectSize = new Vector2D(objectWidth, objectLength);
		
		Vector2D oldPos0 = oldPos.getXZ();
		
		Vector2D newPos0 = newPos.getXZ();
		
		for(int i = 0; i < level.getLevel().getWidth(); i ++) {
			
			for(int j = 0; j < level.getLevel().getHeight(); j++) {
				
				if((level.getLevel().getPixel(i, j) & 0xFFFFFF) == 0) {
					
					collisionVector = collisionVector.mul(rectangleCollide(oldPos0, newPos0, objectSize, new Vector2D(i, j), blockSize));
					
				}
				
			}
			
		}
		
		for (Door d : level.getDoors()) {
			
			Vector2D doorPos = d.getTransform().getPos().getXZ();
			
			if (d.getRotated()) {
				
				doorPos = doorPos.sub(new Vector2D(0, 0.5f));
				
			} else {
				
				doorPos = doorPos.sub(new Vector2D(0.5f, 0));
				
			}
			
			collisionVector = collisionVector.mul(rectangleCollide(oldPos0, newPos0, objectSize, doorPos, d.getOrientation()));
			
		}
			
		
		return new Vector3D(collisionVector.getX(), 0, collisionVector.getY());
		
	}
	
	private Vector2D rectangleCollide(Vector2D oldPos, Vector2D newPos, Vector2D size1, Vector2D pos2, Vector2D size2) {
		
		Vector2D result = new Vector2D(0, 0);
		
		if(		newPos.getX() + size1.getX() < pos2.getX() || 
				
				newPos.getX() - size1.getX() > pos2.getX() + size2.getX() ||
				
				oldPos.getY() + size1.getY() < pos2.getY() || 
				
				oldPos.getY() - size1.getY() > pos2.getY() + size2.getY()) {
			
			result.setX(1);
			
		}
		
		if(		oldPos.getX() + size1.getX() < pos2.getX() || 
				
				oldPos.getX() - size1.getX() > pos2.getX() + size2.getX() ||
				
				newPos.getY() + size1.getY() < pos2.getY() || 
				
				newPos.getY() - size1.getY() > pos2.getY() + size2.getY()) {
			
			result.setY(1);
			
		}
		
		return result;
		
	}
	
}
