package main.engine.audio;

import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.AL10.alSourcei;
import static org.lwjgl.openal.AL10.alSourcef;
import static org.lwjgl.openal.AL10.alSource3f;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourcePause;
import static org.lwjgl.openal.AL10.alSourceStop;
import static org.lwjgl.openal.AL10.alGetSourcei;
import static org.lwjgl.openal.AL10.alDeleteSources;

import static org.lwjgl.openal.AL10.AL_LOOPING;
import static org.lwjgl.openal.AL10.AL_SOURCE_RELATIVE;
import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_POSITION;
import static org.lwjgl.openal.AL10.AL_VELOCITY;
import static org.lwjgl.openal.AL10.AL_GAIN;
import static org.lwjgl.openal.AL10.AL_PITCH;
import static org.lwjgl.openal.AL10.AL_SOURCE_STATE;
import static org.lwjgl.openal.AL10.AL_PLAYING;
import static org.lwjgl.openal.AL10.AL_TRUE;

import main.engine.core.Vector3D;

public class AudioSource {

    private final int id;

    public AudioSource() {

        this(false, false);

    }

    public AudioSource(boolean loop, boolean relative) {

        id = alGenSources();

        // If the sound is meant to be played on a constant loop, can be paused

        if (loop) {

            alSourcei(id, AL_LOOPING, AL_TRUE);

        }

        // This will always play relative to the listener, for music etc.

        if (relative) {

            alSourcei(id, AL_SOURCE_RELATIVE, AL_TRUE);

        }

    }

    public void setBuffer(int bufferId) {

        stop();

        alSourcei(id, AL_BUFFER, bufferId);

    }

    public void setPos(Vector3D pos) {

        alSource3f(id, AL_POSITION, -pos.getX(), -pos.getY(), -pos.getZ());

    }

    public void setPos(float x, float y, float z) {

        alSource3f(id, AL_POSITION, -x, -y, -z);

    }

    public void setVelocity(Vector3D velocity) {

        alSource3f(id, AL_VELOCITY, velocity.getX(), velocity.getY(), velocity.getZ());

    }

    public void setVelocity(float x, float y, float z) {

        alSource3f(id, AL_VELOCITY, x, y, z);

    }

    public void setGain(float gain) {

        alSourcef(id, AL_GAIN, gain);

    }

    public void setPitch(float pitch) {

        alSourcef(id, AL_PITCH, pitch);

    }

    public void set(int parameter, float value) {

        alSourcef(id, parameter, value);

    }

    public void play() {

        alSourcePlay(id);

    }

    public void pause() {

        alSourcePause(id);

    }

    public void stop() {

        alSourceStop(id);

    }

    public boolean isPlaying() {

        return alGetSourcei(id, AL_SOURCE_STATE) == AL_PLAYING;

    }

    public int getSourceId() {

        return id;

    }

    public void deleteSource() {

        stop();

        alDeleteSources(id);

    }

}
