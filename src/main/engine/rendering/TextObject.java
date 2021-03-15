package main.engine.rendering;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import main.engine.components.MeshRenderer;
import main.engine.core.GameObject;
import main.engine.core.Util;
import main.engine.core.Vector2D;
import main.engine.core.Vector3D;

public class TextObject extends GameObject{
	
	private Mesh textMesh;
	
	private Material textMaterial;
	
	private MeshRenderer textRenderer;
	
	private Texture fontTexture;
	
	private String text;
	
	private final int numRows, numCols;
	
	private static final float ZPOS = 0.0f;
	
	private static final int VERTICES = 4;

	public TextObject(String text, String fontFile, int numRows, int numCols) {
		
		this.text = text;
		
		fontTexture = new Texture(fontFile);
		
		this.numRows = numRows;
		
		this.numCols = numCols;
		
		this.textMesh = buildMesh(text, fontTexture, numRows, numCols);
		
		this.textMaterial = new Material(fontTexture, 1, 1);
		
		textRenderer = new MeshRenderer(textMesh, textMaterial);
		
		addComponent(textRenderer);
		
	}
	
	private static byte[] toAscii(String text) {
		
		byte[] result = null;
		
		try {
			
			result = text.getBytes("US-ASCII");
			
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
			
		}
		
//		for (int i = 0; i < result.length; i ++) {
//		
//			System.out.println(result[i]);
//		
//		}
		
		return result;
		
	}
	
	private static Mesh buildMesh(String text, Texture fontTexture, int numRows, int numCols) {
		
		byte[] chars = toAscii(text);
		
		int numChars = chars.length;
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		
		ArrayList<Integer> indices = new ArrayList<Integer>();
		
		float tileWidth = fontTexture.getWidth() / (float) (numCols);
		
		float tileHeight = fontTexture.getHeight() / (float) (numRows);
		
		for(int i = 0; i < numChars; i ++) {
			
			byte nextChar = chars[i];
			
			int col = nextChar % numCols;
			
			int row = numRows - 1 - nextChar / numCols;
			
			if (row > numRows || col > numCols) {
				row = 0;
				col = 0;
			}
			
			float texWidth = 1.0f / (float) numCols;
			
			float texHeight = 1.0f / (float) numRows;
			
			vertices.add(new Vertex(new Vector3D( i * tileWidth, 0, ZPOS), 
									new Vector2D(col * texWidth, row * texHeight))); //v0
			
			vertices.add(new Vertex(new Vector3D( i * tileWidth, tileHeight, ZPOS), 
									new Vector2D(col * texWidth, (row + 1) * texHeight))); //v1
			
			vertices.add(new Vertex(new Vector3D( (i + 1) * tileWidth, tileHeight, ZPOS), 
									new Vector2D((col + 1) * texWidth, (row + 1) * texHeight))); //v2
			
			vertices.add(new Vertex(new Vector3D( (i + 1) * tileWidth, 0, ZPOS), 
									new Vector2D((col + 1) * texWidth, row * texHeight))); //v3
			
			indices.add(i * VERTICES + 3);
			
			indices.add(i * VERTICES);
			
			indices.add(i * VERTICES + 1);
			
			indices.add(i * VERTICES + 1);
			
			indices.add(i * VERTICES + 2);
			
			indices.add(i * VERTICES + 3);
			
		}
		
		Vertex[] verticesArray = new Vertex[vertices.size()];
		
		Integer[] tempIndices = new Integer[indices.size()];
		
		int[] indicesArray = new int[indices.size()];
		
		vertices.toArray(verticesArray);
		
		indices.toArray(tempIndices);
		
		indicesArray = Util.toIntArray(tempIndices);
		
		Mesh textMesh = new Mesh(verticesArray, indicesArray, false);
		
		return textMesh;
		
	}
	
	public String getText() {
		
		return this.text;
		
	}
	
	public void setText(String text) {
		
		this.text = text;
		
		this.removeComponent(textRenderer);
		
		this.textMesh = buildMesh(text, fontTexture, numRows, numCols);
		
		textRenderer = new MeshRenderer(textMesh, textMaterial);
		
		this.addComponent(textRenderer);
		
	}
	
}
