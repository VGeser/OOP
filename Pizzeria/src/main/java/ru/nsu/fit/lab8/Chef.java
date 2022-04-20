package ru.nsu.fit.lab8;

import static java.lang.Thread.sleep;

public class Chef implements Runnable {
    private final int experience;
    private int currentOrder;
    private final Containers containers;

    public Chef(int experience, int order, Containers containers) {
        this.experience = experience;
        this.currentOrder = order;
        this.containers = containers;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Cooking order " + currentOrder + Thread.currentThread().getName());
            try {
                sleep(experience);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Order " + currentOrder + " cooked");
                containers.putToWarehouse(currentOrder);
                try {
                    currentOrder = containers.getOrder();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
