package com.tobiasmaneschijn.core;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL45;

import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Sprite {
    private final Transformation transformation;
    /** The texture that stores the image for this sprite */
    private Texture texture;

    /** The width in pixels of this sprite */
    private int width;

    Vector3f color;
    /**
     * The height in pixels of this sprite
     */
    private int height;
    /**
     * id of the sprite vbo
     */
    private int vboId;
    /**
     * id of the sprite vao
     */
    private int vaoId;
    private Shader shader;
    private GameWindow window;


    /**
     * Create a new sprite from a specified image.
     *
     * @param window The window in which the sprite will be displayed
     * @param ref    A reference to the image on which this sprite should be based
     */
    public Sprite(GameWindow window, String ref) {
        this.window = window;
        color = new Vector3f(.5f,0,0);

        try {
            texture = window.getTextureLoader().getTexture(ref);

            width = texture.getImageWidth();
            height = texture.getImageHeight();

            shader = new Shader("sprite", "sprite");
        } catch (IOException e) {
            // a tad abrupt, but our purposes if you can't find a
            // sprite's image you might as well give up.
            System.err.println("Unable to load texture: "+ref);
            System.err.println("Received error: " + e.getMessage());

            System.exit(0);
        }

         transformation = new Transformation();


        shader.setInteger("image", 0, true);
        shader.setMatrix4("projection", window.getProjection(), true);

    }

    /**
     * Get the width of this sprite in pixels
     *
     * @return The width of this sprite in pixels
     */
    public int getWidth() {
        return texture.getImageWidth();
    }

    /**
     * Get the height of this sprite in pixels
     *
     * @return The height of this sprite in pixels
     */
    public int getHeight() {
        return texture.getImageHeight();
    }


    public void initRenderData() {


        shader.setInteger("image", 0, true);
        shader.setMatrix4("projection", window.getProjection(), true);

        float vertices[] = {
                // pos      // tex
                0.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 0.0f,

                0.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 0.0f
        };

        vaoId = glGenVertexArrays();
        vboId = glGenBuffers();

        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices);
        verticesBuffer.flip();

        GL45.glBindBuffer(GL45.GL_ARRAY_BUFFER, vboId);
        GL45.glBufferData(GL45.GL_ARRAY_BUFFER, verticesBuffer, GL45.GL_STATIC_DRAW);

        GL45.glBindVertexArray(vaoId);
        GL45.glEnableVertexAttribArray(0);
        GL45.glVertexAttribPointer(0, 4, GL45.GL_FLOAT, false, 0, 0);

        GL45.glBindBuffer(GL45.GL_ARRAY_BUFFER, 0);
        GL45.glBindVertexArray(0);
    }


    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    /**
     * Draw the sprite at the specified location
     *
     */
    public void draw(Vector2f position, Vector2f size, float rotate) {

        // prepare transformations
        shader.use();
  /*      Matrix4f model = new Matrix4f();
        model = model.translate(new Vector3f());
        model = model.translate(new Vector3f(position, 0.0f));

        model = model.translate(new Vector3f(0.5f * size.x, 0.5f * size.y, 0.0f));
        model = model.rotate(Math.toRadians(rotate), new Vector3f(0.0f, 0.0f, 1.0f));
        model = model.translate(new Vector3f(-0.5f * size.x, -0.5f * size.y, 0.0f));

        model = model.scale(new Vector3f(size, 1.0f));
*/
        Matrix4f worldMatrix =
                transformation.getWorldMatrix(
                        position,
                        new Vector2f(),
                        size.x);

        shader.setMatrix4("worldMatrix", worldMatrix, true);

       // shader.setMatrix4("model", model, true);
        shader.setVector3f("spriteColor", color, true);

        glActiveTexture(GL_TEXTURE0);
        texture.bind();

        GL45.glBindVertexArray(vaoId);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        GL45.glBindVertexArray(0);

    }

    public int getVboId() {
        return vboId;
    }

    public int getVaoId() {
        return vaoId;
    }

    public void destroy() {
        GL45.glDeleteBuffers(vboId);
        GL45.glDeleteVertexArrays(vaoId);
    }
}
