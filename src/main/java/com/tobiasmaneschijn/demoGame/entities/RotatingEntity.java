package com.tobiasmaneschijn.demoGame.entities;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenUtils;
import com.tobiasmaneschijn.core.Entity;
import com.tobiasmaneschijn.core.ResourceFactory;
import com.tobiasmaneschijn.demoGame.DemoGame;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class RotatingEntity extends Entity {

    public RotatingEntity() {
        super();
        setSprite(ResourceFactory.get().getSprite("sprites/Character.gif"));
        setSize(new Vector2f(getSprite().getWidth() * 2f, getSprite().getHeight() * 2f));
        getSprite().initRenderData();

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        float currentRot = getRotation();


        setRotation(getRotation() + 10 * deltaTime);
    }
}
