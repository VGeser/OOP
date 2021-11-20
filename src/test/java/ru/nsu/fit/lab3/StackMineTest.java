package ru.nsu.fit.lab3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackMineTest {
    //i had issues with gradle, therefore recommend running each test separately
    @Test
    @DisplayName("Test from requirements")
    public void reqTest() throws InvalidQueryException {
        StackMine<Integer> stack = new StackMine<>(5);
        stack.push(2);
        stack.push(7);
        stack.pushStack(new Integer[]{4, 8});
        int res = stack.pop();
        assertEquals(8, res);
        assertArrayEquals(new Integer[]{7, 4}, stack.popStack(2));
        assertEquals(1, stack.size());
    }

    @Test
    @DisplayName("Empty container test")
    public void nullTest() {
        StackMine<Byte> byteStackMine = new StackMine<>(0);
        assertThrows(InvalidQueryException.class , () -> {
            byteStackMine.pop();
            byteStackMine.popStack(7);
        });
    }

    @Test
    @DisplayName("String test")
    public void strTest() throws InvalidQueryException {
        StackMine<String> stackMine = new StackMine<>(3);
        stackMine.push("Honeymoon");
        stackMine.pushStack(new String[]{"is", "the"});
        stackMine.push("worst");
        String str = stackMine.pop();
        assertEquals("worst", str);
        stackMine.pushStack(new String[]{"best", "album", "of", "all", "time"});
        assertEquals(8, stackMine.size());
    }

    //class for next test
    private static class Example {
        double val1;
        char val2;
        float[] arrVal;

        Example() {
            this.val1 = 3.1415926;
            this.val2 = 'Z';
            this.arrVal = new float[]{8.9f, 13.517f, 233.0006f};
        }

        Example(double val1, char val2, float[] arrVal) {
            this.arrVal = arrVal;
            this.val2 = val2;
            this.val1 = val1;
        }

    }

    @Test
    @DisplayName("Complicated class test")
    public void classTest() throws InvalidQueryException {
        Example ex1 = new Example();
        Example ex2 = new Example(5.216, 'A', new float[]{3.111f, 9.999f});
        StackMine<Example> exampleStack = new StackMine<>(5);
        exampleStack.push(ex1);
        exampleStack.push(ex2);
        assertEquals(ex2, exampleStack.pop());
    }
}