package ru.nsu.fit.lab8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class PizzeriaDispatcher {
    private final int chefNum;
    private final int delivNum;
    private final ThreadLocalRandom rand;
    private ExecutorService chefExecutor;
    private ExecutorService deliveryExecutor;
    private final Containers commonContainers;
    private final Chef[] chefs;
    private final Delivery[] delivs;


    public Containers getCommonContainers() {
        return commonContainers;
    }

    public PizzeriaDispatcher(int chefNum, int delivNum, int warehouseCapacity) {
        this.chefNum = chefNum;
        this.delivNum = delivNum;
        rand = ThreadLocalRandom.current();
        commonContainers = new Containers(warehouseCapacity);
        chefs = new Chef[chefNum];
        delivs = new Delivery[delivNum];
    }

    public void orderPizza(int num) {
        commonContainers.placeOrder(num);
    }

    public void start() throws InterruptedException {
        chefExecutor = Executors.newFixedThreadPool(chefNum);
        deliveryExecutor = Executors.newFixedThreadPool(delivNum);
        for (int i = 0; i < chefNum; i++) {
            Chef chef = new Chef(rand.nextInt(2, 51), commonContainers.getOrder(), commonContainers);
            chefs[i] = chef;
            chefExecutor.execute(chef);
        }
        for (int i = 0; i < delivNum; i++) {
            Delivery delivery = new Delivery(rand.nextInt(2, 7), commonContainers);
            delivs[i] = delivery;
            deliveryExecutor.execute(delivery);
        }

    }

    public void stop(){
        for (Chef c : chefs) {
            c.stop = true;
        }
        stop(chefExecutor);
        for (Delivery d : delivs) {
            d.stop = true;
        }
        stop(deliveryExecutor);
    }

    private void stop(ExecutorService pool) {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
        }
    }

}
