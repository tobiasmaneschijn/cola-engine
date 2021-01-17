package com.tobiasmaneschijn.core;

import org.lwjgl.opengl.GL45;

import java.io.IOException;

public class Sprite {
    /** The texture that stores the image for this sprite */
    private Texture texture;

    /** The width in pixels of this sprite */
    private int width;

    /** The height in pixels of this sprite */
    private int height;

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
        // store the current model matrix
        GL45.glPushMatrix();

        // bind to the appropriate texture for this sprite
        texture.bind();

        // translate to the right location and prepare to draw
        GL45.glTranslatef(x, y, 0);
        GL45.glColor3f(1,1,1);

        // draw a quad textured to match the sprite
        GL45.glBegin(GL45.GL_QUADS);
        {
            GL45.glTexCoord2f(0, 0);
            GL45.glVertex2f(0, 0);
            GL45.glTexCoord2f(0, texture.getHeight());
            GL45.glVertex2f(0, height);
            GL45.glTexCoord2f(texture.getWidth(), texture.getHeight());
            GL45.glVertex2f(width,height);
            GL45.glTexCoord2f(texture.getWidth(), 0);
            GL45.glVertex2f(width,0);
        }
        GL45.glEnd();

        // restore the model view matrix to prevent contamination
        GL45.glPopMatrix();
    }

}
