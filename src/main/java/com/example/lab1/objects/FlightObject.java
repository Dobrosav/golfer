package com.example.lab1.objects;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class FlightObject extends Circle {
    private final double y=450;
    private final double x=20;
    public FlightObject(){
        setCenterX(x+15);
        setCenterY(y);
        setFill(Color.AZURE);
        setRadius(15);
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
