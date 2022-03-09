package ru.nsu.fit.lab8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pizzeria {
    private int[] dictionary; //order room - order number
    private final int chefNum;
    private final int delivNum;
    final int [] warehouse;
   // private Chef [] chefsStates; //thread pull??

    public Pizzeria(int capacity,int chefNum, int delivNum) {
        this.chefNum= chefNum;
        this.delivNum= delivNum;
        this.warehouse= new int[capacity];
    }

    public void startPizzeria(){
        ExecutorService pool = Executors.newFixedThreadPool(chefNum);
        for (int i = 0; i < chefNum; i++) {
            Runnable cur = new Chef();
            pool.execute(cur);
        }
        for (int i = 0; i < delivNum; i++) {
            Runnable cur = new Delivery();
            pool.execute(cur);
        }
        synchronized (warehouse){

        }

    }

}
