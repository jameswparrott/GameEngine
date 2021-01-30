package main.engine.components;

import main.engine.core.Vector3D;
import main.engine.sound.AudioEngine;
import main.engine.sound.AudioSource;

public class Source extends GameComponent {
	
	private AudioSource audioSource;
	
	private Vector3D pos;
	
	private Vector3D lastPos;
	
	private Vector3D velocity = new Vector3D(0, 0, 0);
	
	public Source(String file, Vector3D pos) {
		
		audioSource = new AudioSource();
		
		AudioEngine.loadSound(file, audioSource);
		
		audioSource.setGain(1f);
		
		audioSource.setPitch(1f);
		
		this.pos = pos;
		
		this.lastPos = pos;
		
	}
	
	@Override
	public void update(float delta) {
		
		pos = getTransform().getPos();
		
		if(!pos.equals(lastPos)) {
			
			audioSource.setPos(pos);
			
			velocity = pos.sub(lastPos);
			
			lastPos = pos;
			
		}else {
			
			velocity.scale(0);
			
		}
		
		audioSource.setVelocity(velocity);
		
	}
	
	public AudioSource getSource() {
		
		return audioSource;
		
	}
	
	public void play() {
		
		audioSource.play();
		
	}
	
	public void pause() {
		
		audioSource.pause();
		
	}
	
	public void stop() {
		
		audioSource.stop();
		
	}
	
}
