//package src.main.java;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

//TODO: BufferedReader
//TODO: Rabin-Karp
//TODO: test files
public class Finder {
    LinkedList<Integer> candidate = new LinkedList<>();
    byte stopper=0;
    public void CandidateCreator(InputStreamReader in, int len, int iter) {
        try {
            if (iter != 0) {
                for (int i = 0; i < len - 1; i++) {
                    candidate.set(i, candidate.get(i + 1));
                }
                candidate.set(len - 1, in.read());
            } else {
                for (int i = 0; i < len; i++) {
                    candidate.set(i, in.read());
                }
            }
        } catch (IOException ioException) {
            stopper=1;
        }
    }

    public ArrayList<Integer> SubStringFinder(InputStreamReader in, String subString) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        int len = subString.length();
        int counter = 0;
        CandidateCreator(in, len, counter);
        while (stopper==0){
            if ((candidate.toString()).equals(subString)) {
                arrayList.add(counter);
                counter++;
            }
        }
        return arrayList;
    }
}

