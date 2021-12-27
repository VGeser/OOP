package ru.nsu.fit.lab5;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Enter expression:");
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            System.out.println(calculator.calculate(str));
        }
    }
}

