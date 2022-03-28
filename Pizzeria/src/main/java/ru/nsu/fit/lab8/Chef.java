package ru.nsu.fit.lab8;

import static java.lang.Thread.sleep;
import static ru.nsu.fit.lab8.PizzeriaDispatcher.*;

public class Chef implements Runnable {
    private final int experience;
    private int currentOrder;

    public Chef(int experience, int order) {
        this.experience = experience;
        this.currentOrder = order;
    }

    private int nextFreeRoom() {
        int size = warehouseCapacity;
        synchronized (warehouse) {
            for (int i = 0; i < size; i++) {
                if (warehouse[i] == -1)
                    return i;
            }
        }
        return -1;
    }

    @Override
    public void run() {
        System.out.println("Cooking order " + currentOrder);
        try {
            sleep(experience);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Order " + currentOrder + " cooked");

        synchronized(warehouse){
            try{
                while(!(busy < warehouseCapacity)){
                    wait();
                }
                int next = nextFreeRoom();
                warehouse[next] = currentOrder;
                notify();
                busy++;
                System.out.println("Order " + currentOrder + " put to warehouse");
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        //currentOrder = PizzeriaDispatcher.getOrder();
    }

}
