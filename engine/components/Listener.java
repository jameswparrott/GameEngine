package main.engine.components;

import main.engine.core.Vector3D;
import main.engine.sound.AudioListener;

public class Listener extends GameComponent {
	
	private AudioListener audioListener;
	
	private Vector3D lastPos;
	
	private Vector3D pos;
	
	private Vector3D velocity = new Vector3D(0, 0, 0);
	
	public Listener(Vector3D pos) {
		
		audioListener = new AudioListener(pos, velocity);
		
		this.pos = pos;
		
		this.lastPos = pos;
		
	}
	
	@Override
	public void update(float delta) {
		
		pos = getTransform().getPos();
		
		if(!pos.equals(lastPos)) {
			
			audioListener.setPos(pos);
			
			velocity = pos.sub(lastPos);
			
			lastPos = pos;
			
		}else {
			
			velocity.scale(0);
			
		}
		
		audioListener.setOrientation(getTransform().getRot().getForward(), getTransform().getRot().getUp());
		
		audioListener.setVelocity(velocity);
		
	}
	
}
