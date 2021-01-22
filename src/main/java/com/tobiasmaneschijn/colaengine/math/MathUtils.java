package com.tobiasmaneschijn.colaengine.math;

import org.joml.Vector2f;

public class MathUtils {

    public static Vector2f InterpToV(Vector2f current, Vector2f target, float deltaTime, float interpSpeed) {
 	Vector2f delta = target.sub(current);
	float deltaM = delta.length();
	float maxStep = interpSpeed * deltaTime;
        System.out.println("maxStep: " + maxStep);
        if (deltaM > maxStep) {
            if (maxStep > 0.f) {
			Vector2f deltaN = delta.div(deltaM);
                return current.add(deltaN.mul(maxStep));
            } else {
                return current;
            }
        }

        return target;
    }

}
