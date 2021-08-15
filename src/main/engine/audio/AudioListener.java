package main.engine.audio;

import static org.lwjgl.openal.AL10.alListener3f;
import static org.lwjgl.openal.AL10.alListenerfv;

import static org.lwjgl.openal.AL10.AL_POSITION;
import static org.lwjgl.openal.AL10.AL_VELOCITY;
import static org.lwjgl.openal.AL10.AL_ORIENTATION;

import main.engine.core.Vector3D;

public class AudioListener {
	
	public AudioListener() {
		
		this(new Vector3D(0, 0, 0), new Vector3D(0, 0, 0));
		
	}
	
	public AudioListener(Vector3D pos, Vector3D velocity) {
		
		alListener3f(AL_POSITION, pos.getX(), pos.getY(), pos.getZ());
		
		alListener3f(AL_VELOCITY, velocity.getX(), velocity.getY(), velocity.getZ());
		
	}
	
	public void setPos(Vector3D pos) {
		
		//We input negatives since we are getting the 
		
		alListener3f(AL_POSITION, -pos.getX(), -pos.getY(), -pos.getZ());
		
	}
	
	public void setPos(float x, float y, float z) {
		
		alListener3f(AL_POSITION, -x, -y, -z);
		
	}
	
	public void setVelocity(Vector3D velocity) {
		
		alListener3f(AL_VELOCITY, velocity.getX(), velocity.getY(), velocity.getZ());
		
	}
	
	public void setVelocity(float x, float y, float z) {
		
		alListener3f(AL_VELOCITY, x, y, z);
		
	}
	
	public void setOrientation(Vector3D forward, Vector3D up) {
		
		float[] data = new float[6];
		
		data[0] = forward.getX();
		
		data[1] = forward.getY();
		
		data[2] = forward.getZ();
		
		data[3] = up.getX();
		
		data[4] = up.getY();
		
		data[5] = up.getZ();
		
		alListenerfv(AL_ORIENTATION, data);
		
	}
	
}
