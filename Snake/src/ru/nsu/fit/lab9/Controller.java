package ru.nsu.fit.lab9;

import javafx.scene.shape.Rectangle;

import java.util.Iterator;

public class Controller {
    private Snake<Rectangle> snake;

    Controller() {
        snake = new Snake<>();
    }

    public void cutLength(){
        snake.cutTail();
    }

    public void growOne(int x, int y) {
        Rib<Rectangle> r = new Rib<>(x,y);
        r.setBody(new Rectangle(x, y));
        snake.getBody().add(r);
    }

    public int getSize(){
        return snake.getBody().size();
    }

    public int getOldX(int index){
        return snake.getBody().get(index).getOldXpos();
    }

    public int getOldY(int index){
        return snake.getBody().get(index).getOldYpos();
    }

    public int getX(int index){
        return snake.getBody().get(index).getXpos();
    }

    public int getY(int index){
        return snake.getBody().get(index).getYpos();
    }

    public void growLast() {
        int size = this.getSize();
        int x = this.getOldX(size-1);
        int y = this.getOldY(size-1);
        this.growOne(x, y);
    }

    public Rectangle getCurrent(int index){
        return snake.getBody().get(index).getBody();
    }

    public void update(int index,int x,int y){
        snake.getBody().get(index).setPos(x,y);
    }

    public Rib<Rectangle> head() {
        return snake.getHead();
    }

}
