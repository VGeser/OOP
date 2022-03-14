package ru.nsu.fit.lab8;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class Pizzeria {
    private final int chefNum;
    private final int delivNum;
    private int capacity;
    private int busy = 0;
    int[] warehouse;
    private int orderID = 0;
    private Queue<Integer> orderQueue;
    Random random = new Random();

    public Pizzeria(int capacity, int chefNum, int delivNum) {
        this.chefNum = chefNum;
        this.delivNum = delivNum;
        this.capacity = capacity;
        this.warehouse = new int[capacity];
        Arrays.fill(warehouse, -1);
        orderQueue = new LinkedList<>();
    }

    public void placeOrder() {
        orderQueue.add(orderID);
        System.out.println("Your order number is " + orderID);
        orderID++;
    }

    public void startPizzeria() {
        ExecutorService pool = Executors.newFixedThreadPool(chefNum+delivNum+1);
        //что делает диспетчер ? как он что он куда
        for (int i = 0; i < chefNum; i++) {
            Chef cur = new Chef(random.nextInt(10));
            placeOrder();
            cur.setCurrentOrder(orderID);
            pool.execute(cur);
        }
        for (int i = 0; i < delivNum; i++) {
            Runnable cur = new Delivery(random.nextInt(50));
            pool.execute(cur);
        }
        //int currentOrder = orderQueue.remove();
        //TODO: диспетчер сам берет из очереди и отдает кому надо

        pool.shutdown();
        try {
            if (!pool.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
        }
    }

    class Manager implements Runnable{

        @Override
        public void run() {

        }
    }

    //producer
    class Chef implements Runnable {
        private final int experience;

        public void setCurrentOrder(int currentOrder) {
            this.currentOrder = currentOrder;
        }

        private int currentOrder;

        public Chef(int exp) {
            this.experience = exp;
        }

        private int nextFreeOrder() {
            for (int i = 0; i < capacity; i++) {
                if (warehouse[i] == -1)
                    return i;
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
            synchronized (warehouse) {
                if (busy < capacity) {
                    int next = nextFreeOrder();
                    warehouse[next] = currentOrder;
                    notify();
                    busy++;
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //consumer
    class Delivery implements Runnable {
        private final int baggageCapacity;
        private int[] baggage;
        private int taken = 0;

        public Delivery(int cap) {
            this.baggage = new int[cap];
            this.baggageCapacity = cap;
        }

        private void takeOrders() {
            int i = 0;
            while (taken < baggageCapacity || i < capacity) {
                if (warehouse[i] != -1) {
                    System.out.println("Order "+warehouse[i]+" taken to delivery");
                    baggage[taken] = warehouse[i];
                    taken++;
                    busy--;
                    warehouse[i] = -1;
                }
                i++;
            }
        }

        @Override
        public void run() {
            synchronized (warehouse) {
                if (busy > 0) {
                    takeOrders();
                    notify();
                    int temp =taken;
                    for (int i=0;i<temp;i++) {
                        try {
                            sleep(random.nextInt(20));
                            System.out.println("Order "+baggage[i]+" delivered");
                            taken--;
                            baggage[i]=0;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
