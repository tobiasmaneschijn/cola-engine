package com.tobiasmaneschijn.core;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL45;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GameWindow{
    /** The callback which should be notified of window events */
    private GameWindowCallback callback;

    /** The window handle */
    private long window;

    /** The width of the game display area */
    private int width;

    /** The height of the game display area */
    private int height;

    /** The loader responsible for converting images into OpenGL textures */
    private TextureLoader textureLoader;

    /** Title of window, we get it before our window is ready, so store it till needed */
    private String title;

    /**
     * Create a new game window that will use OpenGL to
     * render our game.
     */
    public GameWindow() {
        init();
        loop();
    }

    private void init(){
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        textureLoader = new TextureLoader();

        if(callback != null) {
            callback.initialise();
        }

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
                // Center the window
                if (vidmode != null) {
                    glfwSetWindowPos(
                            window,
                            (vidmode.width() - pWidth.get(0)) / 2,
                            (vidmode.height() - pHeight.get(0)) / 2
                    );
                }
                    else{
                    System.out.println("GLFW: No video mode set!");
                }
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
        // Make the window visible
        glfwShowWindow(window);

/*
try {
            // enable textures since we're going to use these for our sprites
            GL11.glEnable(GL11.GL_TEXTURE_2D);

            // disable the OpenGL depth test since we're rendering 2D graphics
            GL11.glDisable(GL11.GL_DEPTH_TEST);

            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();

            GL11.glOrtho(0, width, height, 0, -1, 1);

            textureLoader = new TextureLoader();

            if(callback != null) {
                callback.initialise();
            }
        } catch (LWJGLException le) {
            callback.windowClosed();
        }

        gameLoop();
  */
        }

    /**
     * Retrieve access to the texture loader that converts images
     * into OpenGL textures. Note, this has been made package level
     * since only other parts of the JOGL implementations need to access
     * it.
     *
     * @return The texture loader that can be used to load images into
     * OpenGL textures.
     */
    TextureLoader getTextureLoader() {
        return textureLoader;
    }

    /**
     * Set the title of this window.
     *
     * @param title The title to set on this window
     */
    public void setTitle(String title) {
        this.title = title;
        if(window != NULL) {
            glfwSetWindowTitle(window, title);
        }
    }

    /**
     * Set the resolution of the game display area.
     *
     * @param x The width of the game display area
     * @param y The height of the game display area
     */
    public void setResolution(int x, int y) {
        width = x;
        height = y;
    }




    /**
     * Register a callback that will be notified of game window
     * events.
     *
     * @param callback The callback that should be notified of game
     * window events.
     */
    public void setGameWindowCallback(GameWindowCallback callback) {
        this.callback = callback;
    }

    /**
     * Check if a particular key is current pressed.
     *
     * @param keyCode The code associated with the key to check
     * @return True if the specified key is pressed
     */
    public boolean isKeyPressed(int keyCode) {

        return false;
    }

    /**
     * Run the main game loop. This method keeps rendering the scene
     * and requesting that the callback update its screen.
     */
    private void loop() {
        GL.createCapabilities(); // Needed for LWJGL.


        // Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        while (!glfwWindowShouldClose(window)) {




            GL45.glClear(GL45.GL_COLOR_BUFFER_BIT | GL45.GL_DEPTH_BUFFER_BIT);

            // let subsystem paint
            if (callback != null) {
                callback.frameRendering();
            }

            glfwSwapBuffers(window); // Swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();

        }

        if(callback != null) {
            callback.windowClosed();
        }
    }

    /**
     * @return The width of the GameWindow
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return The height of the GameWindow
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return The title of the GameWindow
     */
    public String getTitle() {
        return title;
    }
}
