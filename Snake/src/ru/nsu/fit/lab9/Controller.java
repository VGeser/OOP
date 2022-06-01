package ru.nsu.fit.lab9;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Controller {
    private final Snake<Rectangle> snake;
    private final ObservableList<Double> options;
    private final ObservableList<Byte> numbers;
    private final Random r;
    private final View view;
    private byte state;
    private Timeline loop;
    private final Settings settings;
    private SpinnerValueFactory<Byte> valueFactory;

    private byte deltaX;
    private byte deltaY;
    private byte score;
    private int curX;
    private int curY;
    private int foodX;
    private int foodY;

    @FXML
    private Button startButton;

    @FXML
    private ComboBox<Double> comboBox;

    @FXML
    private RadioMenuItem modeItem;

    @FXML
    private Spinner<Byte> spinner;

    public Controller(View v) {
        state = 0; //not started
        foodX = 0;
        foodY = 0;
        deltaX = 0;
        deltaY = 0;
        score = 0;
        settings = Settings.getInstance();
        snake = new Snake<>();
        r = new Random(System.currentTimeMillis());
        options = FXCollections.observableArrayList(
                1.0,
                1 / 2.0,
                3 / 10.0,
                1 / 10.0,
                1 / 15.0,
                1 / 20.0,
                1 / 30.0
        );
        ArrayList<Byte> temp = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            temp.add((byte) i);
        }
        numbers = FXCollections.observableArrayList(temp);
        this.view = v;
    }

    public void initialize() {
        startButton.setOnAction(event -> this.onStartButtonClick());
        comboBox.getItems().addAll(options);
        comboBox.setValue(settings.getSpeed());
        comboBox.setOnAction(event -> settings.setSpeed(comboBox.getValue()));
        modeItem.setSelected(settings.getMode());
        modeItem.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            settings.setMode(!newValue); //mode score (0) or zen (1)
            view.setLabel(score,newValue);
        }));
        makeSpinner();
        //TODO: traps + score label
    }

    private void makeSpinner() {
        valueFactory = new SpinnerValueFactory<>() {

            @Override
            public void decrement(int i) {
                byte currentTraps = this.getValue();
                this.setValue((byte) ((currentTraps - 1) % 8));
            }

            @Override
            public void increment(int i) {
                byte currentTraps = this.getValue();
                this.setValue((byte) ((currentTraps + 1) % 8));
            }
        };
        valueFactory.setValue((byte) 0);
        spinner.setValueFactory(valueFactory);
        spinner.valueProperty().addListener((observableValue, oldVal, newVal) -> settings.setTraps(newVal));
    }

    private void updateFood() {
        foodX = r.nextInt(40);
        foodY = r.nextInt(40);
        view.setFood(foodX, foodY);
    }

    private Rib<Rectangle> nextRib(int x, int y) {
        Rectangle rect = new Rectangle(12, 12, Color.MEDIUMORCHID);
        //12 is size, not coordinates => one for all
        Rib<Rectangle> res = new Rib<>(x, y);
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
        double loopSpeed = settings.getSpeed();
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

    private boolean isFood() {
        return curX == foodX && curY == foodY;
    }

    private void move() {
        if (crashedWall()) {
            endGame(false);
            return;
        }
        if (isFood()) {
            int size = snake.getBody().size();
            Rib<Rectangle> r = snake.getBody().get(size - 1);
            int x = r.getOldXpos();
            int y = r.getOldYpos();
            snake.grow(nextRib(x, y));
            updateFood();
            score++;
            if (settings.getMode() && score == 5) {
                endGame(true);
                return;
            }
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

    private void endGame(boolean won) {
        loop.stop();
        view.erase(snake);
        state = 4;
        snake.getBody().clear();
        if (won) {
            try {
                view.displayWon();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else {
            try {
                view.displayLost();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
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
            case ESCAPE -> System.exit(0);
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
