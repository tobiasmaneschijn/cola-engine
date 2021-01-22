package com.tobiasmaneschijn.realestate.entities;

import com.tobiasmaneschijn.core.Entity;
import com.tobiasmaneschijn.core.ResourceFactory;
import com.tobiasmaneschijn.core.Sprite;
import com.tobiasmaneschijn.math.MathUtils;
import org.joml.Vector2f;

public class PlayerEntity extends Entity {
    Vector2f targetPosition;
    public PlayerEntity() {
        super();
        Sprite spr = ResourceFactory.get().getSprite("sprites/Character.gif");
        setSprite(spr);
        setSize(new Vector2f(getSprite().getWidth() * 1f, getSprite().getHeight() * 1f));
        getSprite().initRenderData();
        targetPosition = new Vector2f(getPosition());

    }

    public void moveTo(Vector2f position){
        targetPosition =  new Vector2f(position);
    }
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        setPosition(getPosition().lerp(targetPosition, deltaTime * 6));
    }

    @Override
    public void render() {
        super.render();
    }

    public void setName(String tobias) {
    }
}
