package main.engine.rendering;

import main.engine.core.Matrix4x4;
import main.engine.core.Transform;

public class HudShader extends Shader{

	public static final HudShader instance = new HudShader();
	
	public static HudShader getInstance() {
		
		return instance;
		
	}
	
	public HudShader() {
		
		super();
		
		addVertexShaderFromFile("hud.vs");
		
		addFragmentShaderFromFile("hud.fs");
		
		compileShader();
		
		addUniform("MVP");
		
		addUniform("texture_sampler");
		
		addUniform("baseColor");
		
	}
	
	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
		
		//TODO: Fix projection!!
		
		Matrix4x4 world = transform.getTransformation();
		
		//Change to orthographic projection
		
		Matrix4x4 orthographic = renderingEngine.getOrthogonal().mult(world);
			
		material.getTexture("diffuse").bind(0);
		
		setUniform("MVP", orthographic);
		
		//TODO: find a good way to pass in a shading color
		
		setUniform("baseColor", renderingEngine.getAmbientLight());
		
	}
	
}
