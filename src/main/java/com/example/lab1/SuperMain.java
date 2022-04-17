package com.example.lab1;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.IOException;


public class SuperMain extends Application {

    public static void main(){
      launch();
    }
    private Text trava=new Text(80,10, "Trava");
    private Text pesak=new Text(200,10, "Pesak");
    private Text beton=new Text(290,10, "Beton");

    private Text top1=new Text(80,100 ,"Top1");
    private Text top2=new Text(200,100, "Top2");
    private Text top3=new Text(290,100, "Top3");
    private Button btntrava;
    private Button btnpesak;
    private Button btnBeteon;

    private Button btntop1;
    private Button btntop2;
    private Button btntop3;
    private Group root;
    public static String[] parameters= new String[2];
    private Button btntart;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.root=new Group();
        this.root.getChildren().addAll(trava,beton,pesak,top1,top3,top2);
        btntrava=new Button("Trava");
        btntrava.setLayoutX(80);
        btntrava.setLayoutY(20);
        btntrava.setOnAction(actionEvent -> {
            SuperMain.parameters[0]="grass";
        });
        btnBeteon=new Button("Beton");
        btnBeteon.setLayoutX(290);
        btnBeteon.setLayoutY(20);
        btnBeteon.setOnAction(actionEvent -> {
            SuperMain.parameters[0]="concret";
            System.out.println("Beton");
        });
        btnpesak=new Button("Pesak");
        btnpesak.setLayoutX(200);
        btnpesak.setLayoutY(20);
        btnpesak.setOnAction(actionEvent -> {
            SuperMain.parameters[0]="pesa";
            System.out.println("Pesak");
        });
        btntop1=new Button("top1");
        btntop1.setLayoutX(80);
        btntop1.setLayoutY(110);
        btntop1.setOnAction(actionEvent -> {
            SuperMain.parameters[1]="top1";
        });
        btntop3=new Button("Top3");
        btntop3.setLayoutX(290);
        btntop3.setLayoutY(110);
        btntop3.setOnAction(actionEvent -> {
            SuperMain.parameters[1]="top3";
        });
        btntop2=new Button("Top2");
        btntop2.setLayoutX(200);
        btntop2.setLayoutY(110);
        btntop2.setOnAction(actionEvent -> {
            System.out.println("top2");
            SuperMain.parameters[1]="top2";
        });
        btntart=new Button("Start");
        btntart.setLayoutX(175);
        btntart.setLayoutY(150);
        btntart.setOnAction(actionEvent -> {
            System.out.println("start");
            primaryStage.hide();
            Main main=new Main();
            try {
                main.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.root.getChildren().addAll(btntrava,btnBeteon,btnpesak,btntop2,btntop1,btntop3,btntart);
        Scene scene= new Scene(root,350,300);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent k) ->{
           if (k.getCode().equals(KeyCode.K))
               System.out.println("K pressed");
        });
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
