package ru.nsu.fit.lab7;

import java.util.Arrays;
import java.util.stream.IntStream;

public class WithAPI {
    public boolean apiPrime(int[] arr) {
        Decider decider = Decider.getInstance();
        IntStream stream = Arrays.stream(arr);
        return stream.parallel().anyMatch(decider::isNotPrime);
    }
}
