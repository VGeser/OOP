package ru.nsu.fit.lab9;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class Controller {
    private Snake<Rectangle> snake;
    private final Random r;
    private final View view;
    private byte state;
    private Timeline loop;

    private byte deltaX;
    private byte deltaY;
    private int curX;
    private int curY;
    private int foodX;
    private int foodY;

    @FXML
    private Button startButton;

    public Controller(View v) {
        state = 0; //not started
        foodX = 0;
        foodY = 0;
        deltaX = 0;
        deltaY = 0;
        snake = new Snake<>();
        r = new Random(2124);
        this.view = v;
    }

    public void initialize(){
        startButton.setOnAction(event -> {
            this.onStartButtonClick();
        });
    }

    private void updateFood() {
        foodX = r.nextInt(40);
        foodY = r.nextInt(40);
        view.setFood(foodX, foodY);
    }

    private Rib<Rectangle> nextRib(int x,int y){
       Rectangle rect = new Rectangle(12,12, Color.BLUE);
       //12 is size, not coordinates => one for all
       Rib <Rectangle> res = new Rib<>(x,y);
       res.setBody(rect);
       return res;
    }

    protected void onStartButtonClick() {
        view.makeField();
        updateFood();
        int xInit = r.nextInt(40);
        int yInit = r.nextInt(40);
        snake.grow(nextRib(xInit, yInit));
        curX = snake.getHead().getXpos();
        curY = snake.getHead().getYpos();
        view.setSnake(snake);
        state = 1;//playing
        double loopSpeed = 1;//1 / 10.0;
        loop = new Timeline(new KeyFrame(Duration.seconds(loopSpeed),
                event -> move()));
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }

    private boolean crashedWall() {
        if (deltaX == -1 && curX == 0) {
            deltaX = 0;
            return true;
        }
        if (deltaY == -1 && curY == 0) {
            deltaY = 0;
            return true;
        }
        if (deltaX == 1 && curX == 39) {
            deltaX = 0;
            return true;
        }
        if (deltaY == 1 && curY == 39) {
            deltaY = 0;
            return true;
        }
        return false;
    }

    /*private boolean isTangled() { //TODO: iterate without the head
    //problem: starts from head, head is head? => always yes => always lose
        for (Rib<Rectangle> r : snake.getBody()) {
            int x = r.getXpos();
            int y = r.getYpos();
            if (x == curX && y == curY)
                return true;
        }
        return false;
    }*/

    private boolean isFood() {
        return curX == foodX && curY == foodY;
    }

    private void move() {
        //(crashedWall() || isTangled())
        if  (crashedWall()){
            die();
            return;
        }
        if (isFood()) {
            int size = snake.getBody().size();
            Rib<Rectangle> r = snake.getBody().get(size - 1);
            int x = r.getOldXpos();
            int y = r.getOldYpos();
            snake.grow(nextRib(x, y));
            updateFood();
        }
        view.erase(snake);
        updateHead();
        updateBody();
        view.setSnake(snake);
    }

    private void updateHead() {
        curX += deltaX;
        curY += deltaY;
        snake.getHead().setPos(curX, curY);
    }

    private void updateBody() {
        int size = snake.getBody().size();
        if (size > 1) {
            for (int x = 1; x < size; x++) {
                int newX = snake.getBody().get(x - 1).getOldXpos();
                int newY = snake.getBody().get(x - 1).getOldYpos();
                snake.getBody().get(x).setPos(newX, newY);
            }
        }
    }

    private void die() {
        view.erase(snake);
        loop.stop();
        state = 4;
        snake.getBody().clear();
        try {
            view.displayLost();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void controlPressedKey(KeyEvent keyEvent) {
        KeyCode c = keyEvent.getCode();
        switch (c) {
            case UP, W -> {
                deltaX = 0;
                deltaY = -1;
            }
            case DOWN, S -> {
                deltaX = 0;
                deltaY = 1;
            }
            case LEFT, A -> {
                deltaX = -1;
                deltaY = 0;
            }
            case RIGHT, D -> {
                deltaX = 1;
                deltaY = 0;
            }
            case ESCAPE -> {
                System.exit(0);
            }
            case SPACE -> {
                if (state == 2) {
                    state = 1;//playing
                    loop.play();
                } else {
                    state = 2;//paused
                    loop.stop();
                }
            }
        }
    }

}
