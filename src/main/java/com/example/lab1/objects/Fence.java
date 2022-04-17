
package com.example.lab1.objects;
import javafx.scene.transform.Translate;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;

public class Fence extends Group
{
    private Rectangle left;
    private Rectangle right;
    private Rectangle top;
    private Rectangle bottom;
    
    public Fence(final double width, final double height, final double fenceWidth, final ImagePattern texture) {
        this.left = new Rectangle(fenceWidth, height, texture);
        this.right = new Rectangle(fenceWidth, height, texture);
        this.right.getTransforms().addAll(new Translate(width - fenceWidth, 0.0));
        this.top = new Rectangle(width - 2.0 * fenceWidth, fenceWidth, texture);
        this.top.getTransforms().addAll(new Translate(fenceWidth, 0.0));
        this.bottom = new Rectangle(width - 2.0 * fenceWidth, fenceWidth, texture);
        this.bottom.getTransforms().addAll(new Translate(fenceWidth, height - fenceWidth));
        super.getChildren().addAll(this.left, this.right, this.top, this.bottom);
    }
    

}
