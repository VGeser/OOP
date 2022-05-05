package ru.nsu.fit.lab9;

public class Rib<T> {
    public T getBody() {
        return body;
    }

    public void setBody(T body) {
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

    public void setPos(int x, int y) {
        oldXpos = xpos;
        oldYpos = ypos;
        xpos = x;
        ypos = y;
    }
    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public int getOldXpos() {
        return oldXpos;
    }

    public int getOldYpos() {
        return oldYpos;
    }
}

