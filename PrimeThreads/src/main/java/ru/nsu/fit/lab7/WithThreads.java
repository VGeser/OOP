package ru.nsu.fit.lab7;

import java.util.Arrays;
import java.util.Random;

public class WithThreads extends Thread {
    private int[] arr;
    private boolean[] takenNumbers;
    private int len;
    Random rand = new Random();
    private boolean semaphore = false;


    public void setArr(int[] arr) {
        this.arr = arr;
        len = arr.length;
    }

    public WithThreads(int[] arr) {
        this.arr = arr;
        len = arr.length;
    }

    private int getCurrent(){
        int curIndex = rand.nextInt(len);

        //if already taken, proceed to the first not taken
        while (takenNumbers[curIndex]) {
            curIndex = rand.nextInt(len);
        }
        return curIndex;
    }

    class Shared {
        private void setTakenNumbers(int index){
            takenNumbers[index] = true;
        }
        private void setSemaphore(){
            semaphore = true;
        }
    }
    final Shared shared = new Shared();

    public void run() {
        Decider decider = Decider.getInstance();
        int cur = getCurrent();
        int current = arr[cur];
        synchronized (shared){
            shared.setTakenNumbers(cur);
            if(decider.isPrime(current)){
                shared.setSemaphore();
            }
        }notify();
    }

    public boolean threadPrime() {
        //TODO: number of threads as an attribute
        //here are 2 threads
        takenNumbers = new boolean[len];
        Arrays.fill(takenNumbers, false);
        WithThreads thread1 = new WithThreads(arr);
        WithThreads thread2 = new WithThreads(arr);
        thread1.start();
        thread2.start();
        //TODO: interrupt all threads if semaphore == 1
        return false;
    }
}
