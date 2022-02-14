package ru.nsu.fit.lab7;

public class Sequential {
    public boolean sequentPrime(int []arr){
        Decider decider = Decider.getInstance();
        for (int number:arr) {
            if(!decider.isPrime(number))return true;
        }
        return false;
    }
}
