package ru.nsu.fit.lab5;

import java.util.Stack;

public class Tan extends Operation {
    Tan() {
        super(1);
    }

    public static OperationFactory builder() {
        return Tan::new;
    }

    @Override
    double calculate(Stack<Double> args) {
        double arg1 = args.pop();
        return Math.tan(arg1);
    }
}
