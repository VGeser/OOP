package ru.nsu.fit.lab5;

import java.util.Stack;

abstract class Operation {
    int argsNum;

    Operation(int argv) {
        this.argsNum = argv;
    }

    abstract double calculate(Stack<Double> args);

    public static OperationFactory builder() {
        return null;
    }
}

abstract class NullOperation extends Operation {
    NullOperation() {
        super(0);
    }
}

abstract class UnaryOperation extends Operation {
    UnaryOperation() {
        super(1);
    }
}

abstract class BinaryOperation extends Operation {
    BinaryOperation() {
        super(2);
    }
}

abstract class TernaryOperation extends Operation {
    TernaryOperation() {
        super(3);
    }
}

class Sum extends BinaryOperation {

    public static OperationFactory builder() {
        return Sum::new;
    }

    @Override
    double calculate(Stack<Double> args) {
        double arg1 = args.pop();
        double arg2 = args.pop();
        return arg1 + arg2;
    }
}

class Sub extends BinaryOperation {

    public static OperationFactory builder() {
        return Sub::new;
    }

    @Override
    double calculate(Stack<Double> args) {
        double arg1 = args.pop();
        double arg2 = args.pop();
        return arg2 - arg1;
    }
}

class Mult extends BinaryOperation {

    public static OperationFactory builder() {
        return Mult::new;
    }

    @Override
    double calculate(Stack<Double> args) {
        double arg1 = args.pop();
        double arg2 = args.pop();
        return arg1 * arg2;
    }
}

class Div extends BinaryOperation {

    public static OperationFactory builder() {
        return Div::new;
    }

    @Override
    double calculate(Stack<Double> args) {
        double arg1 = args.pop();
        double arg2 = args.pop();
        return arg2 / arg1;
    }
}

class Sqrt extends UnaryOperation {

    public static OperationFactory builder() {
        return Sqrt::new;
    }

    @Override
    double calculate(Stack<Double> args) {
        double arg1 = args.pop();
        return Math.sqrt(arg1);
    }
}

class Log extends UnaryOperation {

    public static OperationFactory builder() {
        return Log::new;
    }

    @Override
    double calculate(Stack<Double> args) {
        double arg1 = args.pop();
        return Math.log(arg1);
    }
}

class Pow extends BinaryOperation {

    public static OperationFactory builder() {
        return Pow::new;
    }

    @Override
    double calculate(Stack<Double> args) {
        double arg1 = args.pop();
        double arg2 = args.pop();
        return Math.pow(arg2, arg1);
    }
}

class Sin extends UnaryOperation {

    public static OperationFactory builder() {
        return Sin::new;
    }

    @Override
    double calculate(Stack<Double> args) {
        double arg1 = args.pop();
        return Math.sin(arg1);
    }
}

class Cos extends UnaryOperation {

    public static OperationFactory builder() {
        return Cos::new;
    }

    @Override
    double calculate(Stack<Double> args) {
        double arg1 = args.pop();
        return Math.cos(arg1);
    }
}
