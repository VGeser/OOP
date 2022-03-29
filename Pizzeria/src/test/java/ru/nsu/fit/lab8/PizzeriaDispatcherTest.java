package ru.nsu.fit.lab8;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class PizzeriaDispatcherTest {
    @Test
    void warehouseTest() {
        PizzeriaDispatcher pd = new PizzeriaDispatcher(11, 4, 5);
        try {
            pd.placeOrder(15);
            pd.start();
            pd.placeOrder(320);
            pd.placeOrder(7);
            pd.placeOrder(10);
            pd.placeOrder(75);
            pd.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int[] arr = new int[5];
        Arrays.fill(arr,-1);
        assertArrayEquals(arr,pd.warehouse);
    }
}