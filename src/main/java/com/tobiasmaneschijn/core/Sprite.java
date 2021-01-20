package com.tobiasmaneschijn.core;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL45;

import java.io.IOException;
import java.nio.FloatBuffer;

public class Sprite {
    /** The texture that stores the image for this sprite */
    private Texture texture;

    /** The width in pixels of this sprite */
    private int width;

    /** The height in pixels of this sprite */
    private int height;


    /** id of the sprite vbo */
    private int vboId;

    /** id of the sprite vao */
    private int vaoId;
    /**
     * Create a new sprite from a specified image.
     *
     * @param window The window in which the sprite will be displayed
     * @param ref A reference to the image on which this sprite should be based
     */
    public Sprite(GameWindow window,String ref) {
        try {
            texture = window.getTextureLoader().getTexture(ref);

            width = texture.getImageWidth();
            height = texture.getImageHeight();
        } catch (IOException e) {
            // a tad abrupt, but our purposes if you can't find a
            // sprite's image you might as well give up.
            System.err.println("Unable to load texture: "+ref);
            System.exit(0);
        }
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

    /**
     * Draw the sprite at the specified location
     *
     * @param x The x location at which to draw this sprite
     * @param y The y location at which to draw this sprite
     */
    public void draw(int x, int y) {

        float[] vertices = {
                // Left bottom triangle
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                // Right top triangle
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f
        };

        // Sending data to OpenGL requires the usage of (flipped) byte buffers
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices);
        verticesBuffer.flip();

        int vertexCount = 6;

        // Create a new Vertex Array Object in memory and select it (bind)
// A VAO can have up to 16 attributes (VBO's) assigned to it by default
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);
        GL45.glEnableVertexAttribArray(0);

// Create a new Vertex Buffer Object in memory and select it (bind)
// A VBO is a collection of Vectors which in this case resemble the location of each vertex.
        vboId = GL45.glGenBuffers();
        GL45.glBindBuffer(GL45.GL_ARRAY_BUFFER, vboId);
        GL45.glBufferData(GL45.GL_ARRAY_BUFFER, verticesBuffer, GL45.GL_STATIC_DRAW);
// Put the VBO in the attributes list at index 0
        GL45.glVertexAttribPointer(0, 3, GL45.GL_FLOAT, false, 0, 0);

        // Draw the vertices
        GL45.glDrawArrays(GL45.GL_TRIANGLES, 0, vertexCount);

// Deselect (bind to 0) the VBO
        GL45.glBindBuffer(GL45.GL_ARRAY_BUFFER, 0);

// Deselect (bind to 0) the VAO
        GL45.glBindVertexArray(0);

    }

    public int getVboId() {
        return vboId;
    }

    public int getVaoId() {
        return vaoId;
    }

}
