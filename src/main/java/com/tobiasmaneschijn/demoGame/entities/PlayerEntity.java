package com.tobiasmaneschijn.demoGame.entities;

import com.tobiasmaneschijn.core.Entity;
import com.tobiasmaneschijn.core.ResourceFactory;
import com.tobiasmaneschijn.demoGame.DemoGame;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerEntity extends Entity {

    public PlayerEntity() {
        super();
        setSprite(ResourceFactory.get().getSprite("sprites/Character.gif"));
        setSize(new Vector2f(getSprite().getWidth() * 2f, getSprite().getHeight() * 2f));
        getSprite().initRenderData();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Vector2f targetPosition = getPosition();

        if (DemoGame.get().getGameWindow() == null) return;

        if (DemoGame.get().getGameWindow().isKeyPressed(GLFW_KEY_W)) {
            targetPosition.y = targetPosition.y - 1000f * deltaTime;
        }
        if (DemoGame.get().getGameWindow().isKeyPressed(GLFW_KEY_S)) {
            targetPosition.y= targetPosition.y + 1000f * deltaTime;
        }

        if (DemoGame.get().getGameWindow().isKeyPressed(GLFW_KEY_A)) {
            targetPosition.x = targetPosition.x - 1000f * deltaTime;
        }
        if (DemoGame.get().getGameWindow().isKeyPressed(GLFW_KEY_D)) {
            targetPosition.x = targetPosition.x + 1000f * deltaTime;
        }

        setPosition(getPosition().lerp(targetPosition, .25f * deltaTime));



    }
}
