/* Decompiler 21ms, total 224ms, lines 58 */
package com.example.lab1.widgets;

import com.example.lab1.Utilities;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class ProgressBar extends Group {
   private Rectangle progressBar;
   private Scale scale;
   private State state;
   private double time;

   public ProgressBar(double width, double height) {
      this.progressBar = new Rectangle(width, height, Color.RED);
      this.state = State.IDLE;
      this.scale = new Scale(1.0D, 0.0D);
      this.progressBar.getTransforms().addAll(new Transform[]{new Translate(0.0D, height), this.scale, new Translate(0.0D, -height)});
   }

   public void onClick() {
      super.getChildren().addAll(new Node[]{this.progressBar});
      this.scale.setY(0.0D);
      this.state = State.CLICKED;
      this.time = 0.0D;
   }

   public void elapsed(long dns, double max) {
      if (this.state == State.CLICKED) {
         this.time += (double)dns;
         double value = Utilities.clamp(this.time / max, 0.0D, 1.0D);
         this.scale.setY(value);
      }

   }

   public void onRelease() {
      super.getChildren().remove(this.progressBar);
      this.scale.setY(0.0D);
      this.state = State.IDLE;
      this.time = 0.0D;
   }

   private enum State {
      IDLE,
      CLICKED;
      private static State[] $values() {
         return new State[]{IDLE, CLICKED};
      }
   }
}
