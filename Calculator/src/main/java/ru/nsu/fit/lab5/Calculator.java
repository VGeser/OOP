package ru.nsu.fit.lab5;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Calculator {

    private Stack<String> stackStr = new Stack<>();
    private Map<String, OperationFactory> operations;

    //default constructor
    public Calculator() {
        operations = new HashMap<>();
        operations.put("+", Sum.builder());
        operations.put("-", Sub.builder());
        operations.put("*", Mult.builder());
        operations.put("/", Div.builder());
        operations.put("sqrt", Sqrt.builder());
        operations.put("log", Log.builder());
        operations.put("pow", Pow.builder());
        operations.put("sin", Sin.builder());
        operations.put("cos", Cos.builder());
    }

    //allows using your own set of operations
    public Calculator(Map<String, OperationFactory> operations) {
        this.operations = operations;
    }

    /**
     * method for adding operations
     *
     * @param name      - symbol of operation
     * @param operation - instance of interface to construct operation
     */
    public void addOperation(String name, OperationFactory operation) {
        operations.put(name, operation);
    }

    /**
     * creates stack of tokens from given expression
     *
     * @param in - given expression
     */
    private void makeStack(String in) {
        if (in == null) {
            throw new IllegalArgumentException("Expression must be non-empty");
        }
        String[] tokens = in.split(" ");
        int len = tokens.length - 1;
        for (int i = len; i >= 0; i--) {
            stackStr.push(tokens[i]);
        }
    }

    /**
     * calculates temporary result and calls further calculations
     * needs no arguments because is based on global state of stack
     *
     * @return current branch result
     */
    public double parse() {
        Stack<Double> currentArgs = new Stack<>();
        String token = stackStr.pop();
        try {
            return Double.parseDouble(token);
        } catch (NumberFormatException exception) {
            if (!operations.containsKey(token)) {
                throw new IllegalArgumentException("Unknown operation");
            }
            OperationFactory currentInterface = operations.get(token);
            Operation current = currentInterface.utility();
            int num = current.argsNum;
            for (int i = 0; i < num; i++) {
                double next = parse();
                currentArgs.push(next);
            }
            return current.calculate(currentArgs);
        }
    }

    /**
     * calculates result recursively
     *
     * @param in - given expression
     * @return - calculated double
     */
    public double calculate(String in) {
        makeStack(in);
        return parse();
    }
}

