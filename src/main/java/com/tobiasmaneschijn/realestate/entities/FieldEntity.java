package com.tobiasmaneschijn.realestate.entities;

import com.tobiasmaneschijn.colaengine.core.Entity;
import com.tobiasmaneschijn.colaengine.core.ResourceFactory;
import com.tobiasmaneschijn.colaengine.core.Sprite;
import org.joml.Vector2f;

public class FieldEntity extends Entity {
    private String name;

    public FieldEntity() {
        super();
        name = "Empty Field";
        init();
    }

    private void init() {
        Sprite spr = ResourceFactory.get().getSprite("sprites/squares/outlined.gif");
        setSprite(spr);
        setSize(new Vector2f(getSprite().getWidth() * 1f, getSprite().getHeight() * 1f));
        getSprite().initRenderData();
    }

    

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void render() {
        super.render();
    }

    public void setName(String s) {
        name = s;
    }
}
