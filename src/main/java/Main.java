package ru.nsu.fit.lab2;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        String reference = scanner.nextLine();
        FileInputStream stream = new FileInputStream(fileName);
        BufferedInputStream input = new BufferedInputStream(stream);
        Finder finder = new Finder();
        ArrayList<Integer> res = finder.subStringFinder(input, reference);
        int size = res.size();
        if (size == 0) {
            System.out.println("No occurrences found");
        } else {
            for (Integer re : res) {
                System.out.println(re);
            }
        }
    }
}

