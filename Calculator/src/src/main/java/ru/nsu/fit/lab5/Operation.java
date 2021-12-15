package ru.nsu.fit.lab5;

import java.util.Stack;

abstract class Operation {
    int argsNum;

    abstract double calculate(Stack<Double> args);

    public static OperationFactory builder() {
        return null;
    }
}

class Sum extends Operation {
    Sum() {
        this.argsNum = 2;
    }

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

class Sub extends Operation {
    Sub() {
        this.argsNum = 2;
    }

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

class Mult extends Operation {
    Mult() {
        this.argsNum = 2;
    }

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

class Div extends Operation {
    Div() {
        this.argsNum = 2;
    }

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

class Sqrt extends Operation {
    Sqrt() {
        this.argsNum = 1;
    }

    public static OperationFactory builder() {
        return Sqrt::new;
    }

    @Override
    double calculate(Stack<Double> args) {
        double arg1 = args.pop();
        return Math.sqrt(arg1);
    }
}

class Log extends Operation {
    Log() {
        this.argsNum = 1;
    }

    public static OperationFactory builder() {
        return Log::new;
    }

    @Override
    double calculate(Stack<Double> args) {
        double arg1 = args.pop();
        return Math.log(arg1);
    }
}

class Pow extends Operation {
    Pow() {
        this.argsNum = 2;
    }

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

class Sin extends Operation {
    Sin() {
        this.argsNum = 1;
    }

    public static OperationFactory builder() {
        return Sin::new;
    }

    @Override
    double calculate(Stack<Double> args) {
        double arg1 = args.pop();
        return Math.sin(arg1);
    }
}

class Cos extends Operation {
    Cos() {
        this.argsNum = 1;
    }

    public static OperationFactory builder() {
        return Cos::new;
    }

    @Override
    double calculate(Stack<Double> args) {
        double arg1 = args.pop();
        return Math.cos(arg1);
    }
}
