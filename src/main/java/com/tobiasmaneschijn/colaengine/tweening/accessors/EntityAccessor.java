package com.tobiasmaneschijn.colaengine.tweening.accessors;

import aurelienribon.tweenengine.TweenAccessor;
import com.tobiasmaneschijn.colaengine.core.Entity;
import org.joml.Vector2f;

public class EntityAccessor implements TweenAccessor<Entity> {
    public static final int SCALE_X = 1;
    public static final int SCALE_Y = 2;
    public static final int SCALE_XY = 3;

// TweenAccessor implementation

    @Override
    public int getValues(Entity target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case SCALE_X -> {
                returnValues[0] = target.getSize().x;
                return 1;
            }
            case SCALE_Y -> {
                returnValues[0] = target.getSize().y;
                return 1;
            }
            case SCALE_XY -> {
                returnValues[0] = target.getSize().x;
                returnValues[1] = target.getSize().y;
                return 2;
            }
            default -> {
                assert false;
                return -1;
            }
        }
    }

    @Override
    public void setValues(Entity target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case SCALE_X: target.setSize(new Vector2f(newValues[0], target.getSize().y)); break;
            case SCALE_Y: target.setSize(new Vector2f(target.getSize().x, newValues[0])); break;
            case SCALE_XY:
                target.setSize(new Vector2f(newValues[0], target.getSize().y));
                target.setSize(new Vector2f(target.getSize().x, newValues[1]));
                break;
            default: assert false; break;
        }
    }
}
