package ru.nsu.fit.lab2;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.ArrayList;

//import org.junit.jupiter.params.ParameterizedTest;

public class FinderTest {
    Finder finder = new Finder();
    static File file1 = new File("file1.txt");
    static File file2 = new File("file2.txt");
    public static ArrayList<File> fileMaker() {
        ArrayList<File> arrayList = new ArrayList<>();
        try {
            FileWriter writer1 = new FileWriter("file1.txt");
            writer1.write("I want a pie!");
            writer1.close();
            FileWriter writer2 = new FileWriter("file2.txt");
            writer2.write("I want juice!");
            writer2.close();
            arrayList.add(file1);
            arrayList.add(file2);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<String> refCreator(){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("pie");
        strings.add("pie");
        return strings;
    }

    public static ArrayList<int[]> testResult(){
        var ints = new ArrayList<int[]>();
        ints.add(new int[]{7});
        ints.add(new int[]{});
        return ints;
    }
    //there is an error I don't know how to fix:
    //"No ParameterResolver registered for parameter"
    @ParameterizedTest
    @MethodSource({"fileMaker","refCreator","testResult"})
    void comp(File file,String string,int[] ans) {
        ArrayList<Integer> res = new ArrayList<>();
        try {
            FileInputStream fIS1 = new FileInputStream(file);
            BufferedInputStream bIS1 = new BufferedInputStream(fIS1);
            res=finder.subStringFinder(bIS1,string);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int[] arr = res.stream().mapToInt(i -> i).toArray();
        Assert.assertArrayEquals(arr,ans);
    }

    @AfterAll
    static void clearer(){
        file1.delete();
        file2.delete();
    }
}