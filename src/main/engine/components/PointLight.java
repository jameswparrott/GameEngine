package main.engine.components;

import main.engine.core.Vector3D;
import main.engine.rendering.ForwardPoint;

public class PointLight extends BaseLight {

    private static final int COLOR_DEPTH = 256;

    private Vector3D attenuation;

    private float range;

    public PointLight(Vector3D color, float intensity, Vector3D attenuation) {

        super(color, intensity);

        this.attenuation = attenuation;

        // TODO: Calculate proper range based on attenuation!

        float a = attenuation.getZ();

        float b = attenuation.getY();

        float c = attenuation.getX() - COLOR_DEPTH * intensity * color.max();

        this.range = (float) (-b + Math.sqrt((b * b) - (4 * a * c))) / (2 * a);

        setShader(ForwardPoint.getInstance());

    }

    public float getConstant() {

        return attenuation.getX();

    }

    public void setConstant(float constant) {

        this.attenuation.setX(constant);

    }

    public float getLinear() {

        return attenuation.getY();

    }

    public void setLinear(float linear) {

        this.attenuation.setY(linear);

    }

    public float getExponent() {

        return attenuation.getZ();

    }

    public void setExponent(float exponent) {

        this.attenuation.setZ(exponent);

    }

    public float getRange() {

        return range;

    }

    public void setRange(float range) {

        this.range = range;

    }

}
