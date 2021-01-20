package com.tobiasmaneschijn.core;

import org.lwjgl.opengl.GL45;

import java.util.ArrayList;

public class Game implements GameWindowCallback{

    /** The list of all the entities that exist in our game */
    private ArrayList entities = new ArrayList();
    /** The list of entities that need to be removed from the game this loop */
    private ArrayList removeList = new ArrayList();
    /** The entity representing the player */
    private Entity ship;
    /** The speed at which the player's ship should move (pixels/sec) */
    private double moveSpeed = 300;
    /** The time at which last fired a shot */
    private long lastFire = 0;
    /** The interval between our players shot (ms) */
    private long firingInterval = 500;
    /** The number of aliens left on the screen */
    private int alienCount;

    /** The message to display which waiting for a key press */
    private Sprite message;
    /** True if we're holding up game play until a key has been pressed */
    private boolean waitingForKeyPress = true;
    /** True if game logic needs to be applied this loop, normally as a result of a game event */
    private boolean logicRequiredThisLoop = false;

    /** The window that is being used to render the game */
    private GameWindow window;
    /** True if the fire key has been released */
    private boolean fireHasBeenReleased = false;

    /** The sprite containing the "Press Any Key" message */
    private Sprite pressAnyKey;
    /** The sprite containing the "You win!" message */
    private Sprite youWin;
    /** The sprite containing the "You lose!" message */
    private Sprite gotYou;

    public Game(){

    }
    public void startGame(){
        window = new GameWindow("Cola Engine", 1280, 720, this);
    }

    public GameWindow getGameWindow() {
        return window;
    }

    @Override
    public void initialise() {
         gotYou = ResourceFactory.get().getSprite("sprites/Character.gif");
    }

    @Override
    public void draw() {
        gotYou.draw(0,10);
    }

    @Override
    public void windowClosed() {


// Disable the VBO index from the VAO attributes list
        GL45.glDisableVertexAttribArray(0);

// Delete the VBO
        GL45.glBindBuffer(GL45.GL_ARRAY_BUFFER, 0);
        GL45.glDeleteBuffers(gotYou.getVboId());

// Delete the VAO
        GL45.glBindVertexArray(0);
        GL45.glDeleteVertexArrays(gotYou.getVaoId());
    }
}
