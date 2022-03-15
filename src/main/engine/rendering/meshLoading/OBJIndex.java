package main.engine.rendering.meshLoading;

public class OBJIndex {

    public int vertexIndex;

    public int texCoordIndex;

    public int normalIndex;

    @Override
    public boolean equals(Object obj) {

        OBJIndex index = (OBJIndex) obj;

        return  vertexIndex == index.vertexIndex &&

                texCoordIndex == index.texCoordIndex &&

                normalIndex == index.normalIndex;

    }

    @Override
    public int hashCode() {

        // Try 17 & 31

        final int BASE = 5;

        final int MULT = 13;

        int result = BASE;

        result = result * MULT + vertexIndex;

        result = result * MULT + texCoordIndex;

        result = result * MULT + normalIndex;

        return result;

    }

}
