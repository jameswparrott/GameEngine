package main.engine.rendering;

import main.engine.core.Matrix4x4;
import main.engine.core.Transform;

public class ForwardAmbient extends Shader{

	public static final ForwardAmbient instance = new ForwardAmbient();
	
	public static ForwardAmbient getInstance() {
		
		return instance;
		
	}
	
	public ForwardAmbient() {
		
		super();
		
		addVertexShaderFromFile("forward-ambient.vs");
		
		addFragmentShaderFromFile("forward-ambient.fs");
		
		compileShader();
		
		addUniform("MVP");
		
		addUniform("texture_sampler");
		
		addUniform("ambientIntensity");
		
	}
	
	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
		
		Matrix4x4 world = transform.getTransformation();
		
		//System.out.println("Ambient world matrix: ");
		
		//world.print();
		
		//System.out.println("Ambient projected matrix: ");
		
		//renderingEngine.getMainCamera().getViewProjection().print();
		
		Matrix4x4 projected = renderingEngine.getMainCamera().getViewProjection().mult(world);
		
		//System.out.println("Ambient multiplied matrix: ");
		
		//projected.print();
			
		material.getTexture("diffuse").bind(0);
		
		setUniform("MVP", projected);
		
		setUniform("ambientIntensity", renderingEngine.getAmbientLight());
		
	}

}
