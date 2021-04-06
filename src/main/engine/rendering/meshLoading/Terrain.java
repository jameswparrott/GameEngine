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
	
	private int gridWidth;
	private int cellWidth;
	private int mapWidth;
	
	private float[][] heightMap;
	
	//TODO: Height map, can add different levels of noise
	
	public Terrain(int cellSubdiv, int numCells, int cellWidth) {
		
		vertices = new ArrayList<Vertex>();
		
		indices = new ArrayList<Integer>();
		
		mesh = perlin2D(cellSubdiv, numCells, cellWidth, vertices, indices);
		
	}
	
	//TODO: Generate flat terrain, then add to height map with addPerlin
	public Terrain(int gridWidth, int cellWidth) {
		
		this.gridWidth = gridWidth;
		
		this.cellWidth = cellWidth;
		
		mapWidth = gridWidth * cellWidth;
		
		heightMap = new float[mapWidth][mapWidth];
		
		init();
		
	}
	
	private void init() {
		
		for (int i = 0; i < mapWidth; i ++) {
			
			for (int j = 0; j < mapWidth; j ++) {
				
				heightMap[i][j] = 0.0f;
				
			}
			
		}
		
	}
	
	public void addPerlin(int scale, int max, int min) {
		
		//Add a layer of noise
		
	}
	
	public void genMesh() {
		
		//Generate final mesh
		vertices = new ArrayList<Vertex>();
		
		indices = new ArrayList<Integer>();
		
	}
	
	public Mesh perlin(int gridWidth, int cellWidth) {
		
		int nodeWidth = gridWidth + 1;
		int nodeSize = nodeWidth * nodeWidth;
		int mapWidth = gridWidth * cellWidth;
		int mapSize = mapWidth * mapWidth;
		
		float invGrid = 1.0f / (float) gridWidth;
		float invCell = 1.0f / (float) cellWidth;
		float invMap = invGrid * invCell;
		
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
		
		int u, v, x, y, a, b;
		
		for (int i = 0; i < mapSize; i ++) {
			
			//Cell coordinates
			u = i % cellWidth;
			v = (int) (i * invMap);
			
			//Grid coordinates
			x = (int) (u * invCell);
			y = (int) (v * invCell);
			
			a = (int) (x * invGrid);
			b = (int) (y * invGrid);
			
			o1.set(u - x, v - y);
			o2.set(u - x + cellWidth, v - y);
			o3.set(u - x + cellWidth, v - y + cellWidth);
			o4.set(u - x, v - y + cellWidth);
			
			d1 = o1.dot(node[a + (nodeWidth * b)]);
			d2 = o2.dot(node[a + (nodeWidth * b) + 1]);
			d3 = o3.dot(node[a + (nodeWidth * b) + nodeWidth + 1]);
			d4 = o4.dot(node[a + (nodeWidth * b) + nodeWidth]);
			
			height = interpCub(	interpCub(d1, d2, u - x), 
								interpCub(d4, d3, u - x), 
								v - y);

			vertices.add(new Vertex(new Vector3D(u, height, v), 
									new Vector2D(u, v)));
			
			//Lower triangle
			indices.add(u + v * mapWidth);
			indices.add(u + v * mapWidth + mapWidth);
			indices.add(u + v * mapWidth + mapWidth + 1);
			
			//Upper triangle
			indices.add(u + v * mapWidth + mapWidth + 1);
			indices.add(u + v * mapWidth + 1);
			indices.add(u + v * mapWidth);
			
		}
		
		Vertex[] vertArray = new Vertex[vertices.size()];
		
		int[] intArray = new int[indices.size()];
		
		Integer[] integerArray = new Integer[indices.size()];
		
		vertices.toArray(vertArray);
		
		indices.toArray(integerArray);
		
		intArray = Util.toIntArray(integerArray);
		
		return new Mesh(vertArray, intArray, true);
		
	}
	
	public static Mesh perlin2D(int cellSubdiv, int numCells, int cellWidth, ArrayList<Vertex> vertices, ArrayList<Integer> indices) {
		
		int nodeWidth = numCells + 1;
		
		int nodeSize = nodeWidth * nodeWidth;
		
		int mapWidth = cellSubdiv * numCells;
		
		float invCell = 1.0f / (float) cellSubdiv;
		
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
		
		for (int y = 0; y < numCells; y ++) {
			
			for (float j = 0; j < 1; j += invCell) {
				
				for (int x = 0; x < numCells; x ++) {
					
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
						
						vertices.add(new Vertex(new Vector3D((x + i) * cellWidth, height, (y + j) * cellWidth), 
												new Vector2D((x + i) * cellWidth, (y + j) * cellWidth)));
						
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
	
	//Linear interpolation
	private static float interpLin(float a, float b, float s) {
		
		return a + (b - a) * s;
		
	}
	
	//Cubic interpolation
	private static float interpCub(float a, float b, float s) {
		
		return s * s * (a - b) * (2 * s - 3) + a;
		
	}
	
	//Quintic interpolation
	private static float interpQui(float a, float b, float s) {
		
		return s * s * s * (b - a) * (6 * s * s - 15 * s + 10) + a;
		
	}
	
	public Mesh getMesh() {
		
		return this.mesh;
		
	}

}
