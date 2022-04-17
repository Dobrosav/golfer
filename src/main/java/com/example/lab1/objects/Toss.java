package com.example.lab1.objects;

import com.example.lab1.Main;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Toss extends Circle {
    private double rangemaxx=Main.WINDOW_WIDTH, rangemaxy=Main.WINDOW_HEIGHT;

    private static Random random=new Random();
    public Toss(){
        super();
        super.setFill(Color.YELLOW);
        super.setRadius(10);
        double x=  20 + (rangemaxx - 20) * random.nextDouble(),y=20 + (rangemaxy - 20) * random.nextDouble();

        System.out.println(x);
        super.setCenterY((y));
        super.setCenterX((x));
        System.out.println(y);
    }
}
