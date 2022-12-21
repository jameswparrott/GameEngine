package main.engine.core;

public class Transform {

    private Transform parent;

    private Matrix4x4 parentMatrix;

    private Vector3D lastPos;

    private Quaternion lastRot;

    private Vector3D lastScale;

    private Vector3D pos;

    private Quaternion rot;

    private Vector3D scale;

    public Transform() {
        
        pos = Vector3D.ZERO;

        rot = Quaternion.IDENTITY;

        scale = new Vector3D(1, 1, 1);
        
        lastPos = Vector3D.ZERO;
        
        lastRot = Quaternion.IDENTITY;
        
        lastScale = Vector3D.ZERO;
        
        parentMatrix = Matrix4x4.IDENTITY;

    }

    public Matrix4x4 getTransformation() {

        Matrix4x4 T = new Matrix4x4().initTranslation(pos.getX(), pos.getY(), pos.getZ());

        Matrix4x4 R = rot.toRotationMatrix();

        Matrix4x4 S = new Matrix4x4().initScale(scale.getX(), scale.getY(), scale.getZ());

        return getParentMatrix().mult(T.mult(R.mult(S)));

    }

    public void update() {
        
        //NOTE: If there are problems with this method, test if last pos is null and init if it is
        lastPos = pos;
        
        lastRot = rot;
        
        lastScale = scale;

    }

    public boolean hasChanged() {

        if (parent != null && parent.hasChanged()) {

            return true;

        }

        return !(pos.equals(lastPos) && rot.equals(lastRot) && scale.equals(lastScale));

    }
    
    public boolean hasRotChanged() {
        
        return !rot.equals(lastRot);
        
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