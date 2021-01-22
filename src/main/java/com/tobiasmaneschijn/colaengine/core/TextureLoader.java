package com.tobiasmaneschijn.colaengine.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL45;


public class TextureLoader {
    /** The table of textures that have been loaded in this loader */
    private HashMap table = new HashMap();

    /** The colour model including alpha for the GL image */
    private ColorModel glAlphaColorModel;

    /** The colour model for the GL image */
    private ColorModel glColorModel;

    /**
     * Create a new texture loader based on the game panel
     *
     */
    public TextureLoader() {
        glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
                new int[] {8,8,8,8},
                true,
                false,
                ComponentColorModel.TRANSLUCENT,
                DataBuffer.TYPE_BYTE);

        glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
                new int[] {8,8,8,0},
                false,
                false,
                ComponentColorModel.OPAQUE,
                DataBuffer.TYPE_BYTE);
    }

    /**
     * Create a new texture ID
     *
     * @return A new texture ID
     */
    private int createTextureID()
    {
        IntBuffer tmp = createIntBuffer(1);
        GL45.glGenTextures(tmp);
        return tmp.get(0);
    }

    /**
     * Load a texture
     *
     * @param resourceName The location of the resource to load
     * @return The loaded texture
     * @throws IOException Indicates a failure to access the resource
     */
    public Texture getTexture(String resourceName) throws IOException {
        Texture tex = (Texture) table.get(resourceName);

        if (tex != null) {
            return tex;
        }

        tex = getTexture(resourceName,
                GL45.GL_TEXTURE_2D, // target
                GL45.GL_RGBA,     // dst pixel format
                GL45.GL_LINEAR, // min filter (unused)
                GL45.GL_LINEAR);

        table.put(resourceName,tex);

        return tex;
    }

    /**
     * Load a texture into OpenGL from a image reference on
     * disk.
     *
     * @param resourceName The location of the resource to load
     * @param target The GL target to load the texture against
     * @param dstPixelFormat The pixel format of the screen
     * @param minFilter The minimising filter
     * @param magFilter The magnification filter
     * @return The loaded texture
     * @throws IOException Indicates a failure to access the resource
     */
    public Texture getTexture(String resourceName,
                              int target,
                              int dstPixelFormat,
                              int minFilter,
                              int magFilter) throws IOException
    {
        int srcPixelFormat = 0;

        // create the texture ID for this texture
        int textureID = createTextureID();
        Texture texture = new Texture(target,textureID);

        // bind this texture
        GL45.glBindTexture(target, textureID);

        BufferedImage bufferedImage = loadImage(resourceName);
        texture.setWidth(bufferedImage.getWidth());
        texture.setHeight(bufferedImage.getHeight());

        if (bufferedImage.getColorModel().hasAlpha()) {
            srcPixelFormat = GL45.GL_RGBA;
        } else {
            srcPixelFormat = GL45.GL_RGB;
        }

        // convert that image into a byte buffer of texture data
        ByteBuffer textureBuffer = convertImageData(bufferedImage,texture);

        if (target == GL45.GL_TEXTURE_2D)
        {
            GL45.glTexParameteri(target, GL45.GL_TEXTURE_MIN_FILTER, minFilter);
            GL45.glTexParameteri(target, GL45.GL_TEXTURE_MAG_FILTER, magFilter);
        }

        // produce a texture from the byte buffer
        GL45.glTexImage2D(target,
                0,
                dstPixelFormat,
                get2Fold(bufferedImage.getWidth()),
                get2Fold(bufferedImage.getHeight()),
                0,
                srcPixelFormat,
                GL45.GL_UNSIGNED_BYTE,
                textureBuffer );

        return texture;
    }

    /**
     * Get the closest greater power of 2 to the fold number
     *
     * @param fold The target number
     * @return The power of 2
     */
    private int get2Fold(int fold) {
        int ret = 2;
        while (ret < fold) {
            ret *= 2;
        }
        return ret;
    }

    /**
     * Convert the buffered image to a texture
     *
     * @param bufferedImage The image to convert to a texture
     * @param texture The texture to store the data into
     * @return A buffer containing the data
     */
    private ByteBuffer convertImageData(BufferedImage bufferedImage,Texture texture) {
        ByteBuffer imageBuffer = null;
        WritableRaster raster;
        BufferedImage texImage;

        int texWidth = 2;
        int texHeight = 2;

        // find the closest power of 2 for the width and height
        // of the produced texture
        while (texWidth < bufferedImage.getWidth()) {
            texWidth *= 2;
        }
        while (texHeight < bufferedImage.getHeight()) {
            texHeight *= 2;
        }

        texture.setTextureHeight(texHeight);
        texture.setTextureWidth(texWidth);

        // create a raster that can be used by OpenGL as a source
        // for a texture
        if (bufferedImage.getColorModel().hasAlpha()) {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,texWidth,texHeight,4,null);
            texImage = new BufferedImage(glAlphaColorModel,raster,false,new Hashtable());
        } else {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,texWidth,texHeight,3,null);
            texImage = new BufferedImage(glColorModel,raster,false,new Hashtable());
        }

        // copy the source image into the produced image
        Graphics g = texImage.getGraphics();
        g.setColor(new Color(0f,0f,0f,0f));
        g.fillRect(0,0,texWidth,texHeight);
        g.drawImage(bufferedImage,0,0,null);

        // build a byte buffer from the temporary image
        // that be used by OpenGL to produce a texture.
        byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();

        imageBuffer = ByteBuffer.allocateDirect(data.length);
        imageBuffer.order(ByteOrder.nativeOrder());
        imageBuffer.put(data, 0, data.length);
        imageBuffer.flip();

        return imageBuffer;
    }

    /**
     * Load a given resource as a buffered image
     *
     * @param ref The location of the resource to load
     * @return The loaded buffered image
     * @throws IOException Indicates a failure to find a resource
     */
    private BufferedImage loadImage(String ref) throws IOException
    {
        URL url = TextureLoader.class.getClassLoader().getResource(ref);

        if (url == null) {
            throw new IOException("Cannot find: "+ref);
        }

        return ImageIO.read(new BufferedInputStream(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(ref))));
    }

    /**
     * Creates an integer buffer to hold specified ints
     * - strictly a utility method
     *
     * @param size how many int to contain
     * @return created IntBuffer
     */
    protected IntBuffer createIntBuffer(int size) {
        ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
        temp.order(ByteOrder.nativeOrder());

        return temp.asIntBuffer();
    }
}
