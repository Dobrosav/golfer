package com.example.lab1;

import com.example.lab1.objects.*;
import com.example.lab1.widgets.ProgressBar;
import javafx.event.Event;
import javafx.geometry.Point2D;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;
import com.example.lab1.widgets.Status;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.application.Application;

public class Main extends Application implements EventHandler {
    public static final double WINDOW_WIDTH = 600.0;
    public static final double WINDOW_HEIGHT = 800.0;
    private Group root;
    private Player player;
    private Ball ball;
    private long time;
    private Hole[] holes;
    private ProgressBar progressBar;
    private Obstacle[] obstacles;
    private Fence fence;
    private SpeedModifier[] mudPuddles;
    private SpeedModifier[] icePatches;
    private Status status;
    private State state;
    private Toss toss;

    private void addSpeedModifiers() {
        Translate mudPuddle0Position = new Translate(105.0, 105.0);
        final SpeedModifier mudPuddle0 = SpeedModifier.mud(30.0, 30.0, mudPuddle0Position);
        this.root.getChildren().addAll(mudPuddle0);
        final Translate icePatch0Position = new Translate(465.0, 105.0);
        final SpeedModifier icePatch0 = SpeedModifier.ice(30.0, 30.0, icePatch0Position);
        this.root.getChildren().addAll(icePatch0);
        final Translate mudPuddle1Position = new Translate(465.0, 545.0);
        final SpeedModifier mudPuddle2 = SpeedModifier.mud(30.0, 30.0, mudPuddle1Position);
        this.root.getChildren().addAll(mudPuddle2);
        final Translate icePatch1Position = new Translate(105.0, 545.0);
        final SpeedModifier icePatch2 = SpeedModifier.ice(30.0, 30.0, icePatch1Position);
        this.root.getChildren().addAll(icePatch2);
        this.mudPuddles = new SpeedModifier[]{mudPuddle0, mudPuddle2};
        this.icePatches = new SpeedModifier[]{icePatch0, icePatch2};
    }

    private void addObstacles() {
        final Translate obstacle0Position = new Translate(141., 400.);
        final Obstacle obstacle0 = new Obstacle(120.0, 16.0, obstacle0Position);
        this.root.getChildren().addAll(obstacle0);
        final Translate obstacle1Position = new Translate(340.0, 400.0);
        final Obstacle obstacle2 = new Obstacle(120.0, 16.0, obstacle1Position);
        this.root.getChildren().addAll(obstacle2);
        final Translate obstacle2Position = new Translate(292.0, 136.0);
        final Obstacle obstacle3 = new Obstacle(16.0, 120.0, obstacle2Position);
        this.root.getChildren().addAll(obstacle3);
        this.obstacles = new Obstacle[]{obstacle0, obstacle2, obstacle3};
    }

    private void addHoles() {
        final Translate hole0Position = new Translate(300.0, 80.0);
        final Hole hole0 = new Hole(15.0, hole0Position, Color.DARKGOLDENROD, 20);
        this.root.getChildren().addAll(hole0);
        final Translate hole1Position = new Translate(300.0, 320.0);
        final Hole hole2 = new Hole(15.0, hole1Position, Color.LIGHTGREEN, 5);
        this.root.getChildren().addAll(hole2);
        final Translate hole2Position = new Translate(200.0, 200.0);
        final Hole hole3 = new Hole(15.0, hole2Position, Color.YELLOW, 10);
        this.root.getChildren().addAll(hole3);
        final Translate hole3Position = new Translate(400.0, 200.0);
        final Hole hole4 = new Hole(15.0, hole3Position, Color.YELLOW, 10);
        this.root.getChildren().addAll(hole4);
        this.holes = new Hole[]{hole0, hole2, hole3, hole4};
    }

