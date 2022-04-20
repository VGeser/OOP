package ru.nsu.fit.lab8;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Containers {
    private final BlockingQueue warehouse;
    private final BlockingQueue orderQueue;
    private final List<Integer> doneOrders;
    private int currentOrder;

    public BlockingQueue getWarehouse() {
        return warehouse;
    }

    public List<Integer> getDoneOrders() {
        return doneOrders;
    }

    public Containers(int warehouseCapacity) {
        currentOrder = 0;
        warehouse = new LinkedBlockingDeque(warehouseCapacity);
        orderQueue = new LinkedBlockingDeque(warehouseCapacity * 10);
        doneOrders = new LinkedList();
    }

    void placeOrder(int num) {
        int temp = currentOrder;
        for (int i = 0; i < num; i++) {
            try {
                orderQueue.put(temp);
            } catch (InterruptedException e) {
                return;
            }
            System.out.println("Got order " + temp);
            temp++;
        }
        currentOrder = temp;
    }

    int getOrder() throws InterruptedException {
        return (int) orderQueue.take();
    }

    void putToWarehouse(int order) {
        synchronized (warehouse) {
            boolean res = warehouse.offer(order);
            if (!res) {
                while (!warehouse.offer(order)) {
                    try {
                        warehouse.wait();
                        if (!warehouse.contains(order)) {
                            warehouse.offer(order);
                        }
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
            warehouse.notify();
        }
        System.out.println("Order " + order + " put to warehouse");
    }

    int[] grabFromWarehouse(int len) {
        int [] res = new int[len];
        Arrays.fill(res, -1);
        synchronized (warehouse) {
            if (warehouse.isEmpty()) {
                while (warehouse.isEmpty()) {
                    try {
                        warehouse.wait();
                    } catch (InterruptedException e) {
                        return res;
                    }
                }
            }
            int size = Math.min(len, warehouse.size());
            for (int i = 0; i < size; i++) {
                try {
                    res[i] = (int) warehouse.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            warehouse.notify();
            System.out.println("Retrieved orders from warehouse" + size);
        }
        return res;
    }

    void confirmOrder(int order) {
        if (!doneOrders.contains(order))
            doneOrders.add(order);
    }

}
