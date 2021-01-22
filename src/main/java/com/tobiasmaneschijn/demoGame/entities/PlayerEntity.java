package com.tobiasmaneschijn.demoGame.entities;

import com.tobiasmaneschijn.colaengine.core.Entity;
import com.tobiasmaneschijn.colaengine.core.ResourceFactory;
import com.tobiasmaneschijn.demoGame.DemoGame;
import org.joml.Vector2f;
import org.joml.Math;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerEntity extends Entity {

    /** The speed at which the player moves. Is not related to any kind of units */
    private float moveSpeed = 100.0f;

    /** Time since player last fired */
    private float lastShot = 1;

    public PlayerEntity() {
        super();
        setSprite(ResourceFactory.get().getSprite("sprites/Character.gif"));
        setSize(new Vector2f(getSprite().getWidth() * 2f, getSprite().getHeight() * 2f));
        getSprite().initRenderData();

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        lastShot += deltaTime;

        if(DemoGame.get().getGameWindow().isKeyPressed(GLFW_KEY_LEFT_SHIFT)){
            moveSpeed = 250;
        }
        else{
            moveSpeed = 100;
        }

        if (DemoGame.get().getGameWindow() == null) return;

        Vector2f currentPos = new Vector2f(getPosition().x, getPosition().y);
        Vector2f moveInput = new Vector2f(0, 0);


        if (DemoGame.get().getGameWindow().isKeyPressed(GLFW_KEY_A)) {
            moveInput.x = -1;
        }
        if (DemoGame.get().getGameWindow().isKeyPressed(GLFW_KEY_D)) {
            moveInput.x = 1;
        }
        
        if(DemoGame.get().getGameWindow().isKeyPressed(GLFW_KEY_SPACE))
        {
            tryShoot(deltaTime);
        }
        float smoothSpeed = 100f;
        setPosition(currentPos.add(moveSpeed * deltaTime * moveInput.x, moveSpeed * deltaTime * moveInput.y));

        getPosition().x = Math.clamp(400, 1000, getPosition().x); // Limit movement
    }


 /** Try to fire a bullet. Only fire if player hasn't fired for the last 1 second */
    private void tryShoot(float deltaTime) {

        if(lastShot > .2f){
            lastShot = 0;
            ProjectileEntity projectileEntity = new ProjectileEntity();
            projectileEntity.setPosition(getPosition().add(0, -20));
            DemoGame.get().addEntity(projectileEntity);
        }

    }
}
