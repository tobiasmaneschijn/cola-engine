package com.tobiasmaneschijn.realestate.entities;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Sine;
import com.tobiasmaneschijn.colaengine.core.Entity;
import com.tobiasmaneschijn.colaengine.core.ResourceFactory;
import com.tobiasmaneschijn.colaengine.core.Sprite;
import com.tobiasmaneschijn.colaengine.tweening.accessors.EntityAccessor;
import com.tobiasmaneschijn.realestate.RealEstateGame;
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
        Tween.to(this, EntityAccessor.SCALE_XY, 2f) .target(getSprite().getWidth() * 1.1f, getSprite().getHeight() * 1.2f) .ease(Sine.INOUT) .repeatYoyo(100, 0) .start(RealEstateGame.tweenManager);

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
