package main.engine.rendering.meshLoading;

import java.util.ArrayList;

import main.engine.core.Util;
import main.engine.core.Vector2D;
import main.engine.core.Vector3D;
import main.engine.rendering.Mesh;
import main.engine.rendering.Vertex;

public class PrimitiveModel2D {
	
	public enum Primitive2D{
		
		triangle,
		
		rectangle,
		
		circle
		
	};
	
	private static final int CIRCLE_PRECISION = 32;
	
	private Mesh mesh;
	
	private ArrayList<Vertex> vertices;
	
	private ArrayList<Integer> indices;
	
	public PrimitiveModel2D(float width, float height) {
		
		this(Primitive2D.rectangle, width, height);
		
	}
	
	public PrimitiveModel2D(float width, float height, float[] texCoords) {
		
		this(Primitive2D.rectangle, new Vector3D(0, 0, 0), width, height, true, true, false, false, texCoords);
		
	}
	
	public PrimitiveModel2D(Primitive2D primitive, float width, float height) {
		
		this(primitive, new Vector3D(0, 0, 0), width, height, true, true, false, false, new float[] {0, 1, 0, 1});
		
	}
	
	public PrimitiveModel2D(Primitive2D primitive, Vector3D pos, float width, float height, boolean x, boolean y, boolean z, boolean flip) {
		
		this(primitive, pos, width, height, x, y, z, flip, new float[] {0, 1, 0, 1});
		
	}

	public PrimitiveModel2D(Primitive2D primitive, Vector3D pos, float width, float height, boolean x, boolean y, boolean z, boolean flip, float[] texCoords) {
		
		//TODO: Direction facing
		
		//Take in a quaternion
		
		vertices = new ArrayList<Vertex>();
		
		indices = new ArrayList<Integer>();
		
		mesh = generate2D(primitive, pos, width, height, x, y, z, flip, texCoords);
		
	}
	
	private Mesh generate2D(Primitive2D primitive, Vector3D pos, float width, float height, boolean x, boolean y, boolean z, boolean flip, float[] texCoords) {
		
		switch(primitive) {
		
		case triangle:
			
			return generateTri(pos, width, height, x, y, z, flip, texCoords);
			
		case rectangle:
			
			return generateRect(pos, width, height, x, y, z, flip, texCoords);
			
		case circle:
			
			return generateCircle(pos, width, height, x, y, z, flip, texCoords);
			
		default:
			
			System.err.println("Invalid 2D primitive type: " + primitive);
			
			return null;
		
		}
		
	}
	
	private Mesh generateTri(Vector3D pos, float width, float height, boolean x, boolean y, boolean z, boolean flip, float[] texCoords) {
		
//		indices.add(0);
//		
//		indices.add(1);
//		
//		indices.add(2);
		
		if (flip) {
			
			indices.add(2);
			
			indices.add(1);
			
			indices.add(0);
			
		}else {
			
			indices.add(0);
			
			indices.add(1);
			
			indices.add(2);
			
		}
		
		if(x && y) {
			
			vertices.add(new Vertex(pos.add(new Vector3D(width/2, -height/2, 0)), new Vector2D(texCoords[1], texCoords[2])));
			
			vertices.add(new Vertex(pos.add(new Vector3D(0, height/2, 0)), new Vector2D((texCoords[1] - texCoords[0])/2f, texCoords[3])));
			
			vertices.add(new Vertex(pos.add(new Vector3D(-width/2, -height/2, 0)), new Vector2D(texCoords[0], texCoords[2])));
			
		}else if(y && z) {
			
			vertices.add(new Vertex(pos.add(new Vector3D(0, -height/2, width/2)), new Vector2D(texCoords[1], texCoords[2])));
			
			vertices.add(new Vertex(pos.add(new Vector3D(0, height/2, 0)), new Vector2D((texCoords[1] - texCoords[0])/2f, texCoords[3])));
			
			vertices.add(new Vertex(pos.add(new Vector3D(0, -height/2, -width/2)), new Vector2D(texCoords[0], texCoords[2])));
			
		}else if(x && z) {
			
			vertices.add(new Vertex(pos.add(new Vector3D(width/2, 0, -height/2)), new Vector2D(texCoords[1], texCoords[2])));
			
			vertices.add(new Vertex(pos.add(new Vector3D(0, 0, height/2)), new Vector2D((texCoords[1] - texCoords[0])/2f, texCoords[3])));
			
			vertices.add(new Vertex(pos.add(new Vector3D(-width/2, 0, -height/2)), new Vector2D(texCoords[0], texCoords[2])));
			
		}
		
		return new Mesh(getVertexArray(vertices), getIntArray(indices), true);
		
	}
	
