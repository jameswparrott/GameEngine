package main.engine.components;

import main.engine.core.Vector3D;
import main.engine.rendering.RenderingEngine;
import main.engine.rendering.Shader;

public class BaseLight extends GameComponent {

    private Vector3D color;

    private float intensity;

    private Shader shader;

    public BaseLight(Vector3D color, float intensity) {

        this.color = color;

        this.intensity = intensity;

    }

    @Override
    public void addToRenderingEngine(RenderingEngine renderingEngine) {

        renderingEngine.addLight(this);

    }

    public void setShader(Shader shader) {

        this.shader = shader;

    }

    public Shader getShader() {

        return shader;

    }

    public Vector3D getColor() {

        return color;

    }

    public void setColor(Vector3D color) {

        this.color = color;

    }

    public float getIntensity() {

        return intensity;

    }

    public void setIntensity(float intensity) {

        this.intensity = intensity;

    }

}
