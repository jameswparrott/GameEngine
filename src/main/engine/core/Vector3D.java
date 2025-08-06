package main.engine.core;

import org.lwjgl.system.CallbackI;

import java.util.Vector;

public class Vector3D {

    private float x;

    private float y;

    private float z;
    
    public static final Vector3D ZERO       = new Vector3D( 0,  0,  0);
    public static final Vector3D FORWARD    = new Vector3D( 0,  0,  1);
    public static final Vector3D BACKWARD   = new Vector3D( 0,  0, -1);
    public static final Vector3D RIGHT      = new Vector3D( 1,  0,  0);
    public static final Vector3D LEFT       = new Vector3D(-1,  0,  0);
    public static final Vector3D UP         = new Vector3D( 0,  1,  0);
    public static final Vector3D DOWN       = new Vector3D( 0, -1,  0);

    public Vector3D(float x, float y, float z) {

        this.x = x;

        this.y = y;

        this.z = z;

    }

    public void set(float x, float y, float z) {

        this.x = x;

        this.y = y;

        this.z = z;

    }

    public float length() {

        return (float) Math.sqrt(x * x + y * y + z * z);

    }

    public float lengthSq() {

        return x * x + y * y + z * z;

    }

    public float dot(Vector3D v) {

        return x * v.getX() + y * v.getY() + z * v.getZ();

    }

    public Vector3D abs() {

        x = Math.abs(x);

        y = Math.abs(y);

        z = Math.abs(z);

        return this;

    }

    public Vector3D getAbs() {

        return new Vector3D(Math.abs(x), Math.abs(y), Math.abs(z));

    }

    public float max() {

        return Math.max(x, Math.max(y, z));

    }

    public Vector3D getMax(Vector3D v) {

        return new Vector3D(Math.max(x, v.getX()), Math.max(y, v.getY()), Math.max(z, v.getZ()));

    }

    public float min() {

        return Math.min(x, Math.min(y, z));

    }

    public Vector3D getMin(Vector3D v) {

        return new Vector3D(Math.min(x, v.getX()), Math.min(y, v.getY()), Math.min(z, v.getZ()));

    }

    public Vector3D lerp(Vector3D v, float lerpFactor) {

        // x - (y - x) * lerp

        return this.add((v.sub(this)).getScaled(lerpFactor));

    }

    public Vector3D cross(Vector3D v) {

        return new Vector3D(y * v.getZ() - z * v.getY(), z * v.getX() - x * v.getZ(), x * v.getY() - y * v.getX());

    }

    public Vector3D calcNormal(Vector3D b, Vector3D c) {

        /* CCW orientation is implied
         *          A = this
         *        ***
         *      *****
         *    *******
         *  B*******C
         */
        return b.sub(this).cross(c.sub(this));

    }

    public Vector3D normalize() {

        float length = length();

        if (length != 0) {

            x /= length;

            y /= length;

            z /= length;

        }

        return this;

    }

    public Vector3D getNormal() {

        float length = length();

        if (length != 0) {

            return new Vector3D(x / length, y / length, z / length);

        }
        
        return new Vector3D(x, y, z);

    }

    public Vector3D rotate(Vector3D axis, float angle) {

        return this.rotate(new Quaternion(axis, angle));

    }

    public Vector3D rotate(Quaternion rotation) {

        Quaternion conjugate = rotation.conjugate();

        Quaternion q = rotation.mult(this).mult(conjugate);

        this.x = q.getX();

        this.y = q.getY();

        this.z = q.getZ();

        return this;

    }

    public Vector3D getRotated(Vector3D axis, float angle) {

        return this.getRotated(new Quaternion(axis, angle));

    }

    public Vector3D getRotated(Quaternion rotation) {

        Quaternion conjugate = rotation.conjugate();

        Quaternion q = rotation.mult(this).mult(conjugate);

        return new Vector3D(q.getX(), q.getY(), q.getZ());

    }

    public Vector3D add(Vector3D v) {

        return new Vector3D(x + v.getX(), y + v.getY(), z + v.getZ());

    }

    public Vector3D sub(Vector3D v) {

        return new Vector3D(x - v.getX(), y - v.getY(), z - v.getZ());

    }

    public Vector3D mul(Vector3D v) {

        return new Vector3D(x * v.getX(), y * v.getY(), z * v.getZ());

    }

    public Vector3D scale(float c) {

        x *= c;

        y *= c;

        z *= c;

        return this;

    }

    public Vector3D getScaled(float c) {

        return new Vector3D(x * c, y * c, z * c);

    }

    public Vector3D clamp(Vector3D min, Vector3D max){

        return new Vector3D(Math.max(min.getX(), Math.min(x, max.getX())),
                            Math.max(min.getY(), Math.min(y, max.getY())),
                            Math.max(min.getZ(), Math.min(z, max.getZ())));

    }

    public Vector3D compAdd(float c) {

        return new Vector3D(x + c, y + c, z + c);

    }

    public String toString() {

        return "[" + x + "," + y + "," + z + "]";

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

    public Vector2D getXY() {

        return new Vector2D(x, y);

    }

    public Vector2D getXZ() {

        return new Vector2D(x, z);

    }

    public Vector2D getYX() {

        return new Vector2D(y, x);

    }

    public Vector2D getYZ() {

        return new Vector2D(y, z);

    }

    public Vector2D getZX() {

        return new Vector2D(z, x);

    }

    public Vector2D getZY() {

        return new Vector2D(z, y);

    }

    public void set(Vector3D v) {

        this.x = v.getX();

        this.y = v.getY();

        this.z = v.getZ();

    }

    public boolean epsilonEquals(Vector3D v){

        return epsilonEquals(v, 1e-6f);

    }

    public boolean epsilonEquals(Vector3D v, float epsilon) {

        return Math.abs(x - v.getX()) < epsilon &&
               Math.abs(y - v.getY()) < epsilon &&
               Math.abs(z - v.getZ()) < epsilon;

    }

}