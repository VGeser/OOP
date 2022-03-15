package ru.nsu.fit.lab8;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class Pizzeria {
    //constructing parameters
    private final int chefNum;
    private final int delivNum;
    private final int capacity;

    //shared data structures
    private int busy = 0;
    private int[] warehouse;
    private Integer orderID = 0;
    private Queue<Integer> orderQueue;

    //required access from subclasses and big class
    private final Random random = new Random();
    private Chef[] chefContainer;
    private Delivery[] deliveryContainer;
    private ExecutorService chefExecutor;
    private ExecutorService deliveryExecutor;

    public Pizzeria(int capacity, int chefNum, int delivNum) {
        this.chefNum = chefNum;
        this.delivNum = delivNum;
        this.capacity = capacity;
        this.warehouse = new int[capacity];
        Arrays.fill(warehouse, -1);
        orderQueue = new LinkedList<>();
        this.chefExecutor = Executors.newFixedThreadPool(chefNum);
        this.deliveryExecutor = Executors.newFixedThreadPool(delivNum);
    }

    public void placeOrder() {
        synchronized (orderQueue) {
            orderQueue.add(orderID);
            notify();
        }
        System.out.println("Your order number is " + orderID);
        synchronized (orderID) {
            orderID++;
        }
    }

    public void startPizzeria() {
        chefContainer = new Chef[chefNum];
        for (int i = 0; i < chefNum; i++) {
            Chef cur = new Chef();
            cur.setExperience(random.nextInt(50));
            cur.hasOrder = false;
            chefContainer[i] = cur;
        }
        deliveryContainer = new Delivery[delivNum];
        for (int i = 0; i < delivNum; i++) {
            Delivery cur = new Delivery();
            cur.setBaggageCapacity(random.nextInt(5));
            cur.hasOrder = false;
            deliveryContainer[i] = cur;
        }
        Manager manager = new Manager();
        //TODO: change conditions!!!!!
        while(true){
            manager.run();
        }
        //manager.interrupt();
       /*
        pool.shutdown();
        try {
            if (!pool.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
        }
        *
        */
    }

    class Manager implements Runnable {

        //TODO: adequate first free task finder
        //if no free return -1
        private int firstFree(Runnable[] array) {
            return 0;
        }

        @Override
        public void run() {
            if (orderQueue.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                int currentOrder = orderQueue.remove();
                int freeChef = firstFree(chefContainer);
                //completable future????
                //i cant wait on a runnable container,
                //but i need to know if and when a chef is free
                chefContainer[freeChef].setCurrentOrder(currentOrder);
                chefExecutor.execute(chefContainer[freeChef]);
                int freeCourier= firstFree(deliveryContainer);
                deliveryExecutor.execute(deliveryContainer[freeCourier]);
            }
        }
    }


    class Chef implements Runnable {
        public void setExperience(int experience) {
            this.experience = experience;
        }

        private int experience;
        public boolean hasOrder;

        public void setCurrentOrder(int currentOrder) {
            this.currentOrder = currentOrder;
            hasOrder = true;
        }

        private int currentOrder;

        //shuffle?
        private int nextFreeRoom() {
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
                    int next = nextFreeRoom();
                    warehouse[next] = currentOrder;
                    notify();
                    busy++;
                    System.out.println("Order " + currentOrder + " put to warehouse");
                    hasOrder = false;
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

    class Delivery implements Runnable {
        public void setBaggageCapacity(int baggageCapacity) {
            this.baggageCapacity = baggageCapacity;
            this.baggage = new int[baggageCapacity];
        }

        public boolean hasOrder;
        private int baggageCapacity;
        private int[] baggage;
        private int taken = 0;

        private void takeOrders() {
            hasOrder = true;
            int i = 0;
            while (taken < baggageCapacity || i < capacity) {
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

        @Override
        public void run() {
            synchronized (warehouse) {
                if (busy > 0) {
                    takeOrders();
                    notify();
                    int temp = taken;
                    for (int i = 0; i < temp; i++) {
                        try {
                            sleep(random.nextInt(20));
                            System.out.println("Order " + baggage[i] + " delivered");
                            taken--;
                            baggage[i] = 0;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    hasOrder = false;
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
