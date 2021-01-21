package com.tobiasmaneschijn.core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL45;

import java.util.ArrayList;

import org.lwjgl.system.Library.*;


public class Game implements GameWindowCallback{

    /** The list of all the entities that exist in our game */
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    /** The list of entities that need to be removed from the game this loop */
    private ArrayList<Entity> removeList = new ArrayList<Entity>();
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



    private String gameTitle = "Unnamed Game";
    /**
     * The window that is being used to render the game
     */
    private GameWindow window;

    private static Game instance;
    
    public static Game get(){
        if( instance != null) return instance;
        instance = new Game();
        return  instance;
    }
    
    
    public Game() {
    }

    public void startGame() {
        startGame(1280, 720);
    }

    public void startGame(int width, int height) {
        new GameWindow( gameTitle, width, height, this);
    }

    public GameWindow getGameWindow() {
        return window;
    }

    @Override
    public void setWindow(GameWindow window) {
        this.window =  window;
    }

    @Override
    public void initialise() {


        for (Entity entity : entities) {
            Sprite sprite = entity.getSprite();
            if(sprite != null){
                sprite.initRenderData();
            }
        }


    }

    @Override
    public void draw() {
        for (Entity entity : entities) {
        entity.render();
        }
        for (Entity entity : removeList) {
            entity.destroy();
            removeList.remove(entity);
        }

    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0, entitiesSize = entities.size(); i < entitiesSize; i++) {
            Entity entity = entities.get(i);
            if(entity!= null)
            entity.update(deltaTime);
        }

    }

    @Override
    public void windowClosed() {
        removeList = entities;

        // Disable the VBO index from the VAO attributes list


        GL45.glBindBuffer(GL45.GL_ARRAY_BUFFER, 0);
        GL45.glDisableVertexAttribArray(0);

        for (Entity entity : removeList) {
            entity.destroy();
        }

        removeList.clear();
        GL45.glBindVertexArray(0);


    }

    @Override
    public void windowResized(int width, int height) {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.getSprite().initRenderData();
        }

    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public void destroyEntity(Entity e) {
        entities.remove(e);
        removeList.add(e);
    }


    public static float getTime() {
        return (float) GLFW.glfwGetTime();
    }
}
