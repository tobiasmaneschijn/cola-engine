package com.tobiasmaneschijn.demoGame.entities;

import com.tobiasmaneschijn.core.Entity;
import com.tobiasmaneschijn.core.ResourceFactory;
import org.joml.Vector2f;

public class ProjectileEntity extends Entity {

    private float speed = 500f;


    public ProjectileEntity() {
        super();
        setSprite(ResourceFactory.get().getSprite("sprites/Bullet.gif"));
        setSize(new Vector2f(getSprite().getWidth() , getSprite().getHeight()));

        getSprite().initRenderData();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        getPosition().y -= speed * deltaTime;
    }
}
