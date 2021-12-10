package ru.nsu.fit.lab5;

import java.util.Stack;

public class Calculator {
    private Stack<String> stackStr = new Stack<>();

    /**
     * creates stack of tokens from given expression
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
     * method for better exception handling
     * checks if token can be made double
     * @param token - token being checked
     * @return - true if token is number
     */
    private boolean isNum(String token) {
        if (token == null) return false;
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * calculates temporary result and calls further calculations
     * @param token - current token
     * @return - calculated current branch result
     */
    private double getDouble(String token) {
        if (isNum(token)) {
            return Double.parseDouble(token);
        }
        Operation operation = getOperation(token);
        return operation.calculate(stackStr);
    }

    /**
     * method that defines operation from token
     * @param name - given token
     * @return - instance of operation implementing class
     */
    public Operation getOperation(String name) {
        return switch (name) {
            case ("+") -> new Operation.Sum();
            case ("-") -> new Operation.Sub();
            case ("*") -> new Operation.Mult();
            case ("/") -> new Operation.Div();
            case ("sin") -> new Operation.Sin();
            case ("cos") -> new Operation.Cos();
            case ("sqrt") -> new Operation.Sqrt();
            case ("log") -> new Operation.Log();
            case ("pow") -> new Operation.Pow();
            default -> throw new IllegalArgumentException("Unknown operation");
        };
    }

    /**
     * calculates result recursively
     * @param in - given expression
     * @return - calculated double
     */
    public double calculate(String in) {
        makeStack(in);
        String token = stackStr.pop();
        Operation operation = getOperation(token);
        return operation.calculate(stackStr);
    }
}
