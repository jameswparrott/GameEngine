package main.engine.physics.materials;

public class PhysicsMaterial {
	
	private boolean permeable;
	
	//TODO: update elastic to be the coefficient of restitution of the material.
	private boolean elastic;
	
	/**
	 * The type of material a physics body is made of.
	 * @param permeable whether the physics body is permeable or not
	 * @param elastic the type of collision this physics body will experience
	 */
	public PhysicsMaterial(boolean permeable, boolean elastic) {
		
		this.permeable = permeable;
		
		this.elastic = elastic;
		
	}
	
	public boolean getPermeable() {
		
		return permeable;
		
	}
	
	public boolean getElastic() {
		
		return elastic;
		
	}
	
}
