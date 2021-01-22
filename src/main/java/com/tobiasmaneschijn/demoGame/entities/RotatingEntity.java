package com.tobiasmaneschijn.demoGame.entities;

import com.tobiasmaneschijn.colaengine.core.Entity;
import com.tobiasmaneschijn.colaengine.core.ResourceFactory;
import org.joml.Vector2f;

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
