//package src.main.java;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        String reference = scanner.nextLine();
        FileInputStream stream = new FileInputStream(fileName);
        InputStreamReader input = new InputStreamReader(stream, StandardCharsets.UTF_8);
        Finder finder = new Finder();
        ArrayList<Integer> res = finder.SubStringFinder(input, reference);
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

