package ru.nsu.fit.lab1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.util.Arrays.sort;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class HeapTest {
    static public ArrayList<int[]> arrayMaker() {
        var list = new ArrayList<int[]>();
        list.add(new int[]{5, 4, 3, 2, 1});
        list.add(new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE, 0, 0, 57, 13, 210});
        //sequence generated on website random.org in case you don't trust the default Random() function
        list.add(new int[]{17229033, -22312981, -50636086, -15230070, 17119415, -77287330,
                -84167470, 43203665, 94423698, -99469061, 38307918, 53766538, -34307053, -11663419,
                56337950, 44709790, -80177113, -2013416, 67093249, 7601148, 30818390, -16528086,
                98026994, -26647769, -74525067, 89815398, 66755050, -40709786, -72214880, 93413992,
                -15591768, 33219632, 38730676, 77452370, 95325472, 13031411, -37524404, -33380585,
                -43391894, 75760768, -86039713, -83542066, -36976056, 19117741, 62716922, -61575027,
                -85124602, -78552820, -89818298, 12114279});
        list.add(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        return list;
    }

    @ParameterizedTest
    @MethodSource("arrayMaker")
    void comp(int[] array) {
        int[] res = array.clone();
        Heap heap = new Heap();
        heap.heapsort(array);
        Arrays.sort(res);
        assertArrayEquals(array, res);
    }
}

class RandomHeapTest {
    @Test
    @DisplayName("testRandom")
    public void testRandom() {
        Heap heap = new Heap();
        int[] randarr = new int[10000];
        Random r = new Random();
        for (int i = 0; i < 10000; i++) {
            randarr[i] = r.nextInt();
        }
        heap.heapsort(randarr);
        int[] answer = Arrays.copyOf(randarr, randarr.length);
        Arrays.sort(answer);
        assertArrayEquals(answer, randarr);
    }
}
