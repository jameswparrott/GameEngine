package main.engine.sound;

import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO16;
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alGenBuffers;

import static org.lwjgl.stb.STBVorbis.stb_vorbis_open_memory;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_get_info;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_stream_length_in_samples;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_get_samples_short_interleaved;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_close;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class AudioBuffer {

	private final int id;

	private ByteBuffer vorbis;

	private ShortBuffer pulseCodedModulation;

	//private static List<AudioBuffer> buffers = new ArrayList<AudioBuffer>();

	public AudioBuffer(String file) {

		id = alGenBuffers();

		try (STBVorbisInfo info = STBVorbisInfo.malloc()) {

			ShortBuffer pulseCodedModulation = null;

			try {

				pulseCodedModulation = readVorbis(file, 32 * 1024, info);

			} catch (IOException e) {

				e.printStackTrace();

			}

			if (pulseCodedModulation == null) {

				System.err.println(file + " could not be read!");

				return;

			}

			alBufferData(id, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pulseCodedModulation, info.sample_rate());

			//buffers.add(this);

		}

	}

	public final int getBufferId() {

		return id;

	}

	public void deleteBuffer() {

		alDeleteBuffers(id);

	}

	private ShortBuffer readVorbis(String file, int capacity, STBVorbisInfo info) throws IOException {

		try (MemoryStack stack = MemoryStack.stackPush()) {

			vorbis = fileToByteBuffer("./res/audio/" + file, capacity);
			
			IntBuffer error = stack.mallocInt(1);
			
			long decoder = stb_vorbis_open_memory(vorbis, error, null);
			
			if (decoder == 0) throw new RuntimeException("Failed to open " + file + " Vorbis: " + error.get(0));
			
			stb_vorbis_get_info(decoder, info);
			
			int channels = info.channels();
			
			pulseCodedModulation = MemoryUtil.memAllocShort(stb_vorbis_stream_length_in_samples(decoder));
			
			pulseCodedModulation.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, pulseCodedModulation) * channels);
			
			stb_vorbis_close(decoder);
			
			return pulseCodedModulation;

		}

	}

	private ByteBuffer fileToByteBuffer(String file, int capacity) throws IOException {
		
		ByteBuffer buffer = null;
		
		Path path = Paths.get(file);
		
		if(Files.isReadable(path)) {
			
			try(SeekableByteChannel sbc = Files.newByteChannel(path)){
				
				buffer = BufferUtils.createByteBuffer((int) sbc.size() + 1);
				
				while (sbc.read(buffer) != -1) ;
				
			}
			
		} else {
			
			System.err.println("Unable to read from path " + file);
			
			System.exit(1);
			
		}
		
		buffer.flip();
		
		return buffer;
		
	}

}
