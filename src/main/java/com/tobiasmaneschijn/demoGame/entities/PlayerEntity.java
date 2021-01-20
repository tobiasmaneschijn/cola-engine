package com.tobiasmaneschijn.demoGame.entities;

import com.tobiasmaneschijn.core.Entity;
import com.tobiasmaneschijn.core.ResourceFactory;
import com.tobiasmaneschijn.demoGame.DemoGame;
import org.joml.Vector2f;
import org.joml.Math;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerEntity extends Entity {
    Vector2f targetPosition;
    private float moveSpeed = 100.0f;


    public PlayerEntity() {
        super();
        setSprite(ResourceFactory.get().getSprite("sprites/Character.gif"));
        setSize(new Vector2f(getSprite().getWidth() * 2f, getSprite().getHeight() * 2f));
        getSprite().initRenderData();

        targetPosition = new Vector2f(getPosition().x, getPosition().y);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

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
        float smoothSpeed = 100f;
        setPosition(currentPos.add(moveSpeed * deltaTime * moveInput.x, moveSpeed * deltaTime * moveInput.y));
        getPosition().x = Math.clamp(400, 1000, getPosition().x);
    }
}
