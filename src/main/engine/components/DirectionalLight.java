package main.engine.components;

import main.engine.core.Vector3D;
import main.engine.rendering.ForwardDirectional;

public class DirectionalLight extends BaseLight {

    public DirectionalLight(Vector3D color, float intensity) {

        super(color, intensity);

        setShader(ForwardDirectional.getInstance());

    }

    public Vector3D getDirection() {

        return getTransform().getTransformedRot().getForward();

    }

}
