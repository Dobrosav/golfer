
package com.example.lab1.objects;

import javafx.geometry.Bounds;
import com.example.lab1.Utilities;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Transform;
import javafx.scene.Node;
import javafx.scene.shape.Path;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.scene.Group;

public class Player extends Group
{
    private double width;
    private double height;
    private Translate position;
    private Rotate rotate;
    private double baseRadius;
    
    public Player(final double width, final double height, final Translate position, Color basecolor, Color canoncolor) {
        this.width = width;
        this.height = height;
        this.position = position;
        this.baseRadius = width / 2.0;
        final Circle base = new Circle(this.baseRadius, basecolor);
        base.getTransforms().add(new Translate(width / 2.0, height - this.baseRadius));
        final Path cannon = new Path(new PathElement[] { new MoveTo(width / 4.0, 0.0), new LineTo(0.0, height - this.baseRadius), new HLineTo(width), new LineTo(width * 3.0 / 4.0, 0.0), new ClosePath() });
        cannon.setFill(canoncolor);
        super.getChildren().addAll(cannon, base);
        this.rotate = new Rotate();
        super.getTransforms().addAll(position, new Translate(width / 2.0, height - this.baseRadius), this.rotate, new Translate(-width / 2.0, -(height - this.baseRadius)));
    }
    
    public void handleMouseMoved(final MouseEvent mouseEvent, final double minAngleOffset, final double maxAngleOffset) {
        final Bounds bounds = super.getBoundsInParent();
        final double startX = bounds.getCenterX();
        final double startY = bounds.getMaxY();
        final double endX = mouseEvent.getX();
        final double endY = mouseEvent.getY();
        final Point2D direction = new Point2D(endX - startX, endY - startY).normalize();
        final Point2D startPosition = new Point2D(0.0, -1.0);
        final double angle = ((endX > startX) ? 1 : -1) * direction.angle(startPosition);
        this.rotate.setAngle(Utilities.clamp(angle, minAngleOffset, maxAngleOffset));
    }
    
    public Translate getBallPosition() {
        final double startX = this.position.getX() + this.width / 2.0;
        final double startY = this.position.getY() + this.height - this.baseRadius;
        final double x = startX + Math.sin(Math.toRadians(this.rotate.getAngle())) * this.height;
        final double y = startY - Math.cos(Math.toRadians(this.rotate.getAngle())) * this.height;
        final Translate result = new Translate(x, y);
        return result;
    }
    
    public Point2D getSpeed() {
        final double startX = this.position.getX() + this.width / 2.0;
        final double startY = this.position.getY() + this.height - this.baseRadius;
        final double endX = startX + Math.sin(Math.toRadians(this.rotate.getAngle())) * this.height;
        final double endY = startY - Math.cos(Math.toRadians(this.rotate.getAngle())) * this.height;
        final Point2D result = new Point2D(endX - startX, endY - startY);
        return result.normalize();
    }
}
