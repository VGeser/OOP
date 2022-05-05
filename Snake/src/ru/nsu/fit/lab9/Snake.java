package ru.nsu.fit.lab9;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Snake<T> {
    private ArrayList<Rib<T>> body;
    Snake(){
        body = new ArrayList<>();
    }

    public ArrayList<Rib<T>> getBody() {
        return body;
    }

    public void cutTail(){
        body.subList(1, body.size()).clear();
    }

    public Rib<T> getHead(){
        return body.get(0);
    }
}