package ru.nsu.fit.lab8;

import java.util.Random;

import static java.lang.Thread.sleep;
import static ru.nsu.fit.lab8.PizzeriaDispatcher.*;

public class Delivery implements Runnable {
    private int baggageCapacity;
    private int[] baggage;
    private int taken;
    private Random r;

    public Delivery(int capacity) {
        baggageCapacity = capacity;
        baggage = new int[capacity];
        taken = 0;
        r = new Random(777);
    }

    private void takeOrders() {
        int i = 0;
        synchronized (warehouse) {

            while (!(taken < baggageCapacity || i < PizzeriaDispatcher.warehouseCapacity)) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (warehouse[i] != -1) {
                    System.out.println("Order " + warehouse[i] + " taken to delivery");
                    baggage[taken] = warehouse[i];
                    taken++;
                    busy--;
                    warehouse[i] = -1;
                }
                i++;
            }

        }

    }

    @Override
    public void run() {
        synchronized (warehouse) {
            while (!(busy > 0)) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }takeOrders();
            notify();
        }
        int temp = taken;
        for (int i = 0; i < temp; i++) {
            try {
                sleep(r.nextInt(20));
                System.out.println("Order " + baggage[i] + " delivered");
                taken--;
                baggage[i] = 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
