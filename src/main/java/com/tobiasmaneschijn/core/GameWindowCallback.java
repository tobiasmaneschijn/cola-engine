package com.tobiasmaneschijn.core;

/**
 * An interface describing any class that wishes to be notified
 * as the game window renders.
 *
 * @author Kevin Glass
 */
public interface GameWindowCallback {
    /**
     * Notification that game window has been closed.
     */
    public void setWindow(GameWindow window);

    /**
     * Notification that game should initialise any resources it
     * needs to use. This includes loading sprites.
     */
    public void initialise();

    /**
     * Notification that the display is being rendered. The implementor
     * should draw/render the scene and update any game logic
     */
    public void draw();

    public void update(float deltaTime);

    /**
     * Notification that game window has been closed.
     */
    public void windowClosed();

    public void windowResized(int width, int height);
}