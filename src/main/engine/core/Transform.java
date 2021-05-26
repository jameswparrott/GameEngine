package main.engine.core;

public class Transform {
	
	private Transform parent;
	
	private Matrix4x4 parentMatrix;
	
	private Vector3D pPos;
	
	private Quaternion pRot;
	
	private Vector3D pScale;
	
	private Vector3D pos;
	
	private Quaternion rot;
	
	private Vector3D scale;
	
	public Transform() {
		
		pos = new Vector3D(0, 0, 0);
		
		rot = new Quaternion(0, 0, 0, 1);
		
		scale = new Vector3D(1, 1, 1);
		
		parentMatrix = new Matrix4x4().initI();
		
		update();
		
	}
	
	public Matrix4x4 getTransformation() {
		
		Matrix4x4 T = new Matrix4x4().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		
		Matrix4x4 R = rot.toRotationMatrix();
		
		Matrix4x4 S = new Matrix4x4().initScale(scale.getX(), scale.getY(), scale.getZ());
		
		return getParentMatrix().mult(T.mult(R.mult(S)));
		
	}
	
	public void update() {
		
		if(pPos != null) {
			
			pPos = pos;
			
			pRot = rot;
			
			pScale = scale;
			
		}
		
		else {
			
			//May be source of bug if not initialized properly
			
			pPos = new Vector3D(0, 0, 0);
			
			pRot = new Quaternion(0, 0, 0, 0);
			
			pScale = new Vector3D(1, 1, 1).scale(-1);
			
			//pPos = pos.add(new Vector3D(1, 1, 1));
			
		}
		
	}
	
	public boolean hasChanged() {
		
		if(parent != null && parent.hasChanged()) {
			
			return true;
			
		}
		
		return !(pos.equals(pPos) && rot.equals(pRot) && scale.equals(pScale));
		
	}
	
	public void rotate(Vector3D axis, float angle) {
		
		rot = new Quaternion(axis, angle).mult(rot).getNorm();
			
	}
	
	private Matrix4x4 getParentMatrix() {
		
		if (parent != null && parent.hasChanged()) {
			
			parentMatrix = parent.getTransformation();
			
		}
		
		return parentMatrix;
			
	}
	
	public void setParent(Transform parent) {
		
		this.parent = parent;
		
	}
	
	public Transform getParent() {
		
		return parent;
		
	}
	
	public Vector3D getTransformedPos() {
		
		return getParentMatrix().transform(pos);
		
	}
	
	public Quaternion getTransformedRot() {
		
		Quaternion parentRotation = new Quaternion(0, 0, 0, 1);
		
		if (parent != null) {
			
			parentRotation = parent.getTransformedRot();
			
		}
		
		return parentRotation.mult(rot);
		
	}
	
	public void setPos(float x, float y, float z) {
		
		this.pos = new Vector3D(x, y, z);
		
	}
	
	public void setPos(Vector3D v) {
		
		this.pos = v;
		
	}
	
	public Vector3D getPos() {
		
		return pos;
		
	}

	public Vector3D getScale() {
	
		return scale;
	
	}
	
	public void setScale(float x, float y, float z) {
		
		this.scale = new Vector3D(x, y, z);
		
	}

	public void setScale(Vector3D scale) {
	
		this.scale = scale;
	
	}
	
	public void setRot(Quaternion rotation) {
		
		this.rot = rotation;
	
	}

	public Quaternion getRot() {
	
		return rot;
	
	}
	
}