package ru.nsu.fit.lab9;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;


public class Main extends Application {

    GridPane gameGrid = new GridPane();
    int gridSizeSquared = 40;  //GAME GRID - 40x40
    Label gameOver = new Label("");
    Label pause = new Label("Нажми любую кнопку, чтобы начать.");

    Controller contr = new Controller();

    Timeline loop;
    double loopSpeed = 1 / 10.0; //1/10 seconds == 10FPS

    int deltaX = 0; //the direction of the snake head
    int deltaY = 0;
    //Snakes Head's Initial Position
    int posX = new Random().nextInt(gridSizeSquared);
    int posY = new Random().nextInt(gridSizeSquared);

    //Snake Food, same size as snake
    Circle food = new Circle(6, Color.YELLOW);
    int foodPosX = new Random().nextInt(gridSizeSquared);
    int foodPosY = new Random().nextInt(gridSizeSquared);
    boolean start = false;  //True = Game is Running
    boolean dead = false;  //False = Game is Paused

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        fillGrid();
        contr.growOne(posX, posY); //snake head
        gameGrid.setAlignment(Pos.CENTER);
        gameGrid.add(food, foodPosX, foodPosY); //initial positions
        gameGrid.add(contr.head().getBody(), posX, posY);
        Scene game = new Scene(gameGrid);
        game.setOnKeyPressed(this::keyPressedProcess);

        primaryStage.setTitle("Little Snake");
        primaryStage.setScene(game);
        primaryStage.show();

        //Initializing Loop as timeline.
        loop = new Timeline(new KeyFrame(Duration.seconds(loopSpeed),
                event -> moveUserSnake()));
        loop.setCycleCount(Timeline.INDEFINITE);
    }

    public void moveUserSnake() {
        //WALL CRASH
        if (deltaX == -1 && (posX == 0)) {
            die();
            deltaX = 0;
        } else if (deltaY == -1 && (posY == 0)) {
            die();
            deltaY = 0;
        } else if (deltaX == 1 && (posX == gridSizeSquared - 1)) {
            die();
            deltaX = 0;
        } else if (deltaY == 1 && (posY == gridSizeSquared - 1)) {
            die();
            deltaY = 0;
        } else {

            //Updates head position
            gameGrid.getChildren().remove(contr.head().getBody());
            posX += deltaX;
            posY += deltaY;
            gameGrid.add(contr.head().getBody(), posX, posY);
            contr.head().setPos(posX, posY);

            //Update for rest of body
            int tempSize = contr.getSize();
            if (tempSize > 1) {
                for (int x = 1; x < tempSize; x++) {
                    Rectangle rect = contr.getCurrent(x);
                    gameGrid.getChildren().remove(rect);
                    int xP = contr.getOldX(x - 1);
                    int yP = contr.getOldY(x - 1);
                    gameGrid.add(rect, xP, yP);
                    contr.update(x, xP, yP);

                }
            }

            //FOOD WAS FOUND
            if (posX == foodPosX && posY == foodPosY) {
                contr.growLast();
                gameGrid.add(contr.getCurrent(tempSize - 1),
                        contr.getOldX(tempSize - 1), contr.getOldY(tempSize - 1));
                placeFood();
            }

            //BODY CRASH
            for (int x = 1; x < tempSize; x++) {
                if (posX == contr.getX(x) && posY == contr.getY(x)) {
                    die();
                }
            }
        }
    }

    //Detects Key Presses
    public void keyPressedProcess(KeyEvent event) {
        //If you GameOver and Restart
        if (!start && dead && event.getCode() == KeyCode.ENTER) {
            pause.setText("Нажмите [Enter], чтобы прервать");
            gameOver.setText("");
            loop.play();
            start = true;
            dead = false;
        } else if (!start && !dead) {
            pause.setText("Нажмите [Enter], чтобы прервать");
            loop.play();
            start = true;
        }

        //If Enter is pressed, game will pause
        if (event.getCode() == KeyCode.ENTER) {
            pause.setText("Нажмите любую кнопку, чтобы продолжить");
            loop.stop();
            start = false;
        }

        //Changes direction to UP when up/W is pressed
        if (deltaY == 0 && (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP)) {
            deltaX = 0;
            deltaY = -1;
        } else if (deltaY == 0 && (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN)) {
            deltaX = 0;
            deltaY = 1;
        } else if (deltaX == 0 && (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT)) {
            deltaX = -1;
            deltaY = 0;
        } else if (deltaX == 0 && (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT)) {
            deltaX = 1;
            deltaY = 0;
        }

        //Closes program when escape is pressed
        if (event.getCode() == KeyCode.ESCAPE) {
            System.exit(0);
        }
    }

    public void fillGrid() {
        for (int x = 0; x < gridSizeSquared; x++) {
            gameGrid.addColumn(x, new Rectangle(12, 12, Color.GRAY));
            for (int y = 1; y < gridSizeSquared; y++) {
                gameGrid.addRow(y, new Rectangle(12, 12, Color.GRAY));
            }
        }
    }

    public void placeFood() {
        Random rPos = new Random();
        int newPosX = rPos.nextInt(gridSizeSquared);
        int newPosY = rPos.nextInt(gridSizeSquared);
        foodPosX = newPosX;
        foodPosY = newPosY;
        gameGrid.getChildren().remove(food);
        gameGrid.add(food, newPosX, newPosY);
    }

    //Game Over
    public void die() {

        int size = contr.getSize();

        //First Removes all but the head from the grid
        for (int x = size - 1; x > 0; x--) {
            gameGrid.getChildren().remove(contr.getCurrent(x));
        }

        //Removes all but the head from the arrayList
        if (size > 1) {
            contr.cutLength();
        }

        //Pauses Game
        start = false;
        dead = true;
        loop.stop();

        gameOver.setText("Игра окончена");
        pause.setText("Нажмите Enter, чтобы перезапустить игру.");

        //Generates new Position for the snake
        posX = new Random().nextInt(gridSizeSquared);
        posY = new Random().nextInt(gridSizeSquared);

        //Places Snake in new Position
        gameGrid.getChildren().remove(contr.head().getBody());
        gameGrid.add(contr.head().getBody(), posX, posY);
        contr.update(0, posX, posY);
    }

}
