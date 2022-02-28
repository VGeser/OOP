package ru.nsu.fit.lab7;

import java.util.ArrayList;
import java.util.List;

public class WithAPI {
    public boolean apiPrime(int[] arr) {
        Decider decider = Decider.getInstance();
        List<Integer> arrListed = new ArrayList<>(arr.length);
        for (int i : arr) {
            arrListed.add(i);
        }
        return arrListed.parallelStream().anyMatch(decider::isNotPrime);
    }
}
