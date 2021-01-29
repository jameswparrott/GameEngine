package main.engine.rendering;

import static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.opengl.GL15.*;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.util.ArrayList;

import main.engine.core.Util;
import main.engine.core.Vector3D;
import main.engine.rendering.meshLoading.IndexedModel;
import main.engine.rendering.meshLoading.OBJModel;

public class Mesh {

	private int vbo, vao, ibo;
	
	private int size;
	
	public Mesh(String fileName) {
		
		initMeshData();
		
		loadMesh(fileName);
		
	}
	
	public Mesh(Vertex[] vertices, int[] indices) {
		
		this(vertices, indices, false);
		
	}
	
	public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals) {
		
		initMeshData();
		
		addVertices(vertices, indices, calcNormals);
		
	}
	
	private void initMeshData() {
		
		vao = glGenVertexArrays();
		
		glBindVertexArray(vao);
		
		vbo = glGenBuffers();
		
		ibo = glGenBuffers();
		
		size = 0;
		
	}
	
	public void addVertices(Vertex[] vertices, int[] indices) {
		
		addVertices(vertices, indices, false);
		
	}
	
	private void addVertices(Vertex[] vertices, int[] indices, boolean calcNormals) {
		
		if (calcNormals) {
			
			calcNormals(vertices, indices);
			
			calcTangents(vertices, indices);
			
		}
		
		size = indices.length;
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		
		glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW);
		
	}
	
	private Mesh loadMesh(String fileName) {
		
		String[] splitArray = fileName.split("\\.");
		
		String ext = splitArray[splitArray.length - 1];
		
		if (!ext.equals("obj")) {
			
			System.err.println("Error: Failed to load " + fileName + " mesh. File type " + ext + "not supported.");
			
			new Exception().printStackTrace();
			
			System.exit(1);
			
		}
		
		OBJModel test = new OBJModel("./res/models/" + fileName);
		
		IndexedModel model = test.toIndexedMode();
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		
		for(int i = 0; i < model.getPositions().size(); i ++) {
			
			vertices.add(new Vertex(model.getPositions().get(i),
									model.getTexCoords().get(i),
									model.getNormals().get(i),
									model.getTangents().get(i)));
		}
		
		Vertex[] vertexData = new Vertex[vertices.size()];
		
		vertices.toArray(vertexData);
		
		Integer[] indexData = new Integer[model.getIndices().size()];
		
		model.getIndices().toArray(indexData);
		
		addVertices(vertexData, Util.toIntArray(indexData), false);
		
		return null;
		
	}
	
	public void draw() {
		
		glBindVertexArray(vao);
		
		glEnableVertexAttribArray(0);
		
		glEnableVertexAttribArray(1);
		
		glEnableVertexAttribArray(2);
		
		glEnableVertexAttribArray(3);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
		
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
		
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);
		
		glVertexAttribPointer(3, 3, GL_FLOAT, false, Vertex.SIZE * 4, 32);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ibo);
		
		glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		
		glDisableVertexAttribArray(1);
		
		glDisableVertexAttribArray(2);
		
		glDisableVertexAttribArray(3);
		
	}
	
	//This method calculates VERTEX NORMALS, for smooth shading. Lighting effects will be "weird" on low poly meshes
	
	private void calcNormals(Vertex[] vertices, int[] indices) {
		
		for(int i = 0; i < indices.length; i += 3) {
			
			int i0 = indices[i];
			
			int i1 = indices[i + 1];
			
			int i2 = indices[i + 2];
				
			Vector3D v1 = vertices[i1].getPos().sub(vertices[i0].getPos());
			
			Vector3D v2 = vertices[i2].getPos().sub(vertices[i0].getPos());
			
			Vector3D normal = v1.cross(v2).getNorm();
			
			vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
			
			vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
			
			vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
			
		}
		
		for(int i = 0; i < vertices.length; i++) {
			
			vertices[i].setNormal(vertices[i].getNormal().getNorm());
			
		}
		
	}
	
	private void calcTangents(Vertex[] vertices, int[] indices) {
		
		for(int i = 0; i < indices.length; i += 3) {
			
			int i0 = indices[i];
			
			int i1 = indices[i + 1];
			
			int i2 = indices[i + 2];
				
			Vector3D e1 = vertices[i1].getPos().sub(vertices[i0].getPos());
			
			Vector3D e2 = vertices[i2].getPos().sub(vertices[i0].getPos());
			
			float du1 = vertices[i1].getTexCoord().getX() - vertices[i0].getTexCoord().getX();
			
			float du2 = vertices[i2].getTexCoord().getX() - vertices[i0].getTexCoord().getX();
			
			float dv1 = vertices[i1].getTexCoord().getY() - vertices[i0].getTexCoord().getY();
			
			float dv2 = vertices[i2].getTexCoord().getY() - vertices[i0].getTexCoord().getY();
			
			float det = 1.0f / (du1 * dv2 - du2 * dv1);
			
			Vector3D tangent = new Vector3D(dv2 * e1.getX() - dv1 * e2.getX(), 
											dv2 * e1.getY() - dv1 * e2.getY(), 
											dv2 * e1.getZ() - dv1 * e2.getZ());
			
			tangent.scale(det);
			
			vertices[i0].setTangent(vertices[i0].getTangent().add(tangent));
			
			vertices[i1].setTangent(vertices[i1].getTangent().add(tangent));
			
			vertices[i2].setTangent(vertices[i2].getTangent().add(tangent));
			
		}
		
		for(int i = 0; i < vertices.length; i++) {
			
			vertices[i].setTangent(vertices[i].getTangent().getNorm());
			
		}
		
	}
	
}
