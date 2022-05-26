package ru.nsu.fit.lab9;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class View extends Application {
    private GridPane field;
    private Controller controller;
    private Circle food;
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        controller = new Controller(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/start-screen.fxml"));
        loader.setController(controller);
        Scene startScene = new Scene(loader.load());
        this.stage = stage;
        stage.setScene(startScene);
        stage.setTitle("Little Snake");
        stage.setWidth(600);
        stage.setHeight(400);
        field = new GridPane();
        stage.show();
    }

    private void fillGrid(GridPane pane) {
        for (int x = 0; x < 40; x++) {
            pane.addColumn(x, new Rectangle(12, 12, Color.GRAY));
            for (int y = 1; y < 40; y++) {
                pane.addRow(y, new Rectangle(12, 12, Color.GRAY));
            }
        }
    }

    void makeField() {
        fillGrid(field);
        field.setAlignment(Pos.CENTER);
        stage.setTitle("Little Snake");
        Scene game = new Scene(field);
        game.setOnKeyPressed(controller::controlPressedKey);
        stage.setScene(game);
        stage.sizeToScene();
        stage.show();
    }

    void setFood(int newFoodX, int newFoodY) {
        if (food == null) {
            food = new Circle(6, Color.YELLOW);
        } else {
            field.getChildren().remove(food);
        }
        field.add(food, newFoodX, newFoodY);
    }

    void erase(Snake<Rectangle> snake) {
        for (Rib<Rectangle> r : snake.getBody()) {
            field.getChildren().remove(r.getBody());
        }
    }

    void setSnake(Snake<Rectangle> snake) {
        for (Rib<Rectangle> r : snake.getBody()) {
            field.add(r.getBody(), r.getXpos(), r.getYpos());
        }
    }

    void displayLost() throws IOException {
        controller = new Controller(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/lose-screen.fxml"));
        loader.setController(controller);
        Scene loseScene = new Scene(loader.load());
        stage.setScene(loseScene);
        stage.setTitle("Little Snake");
        stage.setWidth(600);
        stage.setHeight(400);
        field = new GridPane();
        stage.show();
    }
}
