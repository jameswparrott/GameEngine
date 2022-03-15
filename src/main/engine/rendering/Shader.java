package main.engine.rendering;

import static org.lwjgl.opengl.GL20.*;

import static org.lwjgl.opengl.GL32.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import main.engine.core.Matrix4x4;
import main.engine.core.Transform;
import main.engine.core.Util;
import main.engine.core.Vector3D;

public class Shader {

    private int program;

    private HashMap<String, Integer> uniforms;

    public Shader() {

        program = glCreateProgram();

        uniforms = new HashMap<String, Integer>();

        if (program == 0) {

            System.err.println("Shader creation failed, invalid memory location");

            System.exit(1);

        }

    }

    private static String loadShader(String fileName) {

        StringBuilder source = new StringBuilder();

        BufferedReader reader = null;

        try {

            reader = new BufferedReader(new FileReader("./res/shaders/" + fileName));

            String line;

            while ((line = reader.readLine()) != null) {

                source.append(line).append("\n");

            }

            reader.close();

        }

        catch (Exception e) {

            e.printStackTrace();

            System.exit(0);

        }

        return source.toString();

    }

    public void bind() {

        glUseProgram(program);

    }

    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {

    }

    public void addUniform(String uniform) {

        int uniformlocation = glGetUniformLocation(program, uniform);

        if (uniformlocation == 0xFFFFFFFF) {

            System.err.println("Error: could not find uniform: " + uniform);

            new Exception().printStackTrace();

            System.exit(1);

        }

        uniforms.put(uniform, uniformlocation);

    }

    public void addVertexShaderFromFile(String text) {

        addProgram(loadShader(text), GL_VERTEX_SHADER);

    }

    public void addGeometryShaderFromFile(String text) {

        addProgram(loadShader(text), GL_GEOMETRY_SHADER);

    }

    public void addFragmentShaderFromFile(String text) {

        addProgram(loadShader(text), GL_FRAGMENT_SHADER);

    }

    public void addVertexShader(String text) {

        addProgram(text, GL_VERTEX_SHADER);

    }

    public void addGeometryShader(String text) {

        addProgram(text, GL_GEOMETRY_SHADER);

    }

    public void addFragmentShader(String text) {

        addProgram(text, GL_FRAGMENT_SHADER);

    }

    public void compileShader() {

        glLinkProgram(program);

        if (glGetProgrami(program, GL_LINK_STATUS) == 0) {

            System.err.println(glGetProgramInfoLog(program, 1024));

            System.exit(1);

        }

        glValidateProgram(program);

        if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {

            System.err.println(glGetProgramInfoLog(program, 1024));

            System.exit(1);

        }

    }

    private void addProgram(String text, int type) {

        int shader = glCreateShader(type);

        if (shader == 0) {

            System.err.println("Shader creation failed, invalid memory location adding shader");

            System.exit(1);

        }

        glShaderSource(shader, text);

        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {

            System.err.println(glGetShaderInfoLog(shader, 1024));

            System.exit(1);

        }

        glAttachShader(program, shader);

    }

    public void setUniformi(String uniformName, int value) {

        glUniform1i(uniforms.get(uniformName), value);

    }

    public void setUniformf(String uniformName, float value) {

        glUniform1f(uniforms.get(uniformName), value);

    }

    public void setUniform(String uniformName, Vector3D value) {

        glUniform3f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());

    }

    public void setUniform(String uniformName, Matrix4x4 value) {

        // CAUTION: the boolean value here may not be correct

        glUniformMatrix4fv(uniforms.get(uniformName), true, Util.createFlippedBuffer(value));

    }

}
