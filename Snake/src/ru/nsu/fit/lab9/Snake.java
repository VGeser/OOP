package ru.nsu.fit.lab9;

import java.util.ArrayList;

public class Snake<T> {
    private ArrayList<Rib<T>> body;

    Snake() {
        body = new ArrayList<>();
    }

    ArrayList<Rib<T>> getBody() {
        return body;
    }

    void grow(Rib<T> rib) {
        body.add(rib);
    }

    Rib<T> getHead() {
        return body.get(0);
    }
}