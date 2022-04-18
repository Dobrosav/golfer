package com.example.lab1.objects;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Teleport extends Circle {
    public Teleport(double x, double y){
        super();
        super.setCenterX(x);
        setCenterY(y);
        setRadius(10);
        setFill(Color.BLACK);
    }
    public boolean handleCollision(final Circle ball) {
        final Bounds ballBounds = ball.getBoundsInParent();
        final double ballX = ballBounds.getCenterX();
        final double ballY = ballBounds.getCenterY();
        final double ballRadius = ball.getRadius();
        final Bounds holeBounds = super.getBoundsInParent();
        final double holeX = holeBounds.getCenterX();
        final double holeY = holeBounds.getCenterY();
        final double holeRadius = super.getRadius();
        final double distanceX = holeX - ballX;
        final double distanceY = holeY - ballY;
        final double distanceSquared = distanceX * distanceX + distanceY * distanceY;
        final boolean result = distanceSquared < holeRadius * holeRadius;
        return result;
    }

}
