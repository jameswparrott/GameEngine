package main.engine.audio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.openal.AL10.alDistanceModel;

import static org.lwjgl.openal.AL11.AL_EXPONENT_DISTANCE_CLAMPED;

public class AudioEngine {

    private static List<AudioBuffer> buffers;

    private static List<AudioSource> sources;

    private static Map<String, AudioSource> soundSourceMap;

    public AudioEngine() {

        buffers = new ArrayList<AudioBuffer>();

        sources = new ArrayList<AudioSource>();

        soundSourceMap = new HashMap<String, AudioSource>();

        alDistanceModel(AL_EXPONENT_DISTANCE_CLAMPED);

    }

    public static void loadSound(String file, AudioSource source) {

        AudioBuffer buffer = new AudioBuffer(file);

        source.setBuffer(buffer.getBufferId());

        buffers.add(buffer);

        sources.add(source);

        soundSourceMap.put(file, source);

    }

    public static AudioSource getSource(String file) {

        return soundSourceMap.get(file);

    }

    public void cleanUp() {

        soundSourceMap.clear();

        for (AudioSource source : sources) {

            source.deleteSource();

        }

        for (AudioBuffer buffer : buffers) {

            buffer.deleteBuffer();

        }

    }

}
