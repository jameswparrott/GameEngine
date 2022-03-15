package main.engine.rendering;

import main.engine.core.Vector2D;
import main.engine.core.Vector3D;

public class Vertex {

    // public static final int SIZE = 3;

    public static final int SIZE = 11;

    private Vector3D pos;

    private Vector2D texCoord;

    private Vector3D normal;

    private Vector3D tangent;

    public Vertex(Vector3D pos) {

//		MAJOR BUG: This used be the entire constructor!
//		
//		this.pos = pos;
//		
//		this.texCoord = new Vector2D(0, 0);
//		
//		No normals initialized!!!

        // fixed to initialize normals

        this(pos, new Vector2D(0, 0));

    }

    public Vertex(Vector3D pos, Vector2D texCoord) {

        this(pos, texCoord, new Vector3D(0, 0, 0));

    }

    public Vertex(Vector3D pos, Vector2D texCoord, Vector3D normal) {

        this(pos, texCoord, normal, new Vector3D(0, 0, 0));

    }

    public Vertex(Vector3D pos, Vector2D texCoord, Vector3D normal, Vector3D tangent) {

        this.pos = pos;

        this.texCoord = texCoord;

        this.normal = normal;

        this.tangent = tangent;

    }

    public Vector3D getPos() {

        return pos;
    }

    public void setPos(Vector3D pos) {

        this.pos = pos;

    }

    public Vector2D getTexCoord() {

        return texCoord;

    }

    public void setTextCoord(Vector2D texCoord) {

        this.texCoord = texCoord;

    }

    public Vector3D getNormal() {

        return normal;

    }

    public void setNormal(Vector3D normal) {

        this.normal = normal;

    }

    public Vector3D getTangent() {

        return tangent;

    }

    public void setTangent(Vector3D tangent) {

        this.tangent = tangent;

    }

}
