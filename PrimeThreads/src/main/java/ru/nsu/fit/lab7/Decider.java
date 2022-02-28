package ru.nsu.fit.lab7;

import java.util.Arrays;

public class Decider {
    int ceiling = (int) Math.round(Math.sqrt(Integer.MAX_VALUE)) + 1;
    //safe because ceiling is always about 46341
    private final boolean[] cashed = new boolean[ceiling];

    private static Decider instance = null;

    private Decider() {
        Arrays.fill(cashed, true);
        cashed[0] = false;
        cashed[1] = false;

        for (int i = 2; i < ceiling; i++) {
            if (cashed[i]) {
                for (int j = i * 2; j < ceiling; j += i)
                    cashed[j] = false;
            }
        }
    }

    public static Decider getInstance() {
        if (instance == null)
            instance = new Decider();

        return instance;
    }

    public boolean isNotPrime(int num) {
        if (num <= ceiling) {
            return !cashed[num];
        } else {
            int border = (int) Math.round(Math.sqrt(num)) + 1;
            for (int i = 2; i < border; i++) {
                if (cashed[i] && num % i == 0) {
                    return true;
                }
            }
            return false;
        }
    }
}
