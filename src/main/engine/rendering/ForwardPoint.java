package main.engine.rendering;

import main.engine.components.BaseLight;
import main.engine.components.PointLight;
import main.engine.core.Matrix4x4;
import main.engine.core.Transform;

public class ForwardPoint extends Shader{

	public static final ForwardPoint instance = new ForwardPoint();
	
	public static ForwardPoint getInstance() {
		
		return instance;
		
	}
	
	public ForwardPoint() {
		
		super();
		
		addVertexShaderFromFile("forward-point.vs");
		
		addFragmentShaderFromFile("forward-point.fs");
		
		compileShader();
		
		addUniform("diffuse");
		
		addUniform("nMap");
		
		addUniform("model");
		
		addUniform("MVP");
		
		addUniform("specularIntensity");
		
		addUniform("specularExponent");
		
		addUniform("eyePos");
		
		addUniform("pointLight.base.color");
		
		addUniform("pointLight.base.intensity");
		
		addUniform("pointLight.att.constant");
		
		addUniform("pointLight.att.linear");
		
		addUniform("pointLight.att.exponent");
		
		addUniform("pointLight.pos");
		
		addUniform("pointLight.range");
		
	}
	
	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
		
		Matrix4x4 world = transform.getTransformation();
		
		Matrix4x4 projected = renderingEngine.getMainCamera().getViewProjection().mult(world);
			
		material.getTexture("diffuse").bind(0);
		
		material.getTexture("nMap").bind(1);
		
		setUniformi("diffuse", 0);
		
		setUniformi("nMap", 1);
		
		setUniform("model", world);
		
		setUniform("MVP", projected);
		
		setUniformf("specularIntensity", material.getFloat("specularIntensity"));
		
		setUniformf("specularExponent", material.getFloat("specularExponent"));
		
		setUniform("eyePos", renderingEngine.getMainCamera().getTransform().getTransformedPos());
		
		setUniformPointLight("pointLight", (PointLight) renderingEngine.getActiveLight());
		
	}
	
	public void setUniformBaseLight(String uniformName, BaseLight baseLight) {
		
		setUniform(uniformName + ".color", baseLight.getColor());

		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
		
	}
	
	public void setUniformPointLight(String uniformName, PointLight pointLight) {
		
		setUniformBaseLight(uniformName + ".base", pointLight);
		
		setUniformf(uniformName + ".att.constant", pointLight.getConstant());
		
		setUniformf(uniformName + ".att.linear", pointLight.getLinear());
		
		setUniformf(uniformName + ".att.exponent", pointLight.getExponent());
		
		setUniform(uniformName + ".pos", pointLight.getTransform().getTransformedPos());
		
		setUniformf(uniformName + ".range", pointLight.getRange());
		
	}

}
