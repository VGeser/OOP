package ru.nsu.fit.lab7;

import java.util.Arrays;
import java.util.Collections;

public class WithThreads {
    private int[] arr;
    private boolean[] takenNumbers;
    private int len;
    private boolean locked = false;
    private boolean done = false;
    private boolean res = false;
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
                        notify();
                    } else {
                        takenNumbers[curIndex] = true;
                        if (decider.isNotPrime(arr[curIndex])) {
                            res = true;
                            done = true;
                            throw new RuntimeException();
                        } else {
                            locked=false;
                            notify();
                        }
                    }
                }
            }
        }
    }


    public void setArr(int[] arr) {
        len = arr.length;
        Collections.shuffle(Arrays.asList(arr));
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

    public boolean threadPrime(int threadsNum) {
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
        byte j=1;
        for (int i = 0; i < threadsNum; i+=2) {
            threads[i].start();
            try {
                threads[j].sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threads[j].start();
            j+=2;
        }
        return res;
    }
}
