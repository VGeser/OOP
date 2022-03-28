package ru.nsu.fit.lab8;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PizzeriaDispatcher {
    private final int chefNum;
    private final int delivNum;
    private int currentOrder;
    private final Random rand;
    private Queue<Integer> orderQueue;
    private ExecutorService chefExecutor;
    private ExecutorService deliveryExecutor;
    //one warehouse for all Pizzerias
    static int[] warehouse;
    static int warehouseCapacity;
    static int busy;

    public PizzeriaDispatcher(int chefNum, int delivNum, int warehouseCapacity){
        this.chefNum = chefNum;
        this.delivNum = delivNum;
        currentOrder = 0;
        rand = new Random();
        orderQueue = new LinkedList<>();
        this.warehouseCapacity = warehouseCapacity;
        warehouse = new int[warehouseCapacity];
        Arrays.fill(warehouse, -1);
        busy=0;
    }

    /**
     * usage: placeOrder(x); start(); n times placeOrder(y); stop();
     * @param num - number of orders
     */
    public void placeOrder(int num){
        synchronized(orderQueue){
            for (int i = 0; i < num; i++) {
                orderQueue.add(currentOrder);
                currentOrder++;
                notify();
                System.out.println("Got order " + currentOrder);
            }
        }
    }

    int getOrder(){
        synchronized(orderQueue){
            if(orderQueue.isEmpty()){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return orderQueue.remove();
        }
    }

    public void start () throws InterruptedException{
      chefExecutor = Executors.newFixedThreadPool(chefNum);
      deliveryExecutor = Executors.newFixedThreadPool(delivNum);
        for (int i = 0; i < chefNum; i++) {
            Chef chef = new Chef(rand.nextInt(50),getOrder());
            chefExecutor.execute(chef);
        }
        for (int i = 0; i < delivNum; i++) {
            Delivery delivery = new Delivery(rand.nextInt(5));
            deliveryExecutor.execute(delivery);
        }

    }

    public void stop() throws InterruptedException{
        stop(chefExecutor);
        stop(deliveryExecutor);
    }

    private void stop(ExecutorService pool){
        pool.shutdown();
        try {
            if (!pool.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
        }
    }

}
