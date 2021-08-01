package main.engine.physics.materials;

public class PhysicsMaterial {
	
	private boolean permeable;
	
	private boolean elastic;
	
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
