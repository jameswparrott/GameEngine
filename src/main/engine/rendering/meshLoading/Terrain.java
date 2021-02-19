package main.engine.rendering.meshLoading;

import java.util.ArrayList;

import main.engine.core.Util;
import main.engine.core.Vector2D;
import main.engine.core.Vector3D;
import main.engine.rendering.Mesh;
import main.engine.rendering.Vertex;

public class Terrain {
	
	private ArrayList<Vertex> vertices;
	
	private ArrayList<Integer> indices;
	
	private Mesh mesh;
	
	public Terrain(int x_start, int z_start, int x_end, int z_end, float width) {
		
		//-5, -5, 5, 5, 0.1f, 40, 0, 0
		
	}
	
	public Terrain(int x_start, int z_start, int x_end, int z_end, float width, float minHeight, float maxHeight) {
		
		vertices = new ArrayList<Vertex>();
		
		indices = new ArrayList<Integer>();
		
		System.out.println("Loading terrain...");
		
		int ncols = (int) ((x_end - x_start + 1)/ width);
		
		int nrows = (int) ((z_end - z_start + 1)/ width);
		
		int texDensity = (int) ((x_end - x_start) * 20 * width);
		
		for (int i = 0; i < ncols; i ++) {
			
			for (int j = 0; j < nrows; j ++) {
				
				vertices.add(
						new Vertex(
								new Vector3D(x_start + i * width, 0.15f * (float) Math.random(), z_start + j * width), 
								new Vector2D((float) texDensity * i/ (float) ncols, (float) texDensity * j/ (float) nrows)));
				
				if (i < ncols - 1 && j < nrows - 1) {
					
					//First triangle
					
					indices.add((i + 1) * nrows + j);
					
					indices.add(i * nrows + j);
					
					indices.add(i * nrows + j + 1);
					
					//Second triangle
					
					indices.add(i * nrows + j + 1);
					
					indices.add((i + 1) * nrows + j + 1);
					
					indices.add((i + 1) * nrows + j);
					
				}
				
			}
			
		}
		
//		for (int i = 0; i < vertices.size(); i ++) {
//			
//			System.out.println("Position:" + vertices.get(i).getPos().toString());
//			
//			System.out.println("Normal:" + vertices.get(i).getNormal());
//			
//		}
//		
//		for (int i = 0; i < indices.size(); i ++) {
//			
//			System.out.println("Index" + indices.get(i));
//			
//		}
		
		Vertex[] vertArray = new Vertex[vertices.size()];
		
		int[] intArray = new int[indices.size()];
		
		Integer[] integerArray = new Integer[indices.size()];
		
		vertices.toArray(vertArray);
		
		indices.toArray(integerArray);
		
		intArray = Util.toIntArray(integerArray);
		
		mesh = new Mesh(vertArray, intArray, true);
		
	}
	
	public Mesh getMesh() {
		
		return this.mesh;
		
	}

}
