package main.engine.core;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwFocusWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.openal.AL10.AL_NO_ERROR;
import static org.lwjgl.openal.AL10.alGetError;
import static org.lwjgl.openal.ALC10.ALC_DEFAULT_DEVICE_SPECIFIER;
import static org.lwjgl.openal.ALC10.alcCloseDevice;
import static org.lwjgl.openal.ALC10.alcCreateContext;
import static org.lwjgl.openal.ALC10.alcDestroyContext;
import static org.lwjgl.openal.ALC10.alcGetContextsDevice;
import static org.lwjgl.openal.ALC10.alcGetCurrentContext;
import static org.lwjgl.openal.ALC10.alcGetString;
import static org.lwjgl.openal.ALC10.alcMakeContextCurrent;
import static org.lwjgl.openal.ALC10.alcOpenDevice;
import static org.lwjgl.openal.ALC11.ALC_ALL_DEVICES_SPECIFIER;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Objects;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALUtil;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import main.engine.audio.AudioEngine;
import main.engine.physics.PhysicsEngine;
import main.engine.rendering.RenderingEngine;
import main.game.TestGame;

public class CoreEngine {
	
	private boolean isRunning;
	
	private static int width;
	
	private static int height;
	
	private static double framerate;
	
	private static long window;
	
	private static String title;
	
	private RenderingEngine renderingEngine;
	
	private PhysicsEngine physicsEngine;
	
	private AudioEngine audioEngine;
	
	private Game game;
	
	private IntBuffer pWidth; // int*
	
	private IntBuffer pHeight; // int*
	
	public CoreEngine() {
		
		isRunning = false;
		
		width = 800;
		
		height = 600;
		
		framerate = 60.0;
		
		title = "Game Engine 0.4.8";
		
	}
	
	public void run() {
		
		init();
		
		loop();
		
		cleanUp();
		
	}
	
