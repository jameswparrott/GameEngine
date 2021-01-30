package main.game;

import main.engine.components.MeshRenderer;
import main.engine.core.GameObject;
import main.engine.core.Time;
import main.engine.core.Vector2D;
import main.engine.core.Vector3D;
import main.engine.rendering.Material;
import main.engine.rendering.Mesh;
import main.engine.rendering.meshLoading.PrimitiveModel3D;
import main.engine.rendering.meshLoading.PrimitiveModel3D.Primitive3D;

public class Door extends GameObject {

	private static Mesh mesh;
	
	private float[] texCoords = new float[4];
	
	private boolean rotated;
	
	private boolean isOpening;
	
	private boolean shouldOpen;
	
	private double openStart;
	
	private double openTime;
	
	private double closeStart;
	
	private double closeTime;
	
	private double TIME_TO_OPEN = 1.0f;
	
	private double CLOSE_DELAY = 1.0f;
	
	private Vector3D closedPos;
	
	private Vector3D openPos;
	
	public Door(Material material, boolean rotated) {
		
		texCoords[0] = 0.5f;
		
		texCoords[1] = 0.625f;
		
		texCoords[2] = 0.375f;
		
		texCoords[3] = 0.5f;
		
		if (mesh == null) {
			
			PrimitiveModel3D primitive = new PrimitiveModel3D(Primitive3D.box, 1f, 1f, 0.1f, genDoorTexCoords(texCoords));
			
			mesh = primitive.getMesh();
			
		}
		
		MeshRenderer renderer = new MeshRenderer(mesh, material);
		
		addComponent(renderer);
		
		this.rotated = rotated;
		
		isOpening = false;
		
	}
	
	private float[] genDoorTexCoords(float[] texCoords) {
		
		float[] result = new float[24];
		
		//Front face
		
		result[0] = texCoords[0];
		
		result[1] = texCoords[1];
		
		result[2] = texCoords[2];
		
		result[3] = texCoords[3];
		
		//Right face
		
		result[4] = texCoords[0];
		
		result[5] = texCoords[0] + 0.1f * (texCoords[1] - texCoords[0]);
		
		result[6] = texCoords[2];
		
		result[7] = texCoords[3];
		
		//Back face
		
		result[8] = texCoords[1];
		
		result[9] = texCoords[0];
		
		result[10] = texCoords[2];
		
		result[11] = texCoords[3];
		
		//Left face
		
		result[12] = texCoords[0] + 0.1f * (texCoords[1] - texCoords[0]);
		
		result[13] = texCoords[0];
		
		result[14] = texCoords[2];
		
		result[15] = texCoords[3];
		
		//Top face
		
		result[16] = texCoords[0];
		
		result[17] = texCoords[1];
		
		result[18] = texCoords[2];
		
		result[19] = texCoords[2] + 0.1f * (texCoords[3] - texCoords[2]);
		
		//Bottom face
		
		result[20] = texCoords[0];
		
		result[21] = texCoords[1];
		
		result[22] = texCoords[2] + 0.1f * (texCoords[3] - texCoords[2]);
		
		result[23] = texCoords[2];
		
		return result;
		
	}
	
	public void open() {
		
		if (isOpening) {
			
			return;
			
		}
		
		if(closedPos == null) {
			
			closedPos = getTransform().getPos();
			
		}
		
		shouldOpen = true;
		
	}
	
	private Vector3D lerp(Vector3D start, Vector3D end, float factor) {
		
		return start.add(end.sub(start).getScaled(factor));
		
	}
	
	@Override
	public void update(float delta) {
		
		double time = Time.getTime();
		
		if(shouldOpen) {
			
			openStart = time;
			
			openTime = openStart + TIME_TO_OPEN;
			
			closeStart = openTime + CLOSE_DELAY;
			
			closeTime = closeStart + TIME_TO_OPEN;
			
			shouldOpen = false;
			
			isOpening = true;
			
		}
		
		if(isOpening) {
			
			if(time < openTime) {
				
				//getTransform().setPos(closedPos.lerp(openPos, (float) (openTime - delta) /(float) TIME_TO_OPEN));
				
				getTransform().setPos(lerp(closedPos, openPos, (float) ((time - openStart) / TIME_TO_OPEN)));
				
			} else if(time < closeStart) {
				
				getTransform().setPos(openPos);
				
			} else if(time < closeTime) {
				
				getTransform().setPos(lerp(openPos, closedPos, (float) ((time - closeStart) / TIME_TO_OPEN)));
				
			} else {
				
				getTransform().setPos(closedPos);
				
				isOpening = false;
				
			}
			
		}	
		
	}
	
	public void setClosedPos(Vector3D v) {
		
		this.closedPos = v;
		
	}
	
	public void setOpenedPos(Vector3D v) {
		
		this.openPos = v;
		
	}
	
	public Vector2D getOrientation() {
		
		if (rotated) {
			
			return new Vector2D(0.1f, 1f);
			
		}
		
		return new Vector2D(1f, 0.1f);
		
	}
	
	public boolean getRotated() {
		
		return rotated;
		
	}
	
}
