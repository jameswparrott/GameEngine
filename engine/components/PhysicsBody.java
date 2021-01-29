package main.engine.components;

import java.util.ArrayList;

import main.engine.core.Vector2D;
import main.engine.core.Vector3D;
import main.engine.rendering.Vertex;
import main.game.Door;
import main.game.Level;

public class PhysicsBody extends GameComponent {
	
	private Vector3D[] boundary;
	
	private Vector3D acceleration;
	
	private Vector3D velocity;
	
	private Vector3D pos;
	
	private float mass;
	
	public PhysicsBody(ArrayList<Vertex> vertices) {
		
		boundary = new Vector3D[vertices.size()];
		
		for (int i = 0; i < vertices.size(); i ++) {
			
			boundary[i] = vertices.get(i).getPos();
			
		}
		
	}
	
	public PhysicsBody(float width, float height, float depth) {
		
		boundary = new Vector3D[8];
		
		boundary[0] = new Vector3D(-width/2, -height/2, -depth/2);
		
		boundary[1] = new Vector3D(width/2, -height/2, -depth/2);
		
		boundary[2] = new Vector3D(width/2, -height/2, depth/2);
		
		boundary[3] = new Vector3D(-width/2, -height/2, depth/2);
		
		boundary[4] = new Vector3D(-width/2, height/2, -depth/2);
		
		boundary[5] = new Vector3D(width/2, height/2, -depth/2);
		
		boundary[6] = new Vector3D(width/2, height/2, depth/2);
		
		boundary[7] = new Vector3D(-width/2, height/2, depth/2);
		
	}
	
	/*TODO: WE NEED a collision check that can take in a physics body (Method can be in this class) and 
	another "Mesh" or terrain. Some nice way of detecting a collision with THIS SPECIFIC BODY and terrain
	or even another physics body...*/
	
	public Vector3D getCollision(PhysicsBody body) {
		
		return new Vector3D(0, 0, 0);
		
	}
	
	@Override
	public void update(float delta) {
		
		updateVertices();
		
		acceleration = getTransform().getPos().sub(pos).scale(1f/delta).sub(velocity).scale(1f/(delta * delta));
		
		velocity = getTransform().getPos().sub(pos).scale(1f/delta);
		
		pos = getTransform().getPos();
		
	}
	
	private void updateVertices() {
		
		//Does this actually work?
		
		for (int i = 0; i < boundary.length; i ++) {
			
			boundary[i] = getTransform().getTransformation().transform(boundary[i]);
			
		}
		
	}
	
	public Vector3D[] getBoundary(){
		
		return boundary;
		
	}
	
	public float getMass() {
		
		return mass;
		
	}
	
	public Vector3D getPos() {
		
		return pos;
		
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