    @Override
    public void start(final Stage stage) throws IOException {
        this.root = new Group();
        this.state = Main.State.IDLE;
        Image grassImage = new Image(this.getClass().getClassLoader().getResourceAsStream(SuperMain.parameters[0] + ".jpg"));
        final ImagePattern grass = new ImagePattern(grassImage);
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT, grass);
        this.progressBar = new ProgressBar(10.0, 0);
        this.root.getChildren().addAll(this.progressBar);
        final Image fenceImage = new Image(this.getClass().getClassLoader().getResourceAsStream("fences.jpg"));
        final ImagePattern fenceImagePattern = new ImagePattern(fenceImage);
        this.fence = new Fence(600.0, 800.0, 18.0, fenceImagePattern);
        this.root.getChildren().addAll(this.fence);
        final Translate playerPosition = new Translate(290.0, 720.0);
        Color bc = Color.ORANGE, cc = Color.LIGHTBLUE;
        switch (SuperMain.parameters[1]) {
            case "top1":
                bc = Color.ORANGE;
                cc = Color.LIGHTBLUE;
                break;
            case "top2":
                bc = Color.GREEN;
                cc = Color.RED;
                break;
            case "top3":
                bc = Color.BLACK;
                cc = Color.GREEN;
        }
        this.player = new Player(20.0, 80.0, playerPosition, bc, cc);
        this.root.getChildren().addAll(this.player);
        this.addHoles();
        this.addObstacles();
        this.addSpeedModifiers();
        this.status = new Status(5, 5.0, 600.0);
        this.root.getChildren().addAll(this.status);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent k)->{
            if (k.getCode().equals(KeyCode.SPACE)) {
                System.out.println("SPACE");
                if (this.ball!=null){
                    this.root.getChildren().remove(this.ball);
                    this.ball=null;
                }
            }
        });
        scene.addEventHandler(MouseEvent.MOUSE_MOVED, mouseEvent -> this.player.handleMouseMoved(mouseEvent, -60.0, 60.0));
        scene.addEventHandler(MouseEvent.ANY, this);



        Timer timer = new Timer(deltaNanoseconds -> {
            final double deltaSeconds = deltaNanoseconds / 1.0E9;
            if (this.ball==null)
                return;
            if (this.state == State.BALL_SHOT) {
               final boolean collidedWithObstacle = Arrays.stream(this.obstacles).anyMatch(obstacle -> this.ball.handleCollision(obstacle.getBoundsInParent()));
               double dampFactor = 0.995;
               final boolean inMud = Arrays.stream(this.mudPuddles).anyMatch(mudPuddle -> mudPuddle.handleCollision(this.ball));
                final boolean isOverIce = Arrays.stream(this.icePatches).anyMatch(icePatch -> icePatch.handleCollision(this.ball));
                if (inMud) {
                    dampFactor = 0.9;
                }
                else if (isOverIce) {
                    dampFactor = 1.1;
                }
                boolean isInHole = Arrays.stream(this.holes).anyMatch(hole -> hole.handleCollision(this.ball));
                if (isInHole && this.ball.getSpeedMagnitude()<400)  {
                    status.addPoints();
                    this.root.getChildren().remove(this.ball);
                    this.ball = null;
                }
                if (this.ball==null)
                    return;
                boolean stopped = this.ball.update(deltaSeconds, 18.0, 582.0, 18.0, 782.0, dampFactor, 1.0);
                final Optional<Hole> optionalHole;optionalHole = Arrays.stream(this.holes).filter(hole -> hole.handleCollision(this.ball)).findFirst();
                if (stopped) {
                    this.root.getChildren().remove(this.ball);
                    this.ball = null;
                    this.state = State.IDLE;
                }
            }
            else if (this.state == State.PREPARATION) {
                this.progressBar.elapsed(deltaNanoseconds, 3.0E9);
            }
            return;
        });

        timer.start();
        Timer timer1=new Timer(delta->{
                int deltaSeconds = (int) (delta / 1.0E3);
                //    System.out.println(deltaSeconds);
                if (deltaSeconds % 50 == 0 && toss == null) {
                    toss = new Toss();
                    this.root.getChildren().add(toss);
                }
                if (deltaSeconds % 1000 == 0) {
                    this.root.getChildren().remove(toss);
                    toss = null;
                }
                if (ball!=null &&toss!=null &&(this.ball.getCenterX() <= (toss.getCenterX() + toss.getRadius())) &&
                        this.ball.getCenterX() > toss.getCenterX() &&
                        this.ball.getCenterY() <= (toss.getRadius() + toss.getCenterY()) && ball.getCenterY() > toss.getCenterY()) {
                    this.root.getChildren().remove(toss);
                    System.out.println("poklopio se");
                    toss = null;
                    status.addPointsToss();
                }
            return;
        });
        timer1.start();
         scene.setCursor(Cursor.NONE);
        stage.setTitle("Golfer");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(final String[] args) {
        launch();
    }

    @Override
    public void handle(Event event) {
        if (event instanceof MouseEvent)
            this.handlemouse((MouseEvent) event);
        if (event instanceof KeyEvent)
            this.handleKeyEvent((KeyEvent) event);
    }


    private void handleKeyEvent(KeyEvent event) {
            if (event.getCode().equals(KeyCode.S)){
                System.out.println(event);
            }
    }

    private void handlemouse(MouseEvent mouseEvent) {
        if (this.status.hasLives()) {
            if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_PRESSED) && mouseEvent.isPrimaryButtonDown()) {
                this.time = System.currentTimeMillis();
                this.progressBar.onClick();
                System.out.println("mouse pressed");
                this.state= State.PREPARATION;
            } else if (this.state== State.PREPARATION && mouseEvent.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                final double value = (System.currentTimeMillis() - this.time) / 1000.0;
                final double deltaSeconds = Utilities.clamp(value, 0.0, 3.0);
                final double ballSpeedFactor = deltaSeconds / 3.0 * 1500.0;
                final Translate ballPosition = this.player.getBallPosition();
                System.out.println("Mouse released");
                final Point2D ballSpeed = this.player.getSpeed().multiply(ballSpeedFactor);
                this.ball = new Ball(5.0, ballPosition, ballSpeed);
                this.root.getChildren().addAll(this.ball);
                this.progressBar.onRelease();
                this.status.removeLife();
                this.state = Main.State.BALL_SHOT;
            }
        }

    }
    private enum State {
        IDLE,
        PREPARATION,
        BALL_SHOT,
        FALLING;

        // $FF: synthetic method
        private static Main.State[] $values() {
            return new Main.State[]{IDLE, PREPARATION, BALL_SHOT, FALLING};
        }
    }

    public static class SuperSuperMain {
        public static void main(String[] arg){
            SuperMain.main();
        }

    }
}

