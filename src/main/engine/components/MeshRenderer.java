package main.engine.components;

import main.engine.rendering.Mesh;
import main.engine.rendering.RenderingEngine;
import main.engine.rendering.Shader;
import main.engine.rendering.Material;

public class MeshRenderer extends GameComponent{

	private Mesh mesh;
	
	private Material material;
	
	public MeshRenderer(Mesh mesh, Material material){
		
		this.mesh = mesh;
		
		this.material = material;
		
	}
	
	@Override
	public void render(Shader shader, RenderingEngine renderingEngine) {
		
		shader.bind();
		
		shader.updateUniforms(getTransform(), material, renderingEngine);
		
		mesh.draw();
		
	}
	
}
