package com.tobiasmaneschijn.core;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Entity {

    Vector2f position;
    Vector2f size;
    float rotation = 0;

    public Vector2f getSize() {
        return size;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    Sprite sprite;

    public Entity() {
        this(new Vector2f(200, 200), new Vector2f(300.0f, 400.0f), 0);
    }

    Entity(Vector2f position, Vector2f size, float rotation) {
        this.position = position;
        this.size = size;
        this.rotation = rotation;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f newPosition) {
        this.position = newPosition;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    /**
     * Called on every frame
     */
    public void update(float deltaTime) {

    }


    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        getSprite().initRenderData();
    }

    @Override
    public String toString() {
        return "Entity{" +
                "position=" + position +
                ", sprite=" + sprite +
                '}';
    }

    public void destroy() {
        sprite.destroy();
    }

    public void render() {
        if (sprite != null) {
            // Draw the sprite
            sprite.draw(position, size, rotation);
        }
    }
}