	private Mesh generateRect(Vector3D pos, float width, float height, boolean x, boolean y, boolean z, boolean flip, float[] texCoords) {
		
		if (flip) {
			
			indices.add(2);
			
			indices.add(1);
			
			indices.add(0);
			
			indices.add(3);
			
			indices.add(2);
			
			indices.add(0);
			
		}else {
			
			indices.add(0);
			
			indices.add(1);
			
			indices.add(2);
			
			indices.add(0);
			
			indices.add(2);
			
			indices.add(3);
			
		}
		
		if(x && y) {
			
			vertices.add(new Vertex(pos.add(new Vector3D(-width/2, -height/2, 0)), new Vector2D(texCoords[0], texCoords[2])));
			
			vertices.add(new Vertex(pos.add(new Vector3D(-width/2, height/2, 0)), new Vector2D(texCoords[0], texCoords[3])));
			
			vertices.add(new Vertex(pos.add(new Vector3D(width/2, height/2, 0)), new Vector2D(texCoords[1], texCoords[3])));
			
			vertices.add(new Vertex(pos.add(new Vector3D(width/2, -height/2, 0)), new Vector2D(texCoords[1], texCoords[2])));
			
		} else if (y && z) {
			
			vertices.add(new Vertex(pos.add(new Vector3D(0, -height/2, -width/2)), new Vector2D(texCoords[0], texCoords[2])));
			
			vertices.add(new Vertex(pos.add(new Vector3D(0, height/2, -width/2)), new Vector2D(texCoords[0], texCoords[3])));
			
			vertices.add(new Vertex(pos.add(new Vector3D(0, height/2, width/2)), new Vector2D(texCoords[1], texCoords[3])));
			
			vertices.add(new Vertex(pos.add(new Vector3D(0, -height/2, width/2)), new Vector2D(texCoords[1], texCoords[2])));
			
		} else if(x && z) {
			
			vertices.add(new Vertex(pos.add(new Vector3D(-width/2, 0, -height/2)), new Vector2D(texCoords[0], texCoords[2])));
			
			vertices.add(new Vertex(pos.add(new Vector3D(-width/2, 0, height/2)), new Vector2D(texCoords[0], texCoords[3])));
			
			vertices.add(new Vertex(pos.add(new Vector3D(width/2, 0, height/2)), new Vector2D(texCoords[1], texCoords[3])));
			
			vertices.add(new Vertex(pos.add(new Vector3D(width/2, 0, -height/2)), new Vector2D(texCoords[1], texCoords[2])));
			
		}
		
		return new Mesh(getVertexArray(vertices), getIntArray(indices), true);
		
	}
	
	private Mesh generateCircle(Vector3D pos, float width, float height, boolean x, boolean y, boolean z, boolean flip, float[] texCoords) {
		
		float xScaleCoords = (texCoords[1] - texCoords[0])/2f;
		
		float yScaleCoords = (texCoords[3] - texCoords[2])/2f;
		
		float radius = width/2;
		
		float cos;
		
		float sin;
		
		float xTex;
		
		float yTex;
		//center
		
		vertices.add(new Vertex(pos, new Vector2D(xScaleCoords, yScaleCoords)));
		
		//edges
		
		for( int i = 0; i <= CIRCLE_PRECISION; i ++) {
			
			cos = (float) Math.cos((float)(Math.PI * 2 * i)/(float)CIRCLE_PRECISION);
			
			sin = (float) Math.sin((float)(Math.PI * 2 * i)/(float)CIRCLE_PRECISION);
			
			xTex = cos * xScaleCoords + xScaleCoords;
			
			yTex = sin * yScaleCoords + yScaleCoords;
			
			if(x && y) {
				
				vertices.add(new Vertex(pos.add(new Vector3D(cos, sin, 0).scale(radius)), new Vector2D(xTex, yTex)));
				
			} else if (y && z) {
				
				vertices.add(new Vertex(pos.add(new Vector3D(0, sin, cos).scale(radius)), new Vector2D(xTex, yTex)));
				
			} else if (x && z) {
				
				vertices.add(new Vertex(pos.add(new Vector3D(cos, 0, sin).scale(radius)), new Vector2D(xTex, yTex)));
				
			}
			
			//vertices.add(new Vertex(new Vector3D(cos, sin, 0).scale(radius), new Vector2D(xTex, yTex)));
			
			if(i > 1) {
				
				if(flip) {
					
					indices.add(i - 1);
					
					indices.add(i);
					
					indices.add(0);
					
				}else {
					
					indices.add(0);
					
					indices.add(i);
					
					indices.add(i - 1);
					
				}
				
			}
			
		}
		
		indices.add(0);
		
		indices.add(1);
		
		indices.add(CIRCLE_PRECISION);
		
		return new Mesh(getVertexArray(vertices), getIntArray(indices), true);
		
	}
	
	private Vertex[] getVertexArray(ArrayList<Vertex> vertices) {
		
		Vertex[] result = new Vertex[vertices.size()];
		
		vertices.toArray(result);
		
		return result;
		
	}
	
	private int[] getIntArray(ArrayList<Integer> indices) {
		
		Integer[] integerArray = new Integer[indices.size()];
		
		int[] result = new int[indices.size()];
		
		indices.toArray(integerArray);
		
		result = Util.toIntArray(integerArray);
		
		return result;
		
	}
	
	public ArrayList<Vertex> getVertices(){
		
		return vertices;
		
	}
	
	public Mesh getMesh() {
		
		return mesh;
		
	}
	
}
