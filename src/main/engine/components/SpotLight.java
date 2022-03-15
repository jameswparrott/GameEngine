package main.engine.components;

import main.engine.core.Vector3D;
import main.engine.rendering.ForwardSpot;

public class SpotLight extends PointLight {

    private float focus;

    public SpotLight(Vector3D color, float intensity, Vector3D attenuation, float focus) {

        super(color, intensity, attenuation);

        this.focus = focus;

        setShader(ForwardSpot.getInstance());

    }

    public Vector3D getDirection() {

        return getTransform().getTransformedRot().getForward();

    }

    public float getFocus() {

        return focus;

    }

    public void setFocus(float focus) {

        this.focus = focus;

    }

}
