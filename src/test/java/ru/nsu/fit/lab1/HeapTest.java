package ru.nsu.fit.lab1;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

@RunWith(Parameterized.class)
class HeapTest {
    int[] input;
    private Heap heap;

    public HeapTest(int[] input, Heap heap) {
        //super();
        this.input = input;
        this.heap = heap;
    }

    @Before
    public void initial() {
        Heap heap = new Heap();
    }

    @Parameterized.Parameters
    public static Collection nums() {
        return Arrays.asList(new Object[][]{
                {5, 4, 3, 2, 1},
                {Integer.MIN_VALUE, 0, Integer.MAX_VALUE, 0, 0, 57, 13, 210},
                //sequence generated on website random.org in case you don't trust the default Random() function
                {17229033, -22312981, -50636086, -15230070, 17119415, -77287330,
                        -84167470, 43203665, 94423698, -99469061, 38307918, 53766538, -34307053, -11663419,
                        56337950, 44709790, -80177113, -2013416, 67093249, 7601148, 30818390, -16528086,
                        98026994, -26647769, -74525067, 89815398, 66755050, -40709786, -72214880, 93413992,
                        -15591768, 33219632, 38730676, 77452370, 95325472, 13031411, -37524404, -33380585,
                        -43391894, 75760768, -86039713, -83542066, -36976056, 19117741, 62716922, -61575027,
                        -85124602, -78552820, -89818298, 12114279},

        });
    }

    @org.junit.jupiter.api.Test
    @Test
    @DisplayName("testOne")
    public void oneTest() {
        heap.caller(input, input.length);
        int[] answer = Arrays.copyOf(input, input.length);
        Arrays.sort(answer);
        Assert.assertArrayEquals(answer, input);
    }
}
    class RandomHeapTest{
        @org.junit.jupiter.api.Test
        @Test
        @DisplayName("testRandom")
        public void testRandom() {
            Heap heap = new Heap();
            int[] randarr = new int[10000];
            Random r = new Random();
            for (int i = 0; i < 10000; i++) {
                randarr[i] = r.nextInt();
            }
            heap.caller(randarr,randarr.length);
            int[] answer = Arrays.copyOf(randarr, randarr.length);
            Arrays.sort(answer);
            Assert.assertArrayEquals(answer, randarr);
        }
    }
