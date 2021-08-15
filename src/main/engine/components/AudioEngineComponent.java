package main.engine.components;

import main.engine.audio.AudioEngine;

public class AudioEngineComponent extends GameComponent{

	private AudioEngine audioEngine;
	
	public AudioEngineComponent(AudioEngine audioEngine) {
		
		this.audioEngine = audioEngine;
		
	}
	
	public AudioEngine getAudioEngine() {
		
		return audioEngine;
		
	}
	
	public void addSource(Source source) {
		
	}
	
	public void addListener(Listener listener) {
		
		
		
	}
	
}
