package ru.nsu.fit.lab8;

import java.util.Arrays;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Delivery implements Runnable {
    private final int[] baggage;
    private final Random r;
    private final Containers containers;
    boolean stop;

    public Delivery(int capacity, Containers containers) {
        baggage = new int[capacity];
        Arrays.fill(baggage, -1);
        r = new Random(777);
        this.containers = containers;
        stop = false;
    }

    @Override
    public void run() {
        while (!stop) {
            containers.grabFromWarehouse(baggage);
            for (int order : baggage) {
                if (order != -1) {
                    System.out.println("Order " + order + " taken to delivery");
                }
            }
            int size = baggage.length;
            for (int i = 0; i < size; i++) {
                if (baggage[i] != -1) {
                    try {
                        sleep(r.nextInt(25));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Order " + baggage[i] + " delivered");
                    containers.confirmOrder(baggage[i]);
                    baggage[i] = -1;
                }
            }
        }
    }
}
