package main.engine.components;

import main.engine.core.CoreEngine;
import main.engine.core.Matrix4x4;
import main.engine.core.Vector3D;
import main.engine.rendering.RenderingEngine;

public class Camera extends GameComponent{
	
	private Matrix4x4 projection;
	
	public Camera() {
		
		this((float) Math.toRadians(70.0f), (float) CoreEngine.getWidth() / (float) CoreEngine.getHeight(), 0.01f, 1000.0f);
		
	}
	
	public Camera(float fov, float aspect, float zNear, float zFar) {
		
		this.projection = new Matrix4x4().initPerspective(fov, aspect, zNear, zFar);
		
	}
	
	public Matrix4x4 getViewProjection() {
		
		Vector3D cameraPos = getTransform().getTransformedPos().getScaled(-1);
		
		Matrix4x4 cameraRotation = getTransform().getTransformedRot().conjugate().toRotationMatrix();
		
		Matrix4x4 cameraTranslation = new Matrix4x4().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());

		return projection.mult(cameraRotation.mult(cameraTranslation));
		
	}
	
	@Override
	public void addToRenderingEngine(RenderingEngine renderingEngine) {
		
		renderingEngine.addCamera(this);
		
	}
	
	public void move(Vector3D dir, float amount) {
		
		getTransform().setPos(getTransform().getPos().add(dir.getScaled(amount)));
		
	}
	
}
