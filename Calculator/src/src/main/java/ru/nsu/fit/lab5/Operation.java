package ru.nsu.fit.lab5;

import java.util.Stack;

interface Operation {
    double calculate(Stack<String> stackStr);

    class Sum implements Operation {
        public double calculate(Stack<String> tokens) {
            String token = tokens.pop();
            double d1 = Calculator.getDouble(token);
            token = tokens.pop();
            double d2 = Calculator.getDouble(token);
            return d1 + d2;
        }
    }

    class Sub implements Operation {
        public double calculate(Stack<String> tokens) {
            String token = tokens.pop();
            double d1 = Calculator.getDouble(token);
            token = tokens.pop();
            double d2 = Calculator.getDouble(token);
            return d1 - d2;
        }
    }

    class Mult implements Operation {
        public double calculate(Stack<String> tokens) {
            String token = tokens.pop();
            double d1 = Calculator.getDouble(token);
            token = tokens.pop();
            double d2 = Calculator.getDouble(token);
            return d1 * d2;
        }
    }

    class Div implements Operation {
        public double calculate(Stack<String> tokens) {
            String token = tokens.pop();
            double d1 = Calculator.getDouble(token);
            token = tokens.pop();
            double d2 = Calculator.getDouble(token);
            return d1 / d2;
        }
    }

    class Log implements Operation {
        public double calculate(Stack<String> tokens) {
            String token = tokens.pop();
            double d1 = Calculator.getDouble(token);
            return Math.log(d1);
        }
    }

    class Pow implements Operation {
        public double calculate(Stack<String> tokens) {
            String token = tokens.pop();
            double d1 = Calculator.getDouble(token);
            token = tokens.pop();
            double d2 = Calculator.getDouble(token);
            return Math.pow(d1, d2);
        }
    }

    class Sqrt implements Operation {
        public double calculate(Stack<String> tokens) {
            String token = tokens.pop();
            double d1 = Calculator.getDouble(token);
            return Math.sqrt(d1);
        }
    }

    class Sin implements Operation {
        public double calculate(Stack<String> tokens) {
            String token = tokens.pop();
            double d1 = Calculator.getDouble(token);
            return Math.sin(d1);
        }
    }

    class Cos implements Operation {
        public double calculate(Stack<String> tokens) {
            String token = tokens.pop();
            double d1 = Calculator.getDouble(token);
            return Math.cos(d1);
        }
    }
}
