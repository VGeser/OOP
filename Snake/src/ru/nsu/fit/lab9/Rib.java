package ru.nsu.fit.lab9;

public class Rib<T> {
    T getBody() {
        return body;
    }

    void setBody(T body) {
        this.body = body;
    }

    private T body;
    private int xpos;
    private int ypos;
    private int oldXpos;
    private int oldYpos;

    Rib(int x, int y) {
        oldXpos = x;
        xpos = x;
        oldYpos = y;
        ypos = y;
    }

    void setPos(int x, int y) {
        oldXpos = xpos;
        oldYpos = ypos;
        xpos = x;
        ypos = y;
    }

    int getXpos() {
        return xpos;
    }

    int getYpos() {
        return ypos;
    }

    int getOldXpos() {
        return oldXpos;
    }

    int getOldYpos() {
        return oldYpos;
    }
}

