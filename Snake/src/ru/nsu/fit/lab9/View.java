package ru.nsu.fit.lab9;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class View extends Application {
    private GridPane field;
    private Controller controller;
    private Circle food;
    private Stage stage;
    private Menu menu;
    private final Label scoreLabel = new Label();

    @FXML
    private MenuBar menuBar;

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
        stage.setWidth(670);
        stage.setHeight(500);
        field = new GridPane();
        stage.show();
    }

    private void fillGrid(GridPane pane) {
        for (int x = 0; x < 40; x++) {
            pane.addColumn(x, new Rectangle(12, 12, Color.LAVENDER));
            for (int y = 1; y < 40; y++) {
                pane.addRow(y, new Rectangle(12, 12, Color.LAVENDER));
            }
        }
    }

    void makeField() {
        scoreLabel.setTextFill(Color.LIGHTSALMON);
        scoreLabel.setFont(new Font("Microsoft YaHei Light",18));
        this.setLabel((byte) 0,true);
        //TODO: display correct score
        VBox root = new VBox(scoreLabel,field);
        root.setBackground(new Background(new BackgroundFill(Color.LAVENDER, CornerRadii.EMPTY, Insets.EMPTY)));
        fillGrid(field);
        field.setAlignment(Pos.CENTER);
        stage.setTitle("Little Snake");
        Scene game = new Scene(root);
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

    void setLabel(byte score, boolean mode){
        if(!mode){
            scoreLabel.setText(String.valueOf(score));
        }else{
            scoreLabel.setText("Zen Mode");
        }
    }

    void displayLost() throws IOException {
        controller = new Controller(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/lose-screen.fxml"));
        loader.setController(controller);
        Scene loseScene = new Scene(loader.load());
        stage.setScene(loseScene);
        stage.setTitle("Little Snake");
        stage.setWidth(670);
        stage.setHeight(500);
        field = new GridPane();
        stage.show();
    }

    void displayWon() throws IOException {
        controller = new Controller(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/win-screen.fxml"));
        loader.setController(controller);
        Scene loseScene = new Scene(loader.load());
        stage.setScene(loseScene);
        stage.setTitle("Little Snake");
        stage.setWidth(670);
        stage.setHeight(500);
        field = new GridPane();
        stage.show();
    }
}
