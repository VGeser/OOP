package ru.nsu.fit.lab7;

import java.util.Arrays;
import java.util.Random;

public class WithThreads {
    private int[] arr;
    private boolean[] takenNumbers;
    private int len;
    private final Random rand = new Random();
    private boolean locked = false;
    private boolean done = false;
    private final Decider decider = Decider.getInstance();

    class Running extends Thread {
        public Running(String s) {
            super(s);
        }

        @Override
        public synchronized void run() {
            while (!done && !Thread.interrupted()) {
                if (locked) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    locked = true;
                    int curIndex = getCurrent();
                    if (curIndex == -1) {
                        done = true;
                        locked = false;
                        notify();
                    } else {
                        takenNumbers[curIndex] = true;
                        if (decider.isNotPrime(arr[curIndex])) {
                            throw new RuntimeException();
                        } else {
                            notify();
                        }
                    }
                }
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        arr[i] = (arr[i] + arr[j]) - (arr[j] = arr[i]);
    }

    private void shuffle(int r, int[] arr) {
        for (int i = 0; i < r; ++i) {
            int k = (rand.nextInt()) % (i + 1);
            swap(arr, k, i);
        }
    }

    public void setArr(int[] arr) {
        len = arr.length;
        shuffle(len, arr);
        this.arr = arr;
    }

    public WithThreads(int[] arr) {
        setArr(arr);
    }

    private int getCurrent() {
        for (int i = 0; i < len; i++) {
            if (!takenNumbers[i]) {
                return i;
            }
        }
        return -1;
    }

    public boolean threadPrime(int threadsNum, int[] arr) {
        takenNumbers = new boolean[len];
        Arrays.fill(takenNumbers, false);
        Running[] threads = new Running[threadsNum];
        for (int i = 0; i < threadsNum; i++) {
            threads[i] = new Running("thread " + i);
        }
        Thread.UncaughtExceptionHandler handler = (running, exception) -> {
            exception.printStackTrace();
            for (Running t : threads) {
                t.interrupt();
            }
        };
        for (Running t : threads) {
            t.setUncaughtExceptionHandler(handler);
        }
        for (Running t : threads) {
            t.start();
        }
        return false;
    }
}
