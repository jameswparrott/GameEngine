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
		
	}
	
	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
		
		//TODO: Fix projection!!
		
		Matrix4x4 world = transform.getTransformation();
		
		Matrix4x4 orthographic = renderingEngine.getOrthographic().mult(world);
			
		material.getTexture("diffuse").bind(0);
		
		setUniform("MVP", orthographic);
		
//		System.out.println("Hud world matrix: ");
//		
//		world.print();
//		
//		System.out.println("Hud orthographic matrix: ");
//		
//		renderingEngine.getOrthogonal().print();
//		
//		System.out.println("Hud multiplied matrix: ");
//		
//		orthographic.print();
		
		//TODO: find a good way to pass in a shading color
		
	}
	
}
