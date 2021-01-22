package com.tobiasmaneschijn.realestate.entities;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Bounce;
import aurelienribon.tweenengine.equations.Cubic;
import aurelienribon.tweenengine.equations.Sine;
import com.tobiasmaneschijn.colaengine.core.Entity;
import com.tobiasmaneschijn.colaengine.core.ResourceFactory;
import com.tobiasmaneschijn.colaengine.core.Sprite;
import com.tobiasmaneschijn.colaengine.tweening.accessors.EntityAccessor;
import com.tobiasmaneschijn.realestate.RealEstateGame;
import org.joml.Math;
import org.joml.Random;
import org.joml.Vector2f;

public class DieEntity extends Entity {

    Sprite[] spriteFaces;

    public DieEntity() {
        super();
        spriteFaces = new Sprite[6];
        spriteFaces[0] = ResourceFactory.get().getSprite("sprites/Die1.gif");
        spriteFaces[1] = ResourceFactory.get().getSprite("sprites/Die2.gif");
        spriteFaces[2] = ResourceFactory.get().getSprite("sprites/Die3.gif");
        spriteFaces[3] = ResourceFactory.get().getSprite("sprites/Die4.gif");
        spriteFaces[4] = ResourceFactory.get().getSprite("sprites/Die5.gif");
        spriteFaces[5] = ResourceFactory.get().getSprite("sprites/Die6.gif");
        setDie(1);
        Tween.to(this, EntityAccessor.SCALE_XY, 2f) .target(spriteFaces[0].getWidth() * 1.1f, spriteFaces[0].getHeight() * 1.1f) .ease(Sine.INOUT) .repeatYoyo(100, 0) .start(RealEstateGame.tweenManager);


    }

    /**
     * Set the sprite used for the die according to face value.
     * Will be clamped between 1 and 6
     * @param faceValue The value that should be shown on the die
     */
    public void setDie(int faceValue){
       int val = Math.clamp(1,6, faceValue);
       setSprite(spriteFaces[val-1]);
       setSize(new Vector2f(getSprite().getWidth(), getSprite().getHeight()));
       Random random = new Random();
       setPosition(200 + random.nextFloat() * 200f, 200 + random.nextFloat() * 200f);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void render() {
        super.render();
    }
}
