package main.engine.rendering.meshLoading;

import java.util.ArrayList;

import main.engine.core.Vector2D;
import main.engine.core.Vector3D;

public class IndexedModel {

	private ArrayList<Vector3D> positions;
	
	private ArrayList<Vector2D> texCoords;
	
	private ArrayList<Vector3D> normals;
	
	private ArrayList<Vector3D> tangents;
	
	private ArrayList<Integer> indices;
	
	public IndexedModel() {
		
		positions = new ArrayList<Vector3D>();
		
		texCoords = new ArrayList<Vector2D>();
		
		normals = new ArrayList<Vector3D>();
		
		tangents = new ArrayList<Vector3D>();
		
		indices = new ArrayList<Integer>();
		
	}
	
	public void calcNormals() {
		
		for(int i = 0; i < indices.size(); i += 3) {
			
			int i0 = indices.get(i);
			
			int i1 = indices.get(i + 1);
			
			int i2 = indices.get(i + 2);
				
			Vector3D v1 = positions.get(i1).sub(positions.get(i0));
			
			Vector3D v2 = positions.get(i2).sub(positions.get(i0));
			
			Vector3D normal = v1.cross(v2).getNorm();
			
			normals.get(i0).set(normals.get(i0).add(normal));
			
			normals.get(i1).set(normals.get(i1).add(normal));
			
			normals.get(i2).set(normals.get(i2).add(normal));
			
		}
		
		for(int i = 0; i < normals.size(); i++) {
			
			normals.get(i).set(normals.get(i).getNorm());
			
		}
		
	}
	
	public void calcTangents() {
		
		for(int i = 0; i < indices.size(); i += 3) {
			
			int i0 = indices.get(i);
			
			int i1 = indices.get(i + 1);
			
			int i2 = indices.get(i + 2);
				
			Vector3D e1 = positions.get(i1).sub(positions.get(i0));
			
			Vector3D e2 = positions.get(i2).sub(positions.get(i0));
			
			float du1 = texCoords.get(i1).getX() - texCoords.get(i0).getX();
			
			float du2 = texCoords.get(i2).getX() - texCoords.get(i0).getX();
			
			float dv1 = texCoords.get(i1).getY() - texCoords.get(i0).getY();
			
			float dv2 = texCoords.get(i2).getY() - texCoords.get(i0).getY();
			
			float den = (du1 * dv2 - du2 * dv1);
			
			float det = den == 0 ? 1.0f : (1.0f / den);
			
			Vector3D tangent = new Vector3D(det * (dv2 * e1.getX() - dv1 * e2.getX()), 
											det * (dv2 * e1.getY() - dv1 * e2.getY()), 
											det * (dv2 * e1.getZ() - dv1 * e2.getZ()));
			
			tangents.get(i0).set(tangents.get(i0).add(tangent));
			
			tangents.get(i1).set(tangents.get(i1).add(tangent));
			
			tangents.get(i2).set(tangents.get(i2).add(tangent));
			
		}
		
		for(int i = 0; i < tangents.size(); i++) {
			
			tangents.get(i).normalize();
			
			//tangents.get(i).set(tangents.get(i).getNorm());
			
		}
		
	}

	public ArrayList<Vector3D> getPositions() {
	
		return positions;
	
	}

	public ArrayList<Vector2D> getTexCoords() {
	
		return texCoords;
	
	}

	public ArrayList<Vector3D> getNormals() {
	
		return normals;
	
	}
	
	public ArrayList<Vector3D> getTangents() {
		
		return tangents;
	
	}

	public ArrayList<Integer> getIndices() {
	
		return indices;
	
	}
	
}
