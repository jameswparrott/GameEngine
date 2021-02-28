package main.game;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import main.engine.components.MeshRenderer;
import main.engine.core.GameObject;
import main.engine.core.Vector2D;
import main.engine.core.Vector3D;
import main.engine.rendering.Material;
import main.engine.rendering.Mesh;
import main.engine.rendering.Texture;
import main.engine.rendering.Vertex;

public class TextItem extends GameObject{
	
	private Mesh textMesh;
	
	private Material textMaterial;
	
	private MeshRenderer textRenderer;
	
	private String text;
	
	private static final float ZPOS = 0.0f;
	
	private static final int VERTICES = 4;

	public TextItem(String text, String fontFile, int numRows, int numCols) {
		
		this.text = text;
		
		Texture fontTexture = new Texture(fontFile);
		
		buildMesh(fontTexture, numRows, numCols);
		
		this.addComponent(textRenderer);
		
	}
	
	private static byte[] toAscii(String text) {
		
		byte[] result = null;
		
		try {
			
			result = text.getBytes("US-ASCII");
			
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
			
		}
		
		return result;
		
	}
	
	private Mesh buildMesh(Texture fontTexture, int numRows, int numCols) {
		
		byte[] chars = toAscii(text);
		
		int numChars = chars.length;
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		
		ArrayList<Integer> indices = new ArrayList<Integer>();
		
		float tileWidth = fontTexture.getWidth() / (float) numCols;
		
		float tileHeight = fontTexture.getHeight() / (float) numRows;
		
		for(int i = 0; i < numChars; i ++) {
			
			byte nextChar = chars[i];
			
			int col = nextChar % numCols;
			
			int row = nextChar / numCols;
			
			vertices.add(new Vertex(new Vector3D( i * tileWidth, 0, ZPOS), new Vector2D(0, 0))); //v0
			
			vertices.add(new Vertex(new Vector3D( i * tileWidth, tileHeight, ZPOS), new Vector2D(0, 0))); //v1
			
			vertices.add(new Vertex(new Vector3D( i * tileWidth + tileWidth, tileHeight, ZPOS), new Vector2D(0, 0))); //v2
			
			vertices.add(new Vertex(new Vector3D( i * tileWidth + tileWidth, 0, ZPOS), new Vector2D(0, 0))); //v3
			
			indices.add(i * VERTICES + 3);
			
			indices.add(i * VERTICES);
			
			indices.add(i * VERTICES + 1);
			
			indices.add(i * VERTICES + 1);
			
			indices.add(i * VERTICES + 2);
			
			indices.add(i * VERTICES + 3);
			
		}
		
		return new Mesh(text);
		
	}
	
}
