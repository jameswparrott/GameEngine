package main.engine.rendering;

import main.engine.components.BaseLight;
import main.engine.components.DirectionalLight;
import main.engine.core.Matrix4x4;
import main.engine.core.Transform;

public class ForwardDirectional extends Shader{

	public static final ForwardDirectional instance = new ForwardDirectional();
	
	public static ForwardDirectional getInstance() {
		
		return instance;
		
	}
	
	public ForwardDirectional() {
		
		super();
		
		addVertexShaderFromFile("forward-directional.vs");
		
		addFragmentShaderFromFile("forward-directional.fs");
		
		compileShader();
		
		addUniform("diffuse");
		
		addUniform("nMap");
		
		addUniform("model");
		
		addUniform("MVP");
		
		addUniform("specularIntensity");
		
		addUniform("specularExponent");
		
		addUniform("eyePos");
		
		addUniform("directionalLight.base.color");
		
		addUniform("directionalLight.base.intensity");
		
		addUniform("directionalLight.direction");
		
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
		
		setUniformDirectionalLight("directionalLight", (DirectionalLight) renderingEngine.getActiveLight());
		
	}
	
	public void setUniformBaseLight(String uniformName, BaseLight baseLight) {
		
		setUniform(uniformName + ".color", baseLight.getColor());

		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
		
	}
	
	public void setUniformDirectionalLight(String uniformName, DirectionalLight directionalLight) {
		
		setUniformBaseLight(uniformName + ".base", directionalLight);
		
		setUniform(uniformName + ".direction", directionalLight.getDirection());
		
	}

}
