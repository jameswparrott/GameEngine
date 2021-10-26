package main.engine.components;

import main.engine.core.Vector2D;
import main.engine.core.Vector3D;
import main.engine.physics.PhysicsBody;
import main.game.Door;
import main.game.Level;

public class PhysicsBodyComponent extends GameComponent {
	
	private PhysicsBody physicsBody;
	
	/**
	 * Constructs a physics body component. Essentially a physics body from the
	 * physics package which will allow game objects to interact with each other.
	 * @param physicsBody physics body this component will use
	 */
	public PhysicsBodyComponent(PhysicsBody physicsBody) {
		
		this.physicsBody = physicsBody;
		
	}
	
	@Override
	public void input(float delta) {
		
		//TODO: take some input 
		
	}
	
	@Override
	public void update(float delta) {
		
		getTransform().setPos(physicsBody.getPos());
		
		physicsBody.getBoundary().setPos(physicsBody.getPos());
		
	}
	
	public PhysicsBody getPhysicsBody() {
		
		return this.physicsBody;
		
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
