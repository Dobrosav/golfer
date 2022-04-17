
package com.example.lab1.objects;

import javafx.geometry.Bounds;
import javafx.scene.transform.Transform;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;
import javafx.scene.shape.Circle;

public class Hole extends Circle
{
    private int points;
    
    public Hole(final double radius, final Translate position, final Color color, final int points) {
        super(radius);
        this.points = points;
        final Stop[] stops = { new Stop(0.0, Color.BLACK), new Stop(1.0, Color.YELLOW) };
        final RadialGradient radialGradient = new RadialGradient(0.0, 0.0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, stops);
        super.setFill(radialGradient);
        super.getTransforms().addAll(position);
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
    
    public int getPoints() {
        return this.points;
    }
}
