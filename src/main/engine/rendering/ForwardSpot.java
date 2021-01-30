package main.engine.rendering;

import main.engine.components.BaseLight;
import main.engine.components.PointLight;
import main.engine.components.SpotLight;
import main.engine.core.Matrix4x4;
import main.engine.core.Transform;

public class ForwardSpot extends Shader{

	public static final ForwardSpot instance = new ForwardSpot();
	
	public static ForwardSpot getInstance() {
		
		return instance;
		
	}
	
	public ForwardSpot() {
		
		super();
		
		addVertexShaderFromFile("forward-spot.vs");
		
		addFragmentShaderFromFile("forward-spot.fs");
		
		compileShader();
		
		addUniform("diffuse");
		
		addUniform("nMap");
		
		addUniform("model");
		
		addUniform("MVP");
		
		addUniform("specularIntensity");
		
		addUniform("specularExponent");
		
		addUniform("eyePos");
		
		addUniform("spotLight.pointLight.base.color");
		
		addUniform("spotLight.pointLight.base.intensity");
		
		addUniform("spotLight.pointLight.att.constant");
		
		addUniform("spotLight.pointLight.att.linear");
		
		addUniform("spotLight.pointLight.att.exponent");
		
		addUniform("spotLight.pointLight.pos");
		
		addUniform("spotLight.pointLight.range");
		
		addUniform("spotLight.direction");
		
		//Update: change "range" here to something like "focus"
		
		addUniform("spotLight.focus");
		
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
		
		setUniformSpotLight("spotLight", (SpotLight) renderingEngine.getActiveLight());
		
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
	
	public void setUniformSpotLight(String uniformName, SpotLight spotLight) {
		
		setUniformPointLight(uniformName + ".pointLight", spotLight);
	
		setUniform(uniformName + ".direction", spotLight.getDirection());
		
		setUniformf(uniformName + ".focus", spotLight.getFocus());
		
	}

}
