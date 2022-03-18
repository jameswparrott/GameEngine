package main.engine.core;

public class Quaternion {

    private float x;

    private float y;

    private float z;
    
    private float w;

    public static final Quaternion ZERO     = new Quaternion( 0, 0, 0, 0);
    public static final Quaternion I        = new Quaternion( 1, 0, 0, 0);
    public static final Quaternion J        = new Quaternion( 0, 1, 0, 0);
    public static final Quaternion K        = new Quaternion( 0, 0, 1, 0);
    public static final Quaternion IDENTITY = new Quaternion( 0, 0, 0, 1);

    public Quaternion(float x, float y, float z, float w) {

        this.x = x;

        this.y = y;

        this.z = z;

        this.w = w;

    }

    public Quaternion(Vector3D axis, float angle) {

        float sinHalfAngle = (float) Math.sin(angle / 2);

        float cosHalfAngle = (float) Math.cos(angle / 2);

        this.x = axis.getX() * sinHalfAngle;

        this.y = axis.getY() * sinHalfAngle;

        this.z = axis.getZ() * sinHalfAngle;

        this.w = cosHalfAngle;

    }

    public float length() {

        return (float) Math.sqrt(w * w + x * x + y * y + z * z);

    }

    public Quaternion normalize() {

        float length = length();

        if (length != 0) {

            x /= length;

            y /= length;

            z /= length;

            w /= length;
            
        }

        return this;

    }

    public Quaternion getNorm() {

        float length = length();

        if (length != 0) {

            return new Quaternion(x / length, y / length, z / length, w / length);

        }
        
        return new Quaternion(x, y, z, w);
        
    }

    public Quaternion conjugate() {

        return new Quaternion(-x, -y, -z, w);

    }

    public Quaternion mult(Quaternion q) {

        float x1 = x * q.getW() + w * q.getX() - z * q.getY() + y * q.getZ();

        float y1 = y * q.getW() + z * q.getX() + w * q.getY() - x * q.getZ();

        float z1 = z * q.getW() - y * q.getX() + x * q.getY() + w * q.getZ();
        
        float w1 = w * q.getW() - x * q.getX() - y * q.getY() - z * q.getZ();

        return new Quaternion(x1, y1, z1, w1);

    }

    public Quaternion mult(Vector3D v) {

        float x1 = w * v.getX() + y * v.getZ() - z * v.getY();

        float y1 = w * v.getY() + z * v.getX() - x * v.getZ();

        float z1 = w * v.getZ() + x * v.getY() - y * v.getX();
        
        float w1 = -x * v.getX() - y * v.getY() - z * v.getZ();

        return new Quaternion(x1, y1, z1, w1);

    }

    public Matrix4x4 toRotationMatrix() {

        Vector3D forward = new Vector3D(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));

        Vector3D up = new Vector3D(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));

        Vector3D right = new Vector3D(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

        return new Matrix4x4().initRotation(forward, up, right);

    }

    public Vector3D getForward() {

        return Vector3D.FORWARD.getRotated(this);

    }

    public Vector3D getBack() {

        return Vector3D.BACKWARD.getRotated(this);

    }

    public Vector3D getUp() {

        return Vector3D.UP.getRotated(this);

    }

    public Vector3D getDown() {

        return Vector3D.DOWN.getRotated(this);

    }

    public Vector3D getRight() {

        return Vector3D.RIGHT.getRotated(this);

    }

    public Vector3D getLeft() {

        return Vector3D.LEFT.getRotated(this);

    }

    public float getW() {

        return w;

    }

    public void setW(float w) {

        this.w = w;

    }

    public float getX() {

        return x;

    }

    public void setX(float x) {

        this.x = x;

    }

    public float getY() {

        return y;

    }

    public void setY(float y) {

        this.y = y;

    }

    public float getZ() {

        return z;

    }

    public void setZ(float z) {

        this.z = z;

    }

    public boolean equals(Quaternion q) {

        return (x == q.getX()) && (y == q.getY()) && (z == q.getZ()) && (w == q.getW());

    }

}