package com.tobiasmaneschijn.demoGame.entities;

import com.tobiasmaneschijn.colaengine.core.Entity;
import com.tobiasmaneschijn.colaengine.core.ResourceFactory;
import com.tobiasmaneschijn.demoGame.DemoGame;
import org.joml.Math;
import org.joml.Vector2f;

public class EnemyEntity extends Entity {

    public EnemyEntity() {
        super();
        setSprite(ResourceFactory.get().getSprite("sprites/Character.gif"));
        setSize(new Vector2f(getSprite().getWidth() * 2f, getSprite().getHeight() * 2f));
        getSprite().initRenderData();

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        float currentRot = getRotation();
        float sin = Math.sin(DemoGame.getTime());
        getPosition().y =  200f * .2f * sin;
        getPosition().x += 100 * deltaTime;
        setRotation( 10 * sin );
    }
}
