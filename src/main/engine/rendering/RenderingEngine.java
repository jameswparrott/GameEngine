package main.engine.rendering;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

import java.util.ArrayList;

import main.engine.components.BaseLight;
import main.engine.components.Camera;
import main.engine.core.CoreEngine;
import main.engine.core.GameObject;
import main.engine.core.Vector3D;

public class RenderingEngine {
	
	private Camera mainCamera;
	
	private Vector3D ambientLight;
	
	private ArrayList<BaseLight> lights;
	
	private BaseLight activeLight;
	
	public RenderingEngine() {
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glFrontFace(GL_CW);
		
		glCullFace(GL_BACK);
		
		glEnable(GL_CULL_FACE);
		
		glEnable(GL_DEPTH_TEST);
		
//		glEnable(GL_BLEND);
//		
//		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glEnable(GL_DEPTH_CLAMP);
		
		//glEnable(GL_TEXTURE_2D);
		
		//glEnable(GL_FRAMEBUFFER_SRGB);
		
		//mainCamera = new Camera((float) Math.toRadians(70.0f), (float) CoreEngine.getWidth() / (float) CoreEngine.getHeight(), 0.01f, 1000.0f);
		
		ambientLight = new Vector3D(0.0f, 0.0f, 0.0f);
		
		lights = new ArrayList<BaseLight>();
		
	}
	
	public Vector3D getAmbientLight() {
		
		return ambientLight;
		
	}
	
	public void render(GameObject object) {
		
		clearScreen();
		
		lights.clear();
		
		//glEnable(GL_BLEND);
		
		//Use only for sprites!
		
		//glEnable(GL_BLEND);
		
		//glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		object.addToRenderingEngine(this);
		
		Shader forwardAmbient = ForwardAmbient.getInstance();
		
		object.render(forwardAmbient, this);
		
		glEnable(GL_BLEND);
		
		//glBlendFunc(GL_ONE, GL_ONE);
		
		//glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
		
		//glBlendFunc(GL_SRC_ALPHA, GL_ONE);
		
		//glBlendFunc(GL_SRC_ALPHA, GL_EQUAL);
		
		glBlendFunc(GL_ONE, GL_ONE);
		
		glDepthMask(false);
		
		glDepthFunc(GL_EQUAL);
		
		for(BaseLight light : lights) {
			
			activeLight = light;
			
			object.render(light.getShader(), this);
			
		}
		
		glDepthFunc(GL_LESS);
		
		glDepthMask(true);
		
		glDisable(GL_BLEND);
		
		//Move back into main core loop
		
		glfwSwapBuffers(CoreEngine.getWindow());
		
		glfwPollEvents();
		
	}
	
	private static void clearScreen() {
		
		//TODO: Stencil Buffer
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		
	}
	
	public static void unbindTextures() {
		
		glBindTexture(GL_TEXTURE_2D, 0);
		
	}
	
	public static void setClearColor(Vector3D color){
		
		glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
		
	}
	
	public static void setClearColor(float r, float g, float b){
		
		glClearColor(r, g, b, 1.0f);
		
	}
	
	public static String getOpenGLVersion() {
		
		return glGetString(GL_VERSION);
		
	}
	
	public void addLight(BaseLight light) {
		
		lights.add(light);
		
	}
	
	public void addCamera(Camera camera) {
		
		mainCamera = camera;
		
	}
	
	public BaseLight getActiveLight() {
		
		return activeLight;
		
	}

	public Camera getMainCamera() {
		
		return mainCamera;
	
	}

	public void setMainCamera(Camera mainCamera) {
		
		this.mainCamera = mainCamera;
	
	}

}
