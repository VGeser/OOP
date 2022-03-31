package ru.nsu.fit.lab8;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PizzeriaDispatcherTest {
    @Test
    void warehouseTest() {
        PizzeriaDispatcher pd = new PizzeriaDispatcher(11, 4, 5);
        try {
            pd.orderPizza(15);
            pd.start();
            sleep(2000);
            pd.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0, pd.getCommonContainers().getWarehouse().size());
    }

    @Test
    void correctnessTest() {
        PizzeriaDispatcher pd = new PizzeriaDispatcher(11, 4, 5);
        try {
            pd.orderPizza(15);
            pd.start();
            sleep(2000);
            pd.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Integer> testList = new LinkedList<>();
        for (int i = 0; i < 15; i++) {
            testList.add(i);
        }
        List<Integer> res = pd.getCommonContainers().getDoneOrders();
        Collections.sort(res);
        assertEquals(testList, res);
    }
}