	private void init() {
		
		System.out.println("LWJGL version " + Version.getVersion());
		
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW.");
		
		else
			System.out.println("GLFW initialized.");
		
		// Configure GLFW
		glfwDefaultWindowHints();
		
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		
		// Create the window
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window.");

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
		
			pWidth = stack.mallocInt(1); // int*
		
			pHeight = stack.mallocInt(1); // int*
		
			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);
		
			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
			// Center the window
			glfwSetWindowPos(
			
				window,
				
				(vidmode.width() - pWidth.get(0)) / 2,
				
				(vidmode.height() - pHeight.get(0)) / 2
			
			);
			
		} 
		// the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
		
		//Give the window focus
		glfwFocusWindow(window);
		
		//Set window opacity... for whatever reason
		//glfwSetWindowOpacity(window, 0.5f);
		
		/*
		 This line is critical for LWJGL's interoperation with GLFW's
		 OpenGL context, or any context that is managed externally.
		 LWJGL detects the context that is current in the current thread,
		 creates the GLCapabilities instance and makes the OpenGL
		 bindings available for use.
		 */
		
		GL.createCapabilities();
		
		System.out.println("OpenGL v" + RenderingEngine.getOpenGLVersion());
		
		renderingEngine = new RenderingEngine();
		
		physicsEngine = new PhysicsEngine();
		
		/*
		 Initializing OpenAL, creating a device (default if null is
		 specified) and a context which is made current. After create
		 capabilities is called, calls to OpenAL can be made.
		 */
		
		long device = alcOpenDevice((ByteBuffer) null);
		
		ALCCapabilities deviceCapabilities = ALC.createCapabilities(device);
		
		System.out.println("OpenALC10: " + deviceCapabilities.OpenALC10);
		
		System.out.println("OpenALC11: " + deviceCapabilities.OpenALC11);
		
		if (deviceCapabilities.OpenALC11) {
			
			List<String> devices = ALUtil.getStringList(NULL, ALC_ALL_DEVICES_SPECIFIER);
			
			if(devices == null) {
				
				System.err.println("Failed to retrieve devices.");
				
			} else {
				
				for (int i = 0; i < devices.size(); i ++) {
					
					System.out.println("Device " + i + devices.get(i));
					
				}
				
			}
			
		}
		
		String defaultDevice = Objects.requireNonNull(alcGetString(NULL, ALC_DEFAULT_DEVICE_SPECIFIER));
		
		System.out.println("Default device: " + defaultDevice);
		
		long context = alcCreateContext(device, (IntBuffer) null);
		
		alcMakeContextCurrent(context);
		
		AL.createCapabilities(deviceCapabilities);
		
		if (alGetError() != AL_NO_ERROR) {
			
			System.err.println("Error was created trying to connect to a device or estabish a context: " + alGetError());
			
		}
		
		audioEngine = new AudioEngine();
		
		game = new TestGame();
		
		game.addPhysicsEngine(physicsEngine);
		
		game.addAudioEngine(audioEngine);
		
		game.init();
		
		Input.keyInput();
		
		Input.mouseButtonInput();
		
		Input.windowSizeInput();
		
		isRunning = true;
		
	}
	
	private void loop() {
		
		int frames = 0;
		
		double frameCount = 0;
		
		final double frameTime = 1.0 / framerate;
		
		double unprocessedTime = 0;
		
		boolean render;
		
		double startTime;
		
		double lastTime = Time.getTime();
		
		double passedTime;
		
		String passedTimeString;
		
		while ( isRunning ) {
			
			render = false;
			
			startTime = Time.getTime();
			
			passedTime = startTime - lastTime;
			
			lastTime = startTime;
			
			//unprocessedTime += passedTime / (double) Time.SEC;
			
			unprocessedTime += passedTime;
			
			frameCount += passedTime;
			
			while (unprocessedTime > frameTime) {
				
				render = true;
				
				unprocessedTime -= frameTime;
				
				if(glfwWindowShouldClose(window)) {
					
					stop();
					
				}
				
				game.input((float) frameTime);
				
				game.update((float) frameTime);
				
				Input.update();
				
				if(Input.shouldWindowUpdate()) {
					
					System.out.println("Resizing window!");
					
					width = (int) Input.getWindowSize().getX();
					
					height = (int) Input.getWindowSize().getY();
					
					renderingEngine.setOrthographic(width, height);
					
					System.out.println("New size: " + width + "x" + height);
					
					//TODO: Update camera class to add a method for updating the aspect ratio in a nicer way
					
					renderingEngine.getMainCamera().getViewProjection().initPerspective((float) Math.toRadians(70.0f), 
																						(float) width/(float) height, 
																						0.01f, 1000.0f);
					
				}
				
				if(frameCount >= 1.0d) {
					
					//System.out.println("FPS: " + frames);
					
					passedTimeString = Double.toString(passedTime);
					
					passedTimeString = passedTimeString.substring(0, 5);
					
					//System.out.println("Run time: " + passedTimeString + " seconds");
					
					//System.out.println("Frame time/delta: " + frameTime);
					
					frames = 0;
					
					frameCount = 0;
					
				}
				
			}
			
			if(render) {
				
				game.render(renderingEngine);
				
				frames ++;
				
			}
			else {
				
				try {
					
					Thread.sleep(1);
				
				} 
				catch (InterruptedException e) {

					e.printStackTrace();
				
				}
				
			}
			
		}
		
	}
	
	public void stop() {
		
		if(isRunning) {
		
			System.out.println("Stopping...");
			
			isRunning = false;
			
		}
		
		System.out.println("...");
		
	}
	
	private void cleanUp() {
		
		long context = alcGetCurrentContext();
		
		long device = alcGetContextsDevice(context);
		
		audioEngine.cleanUp();
		
		//Free the current context
		
		alcMakeContextCurrent(NULL);
		
		//Destroy the context
		
		alcDestroyContext(context);
		
		//Disconnect from device
		
		alcCloseDevice(device);
		
		//Unload library
		
		ALC.destroy();
		
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		
		glfwDestroyWindow(window);
		
		System.out.println("Window destroyed.");
		
		// Terminate GLFW and free the error callback
		glfwTerminate();
		
		glfwSetErrorCallback(null).free();
		
		//Unload library
		
		GL.destroy();
		
		System.out.println("GLFW terminated.");
		
		System.out.println("Exiting system...");
		
		System.exit(0);
		
	}

	public static int getWidth() {
	
		return width;
	
	}
	
	public void setWidth(int width) {
		
		this.width = width;
		
	}

	public static int getHeight() {
		
		return height;
	
	}
	
	public void setHeight(int height) {
		
		this.height = height;
		
	}

	public static double getFramerate() {
	
		return framerate;
	
	}
	
	public static long getWindow() {
		
		return window;
		
	}

	public static String getTitle() {
		
		return title;
	
	}
	
	public static Vector2D getCenter() {
		
		return new Vector2D(getWidth()/2, getHeight()/2);
	
	}
	
	public boolean getRunning() {
		
		return isRunning;
		
	}
	
	public void setRunning(boolean isRunning) {
		
		this.isRunning = isRunning;
		
	}
	
}
