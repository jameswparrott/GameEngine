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
	
	//TODO: Height map, can add different levels of noise
	
	public Terrain(int cellWidth, int gridWidth, int texDensity) {
		
		vertices = new ArrayList<Vertex>();
		
		indices = new ArrayList<Integer>();
		
		mesh = perlin2D(cellWidth, gridWidth, vertices, indices);
		
	}
	
	public Mesh getMesh() {
		
		return this.mesh;
		
	}
	
	public static Mesh perlin2D(int cellWidth, int gridWidth, ArrayList<Vertex> vertices, ArrayList<Integer> indices) {
		
		int nodeWidth = gridWidth + 1;
		
		int nodeSize = nodeWidth * nodeWidth;
		
		int mapWidth = cellWidth * gridWidth;
		
		float invCell = 1.0f / (float) cellWidth;
		
		Vector2D[] node = new Vector2D[nodeSize];
		
		for (int i = 0; i < nodeSize; i++) {
			
			node[i] = new Vector2D((float) (2 * Math.random() - 1), (float) (2 * Math.random() - 1));
			
			node[i].normalize();
			
		}
		
		//Offset vectors
		Vector2D o1 = new Vector2D(0, 0);
		Vector2D o2 = new Vector2D(0, 0);
		Vector2D o3 = new Vector2D(0, 0);
		Vector2D o4 = new Vector2D(0, 0);
		
		//Dot product values
		float d1 = 0;
		float d2 = 0;
		float d3 = 0;
		float d4 = 0;
		
		//Final height
		float height;
		
		for (int y = 0; y < gridWidth; y ++) {
			
			for (float j = 0; j < 1; j += invCell) {
				
				for (int x = 0; x < gridWidth; x ++) {
					
					for (float i = 0; i < 1; i += invCell) {
						
						o1.set(i, j);
						o2.set(i - 1, j);
						o3.set(i - 1, j - 1);
						o4.set(i, j - 1);
						
						d1 = o1.dot(node[x + (nodeWidth * y)]);
						d2 = o2.dot(node[x + (nodeWidth * y) + 1]);
						d3 = o3.dot(node[x + (nodeWidth * y) + nodeWidth + 1]);
						d4 = o4.dot(node[x + (nodeWidth * y) + nodeWidth]);
						
						height = interpCub(	interpCub(d1, d2, i), 
											interpCub(d4, d3, i), 
											j);
						
						vertices.add(new Vertex(new Vector3D(x + i, height, y + j), 
												new Vector2D(x + i, y + j)));
						
					}
					
				}
				
			}
			
		}
		
		for (int i = 0; i < mapWidth - 1; i ++) {
			
			for (int j = 0; j < mapWidth - 1; j ++) {
				
				//Lower triangle
				indices.add(i + j * mapWidth);
				indices.add(i + j * mapWidth + mapWidth);
				indices.add(i + j * mapWidth + mapWidth + 1);
				
				//Upper triangle
				indices.add(i + j * mapWidth + mapWidth + 1);
				indices.add(i + j * mapWidth + 1);
				indices.add(i + j * mapWidth);
				
			}
			
		}
		
		Vertex[] vertArray = new Vertex[vertices.size()];
		
		int[] intArray = new int[indices.size()];
		
		Integer[] integerArray = new Integer[indices.size()];
		
		vertices.toArray(vertArray);
		
		indices.toArray(integerArray);
		
		intArray = Util.toIntArray(integerArray);
		
		return new Mesh(vertArray, intArray, true);
		
	}
	
	private static float interpLin(float a, float b, float s) {
		
		return a + (b - a) * s;
		
	}
	
	private static float interpCub(float a, float b, float s) {
		
		return s * s * (a - b) * (2 * s - 3) + a;
		
	}
	
	private static float interpQui(float a, float b, float s) {
		
		return s * s * s * (b - a) * (6 * s * s - 15 * s + 10) + a;
		
	}

}
