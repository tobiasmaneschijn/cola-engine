package com.tobiasmaneschijn.core;

import com.tobiasmaneschijn.utils.FileUtils;
import org.joml.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.Vector;

public class Shader
{
    public int getProgram() {
        return program;
    }

    private int program = 0;

    public Shader(String vert, String frag){
        int vertShader = 0, fragShader = 0;

        try {
            vertShader = createShader("shaders/"+ vert +".vert", ARBVertexShader.GL_VERTEX_SHADER_ARB);
            fragShader = createShader("shaders/" + frag +".frag", ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
        }
        catch(Exception exc) {
            exc.printStackTrace();
            return;
        }
        finally {
            if(vertShader == 0 || fragShader == 0)
                return;
        }

        program = ARBShaderObjects.glCreateProgramObjectARB();

        if(program == 0)
            return;

        /*
         * if the vertex and fragment shaders setup sucessfully,
         * attach them to the shader program, link the sahder program
         * (into the GL context I suppose), and validate
         */
        ARBShaderObjects.glAttachObjectARB(program, vertShader);
        ARBShaderObjects.glAttachObjectARB(program, fragShader);

        ARBShaderObjects.glLinkProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
            System.err.println(getLogInfo(program));
            return;
        }

        ARBShaderObjects.glValidateProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
            System.err.println(getLogInfo(program));
            return;
        }
    }

    public Shader use() {
        GL45.glUseProgram(program);
        return this;
    }

    private static int createShader(String filename, int shaderType) {
        int shader = 0;
        try {
            shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

            if(shader == 0)
                return 0;

            ARBShaderObjects.glShaderSourceARB(shader, FileUtils.readFileAsString(filename));
            ARBShaderObjects.glCompileShaderARB(shader);

            if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
                throw new RuntimeException("Error creating shader: " + getLogInfo(shader));

            return shader;
        }
        catch(Exception exc) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            throw new RuntimeException("Error creating shader: " + getLogInfo(shader));
        }
    }

    public void setFloat(String name, float value, boolean useShader) {
        if (useShader)
            use();
        GL45.glUniform1f(GL45.glGetUniformLocation(program, name), value);
    }

    public void setInteger(String name, int value, boolean useShader) {
        if (useShader)
            use();
        GL45.glUniform1i(GL45.glGetUniformLocation(program, name), value);
    }

    public void setVector2f(String name, Vector2f value, boolean useShader) {
        if (useShader)
            use();
        GL45.glUniform2f(GL45.glGetUniformLocation(program, name), value.x, value.y);
    }

    public void setVector2i(String name, Vector2i value, boolean useShader) {
        if (useShader)
            use();
        GL45.glUniform2i(GL45.glGetUniformLocation(program, name), value.x, value.y);
    }

    public void setVector3f(String name, Vector3f value, boolean useShader) {
        if (useShader)
            use();
        GL45.glUniform3f(GL45.glGetUniformLocation(program, name), value.x, value.y, value.z);
    }

    public void setVector3i(String name, Vector3i value, boolean useShader) {
        if (useShader)
            use();
        GL45.glUniform3i(GL45.glGetUniformLocation(program, name), value.x, value.y, value.z);
    }

    public void setVector4f(String name, Vector4f value, boolean useShader) {
        if (useShader)
            use();
        GL45.glUniform4f(GL45.glGetUniformLocation(program, name), value.x, value.y, value.z, value.w);
    }

    public void setVector4i(String name, Vector4i value, boolean useShader) {
        if (useShader)
            use();
        GL45.glUniform4i(GL45.glGetUniformLocation(program, name), value.x, value.y, value.z, value.w);
    }

    public void setMatrix4(String name, Matrix4f value, boolean useShader) {
        if (useShader)
            use();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            // Dump the matrix into a float buffer
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            GL45.glUniformMatrix4fv(GL45.glGetUniformLocation(program, name), false, fb);

        }
    }



    private static String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }




}
