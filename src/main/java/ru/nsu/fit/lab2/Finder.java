package ru.nsu.fit.lab2;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Finder {
    /**
     * function that calculates prefixes numbers
     *
     * @param str - given char array, converting to string makes no sense
     * @param len - length of string, needed because the function is called
     *            on every iteration of search and _.length indexes the whole string
     * @return - returns array of prefixes
     */
    private int[] prefixFunc(char[] str, int len) {
        int[] Pi = new int[len]; //potentially unsafe
        Pi[0] = 0;
        for (int i = 1; i < len; ++i) {
            int j = Pi[i - 1];
            while (j > 0 && str[i] != str[j]) {
                j = Pi[j - 1];
            }
            if (str[i] == str[j]) {
                ++j;
            }
            Pi[i] = j;
        }
        return Pi;
    }

    //a flag to stop reading from input stream
    byte stopper = 0;

    /**
     * creates a char array to be tested
     *
     * @param in   - input stream to get data from
     * @param len  - length of candidate
     * @param prev - previous candidate, because it stores data we can no longer access via stream
     * @param move - the distance to move position to or -1 if first iteration
     * @return - returns array of chars as a candidate
     */
    public char[] candidateCreator(BufferedInputStream in, int len, char[] prev, int move) {
        try {
            if (move == -1) {
                for (int i = 0; i < len; i++) {
                    prev[i] = (char) in.read();
                }
            } else {
                System.arraycopy(prev, move, prev, 0, len - move);
                for (int i = move; i < len; i++) {
                    prev[i] = (char) in.read();
                }
            }
        } catch (IOException ioException) {
            stopper = 1;
        }
        return prev;
    }

    /**
     * Knut-Moris-Pratt algorithm
     *
     * @param in        - stream to read from
     * @param subString - reference substring we are looking for
     * @return - returns a list of indexes where occurrences are found
     */
    public ArrayList<Integer> subStringFinder(BufferedInputStream in, String subString) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        int len = subString.length();
        char[] candidate = new char[len];
        Arrays.fill(candidate, '0');
        candidate = candidateCreator(in, len, candidate, -1);
        int[] refPrefix = prefixFunc(candidate, len);
        int[] candPrefix = prefixFunc(candidate, len);
        int counter = 0;
        int temp;
        while (stopper == 0) {
            temp = move(len,candPrefix,refPrefix);
            if (temp == len) {
                arrayList.add(counter);
            }
            counter += temp;
            candidate = candidateCreator(in, len, candidate, temp);
            candPrefix=prefixFunc(candidate,len);
        }
        return arrayList;
    }

    /**
     * iterating function that compares prefixes
     * @param len - length of arrays
     * @param candPrefix - current array being tested
     * @param refPrefix - reference array
     * @return - returns the number of characters to move reader to
     */
    private int move(int len, int[] candPrefix, int[] refPrefix) {
        for (int i = 0; i < len; i++) {
            if (candPrefix[i] != refPrefix[i]) {
                return i + 1;
            }
        }
        return len;
    }
}

