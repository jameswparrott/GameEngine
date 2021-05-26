package main.engine.components;

import main.engine.core.Vector2D;
import main.engine.core.Vector3D;
import main.engine.physics.Boundary;
import main.game.Door;
import main.game.Level;

public class PhysicsBody extends GameComponent {
	
	private Boundary boundary;
	
	private Vector3D acceleration;
	
	private Vector3D velocity;
	
	private float mass;
	
	public PhysicsBody(float mass, Boundary boundary) {
		
		this(mass, boundary, new Vector3D(0, 0, 0), new Vector3D(0, 0, 0));
		
	}
	
	public PhysicsBody(float mass, Boundary boundary, Vector3D acceleration, Vector3D velocity) {
		
		this.mass = mass;
		
		this.boundary = boundary;
		
		this.acceleration = acceleration;
		
		this.velocity = velocity;
		
	}
	
	public Vector3D getCollision(PhysicsBody body) {
		
		return new Vector3D(0, 0, 0);
		
	}
	
	@Override
	public void update(float delta) {
		
		//TODO: make everything in terms of SI units
		
		float time = delta * 60;
		
		velocity = velocity.add(acceleration.getScaled(time * 0.5f));
		
		getTransform().setPos(getTransform().getPos().add(velocity.getScaled(time)));
		
	}
	
	public float getMass() {
		
		return mass;
		
	}
	
	public Vector3D getVelocity() {
		
		return velocity;
		
	}
	
	public Vector3D getAcceleration() {
		
		return acceleration;
		
	}
	
	public Vector3D bodyCheck() {
		
		return new Vector3D(0, 0, 0);
		
	}
	
	public Vector3D terrainCheck() {
		
		return new Vector3D(0, 0, 0);
		
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
