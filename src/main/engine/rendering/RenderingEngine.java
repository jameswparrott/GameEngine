package main.engine.rendering;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_CW;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_EQUAL;
import static org.lwjgl.opengl.GL11.GL_LESS;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;
import static org.lwjgl.opengl.GL32.GL_FRAMEBUFFER_SRGB;

import java.util.ArrayList;

import main.engine.components.BaseLight;
import main.engine.components.Camera;
import main.engine.core.CoreEngine;
import main.engine.core.GameObject;
import main.engine.core.Matrix4x4;
import main.engine.core.Vector3D;

public class RenderingEngine {

    private Camera mainCamera;

    private Matrix4x4 orthographic;

    private Vector3D ambientLight;

    private ArrayList<BaseLight> lights;

    private BaseLight activeLight;

    public RenderingEngine() {

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glFrontFace(GL_CW);

        glCullFace(GL_BACK);

        glEnable(GL_CULL_FACE);

        glEnable(GL_DEPTH_TEST);

        glEnable(GL_DEPTH_CLAMP);

        glEnable(GL_TEXTURE_2D);

        glEnable(GL_FRAMEBUFFER_SRGB);

        // mainCamera = new Camera((float) Math.toRadians(70.0f), (float)
        // CoreEngine.getWidth() / (float) CoreEngine.getHeight(), 0.01f, 1000.0f);

        ambientLight = new Vector3D(0.05f, 0.05f, 0.05f);

        lights = new ArrayList<BaseLight>();

        orthographic = new Matrix4x4().initOrthographic(0, CoreEngine.getWidth(), 0, CoreEngine.getHeight(), -1, 1);

    }

    public Vector3D getAmbientLight() {

        return ambientLight;

    }
    
    public void setAmbientLight(Vector3D ambientLight) {
        
        this.ambientLight = ambientLight;
        
    }

    public void render(GameObject object) {

        clearScreen();

        lights.clear();

        object.addToRenderingEngine(this);

        Shader forwardAmbient = ForwardAmbient.getInstance();

        object.render(forwardAmbient, this);

        glEnable(GL_BLEND);

        glBlendFunc(GL_ONE, GL_ONE);

        glDepthMask(false);

        glDepthFunc(GL_EQUAL);

        for (BaseLight light : lights) {

            activeLight = light;

            object.render(light.getShader(), this);

        }

        glDepthFunc(GL_LESS);

        glDepthMask(true);

        glDisable(GL_BLEND);

        swapBuffers();

    }

    public void render(GameObject object, GameObject hudObject) {

        clearScreen();

        lights.clear();

        object.addToRenderingEngine(this);

        Shader forwardAmbient = ForwardAmbient.getInstance();

        object.render(forwardAmbient, this);

        glEnable(GL_BLEND);

        glBlendFunc(GL_ONE, GL_ONE);

        glDepthMask(false);

        glDepthFunc(GL_EQUAL);

        for (BaseLight light : lights) {

            activeLight = light;

            object.render(light.getShader(), this);

        }

        glDepthFunc(GL_LESS);

        glDepthMask(true);

        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        Shader hudShader = HudShader.getInstance();

        hudObject.render(hudShader, this);

        glDisable(GL_BLEND);

        swapBuffers();

    }

    private static void clearScreen() {

        // TODO: Stencil Buffer

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    }

    private static void swapBuffers() {

        glfwSwapBuffers(CoreEngine.getWindow());

        glfwPollEvents();

    }

    public static void unbindTextures() {

        glBindTexture(GL_TEXTURE_2D, 0);

    }

    public static void setClearColor(Vector3D color) {

        glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);

    }

    public static void setClearColor(float r, float g, float b) {

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

    public Matrix4x4 getOrthographic() {

        return this.orthographic;

    }

    public void setOrthographic(int width, int height) {

        this.orthographic = orthographic.initOrthographic(0, width, 0, height, -1, 1);

    }

}
