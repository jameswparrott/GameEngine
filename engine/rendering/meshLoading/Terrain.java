package main.engine.rendering.meshLoading;

import java.util.ArrayList;

import main.engine.core.Vector2D;
import main.engine.core.Vector3D;
import main.engine.rendering.Vertex;

public class Terrain {
	
	//private HashMap<Vector3D, >
	
	private ArrayList<Vertex> vertices;
	
	private ArrayList<Integer> indices;
	
	private Vector2D texCoords1 = new Vector2D(0, 0);
	
	private Vector2D texCoords2 = new Vector2D(1, 0);
	
	private Vector2D texCoords3 = new Vector2D(0, 1);
	
	private Vector2D texCoords4 = new Vector2D(1, 1);
	
	public Terrain(Vector2D start, Vector2D end, float width, float depth, float minHeight, float maxHeight) {
		
		int x = (int) ((end.getX() - start.getX()) / width);
		
		int z = (int) ((end.getY() - start.getY()) / depth);
		
		for (int i = 0; i < x; i ++) {
			
			for (int j = 0; j < z; j ++) {
				
				vertices.add(new Vertex(new Vector3D(start.getX() + i * width, 0, start.getY() + j * depth), texCoords1));
				
				vertices.add(new Vertex(new Vector3D(start.getX() + (i + 1) * width, 0, start.getY() + j * depth), texCoords2));
				
				vertices.add(new Vertex(new Vector3D(start.getX() + i * width, 0, start.getY() + (j + 1) * depth), texCoords3));
				
				vertices.add(new Vertex(new Vector3D(start.getX() + (i + 1) * width, 0, start.getY() + (j + 1) * depth), texCoords4));
				
			}
			
		}
		
	}

}
