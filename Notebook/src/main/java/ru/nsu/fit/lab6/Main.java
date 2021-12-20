package ru.nsu.fit.lab6;

import java.util.ArrayList;

import static java.lang.System.exit;

public class Main {
    /**
     * closing a program with warning
     * does not throw an exception, because it is rather a misunderstanding
     */
    public static void closer() {
        System.out.println("Usage: notebook -command <args>");
        exit(-1);
    }

    public static void main(String[] args) {
        int len = args.length;
        if (len == 0) {
            closer();
        } else {
            String command = args[0];
            Notebook notebook = new Notebook();
            switch (command) {
                case "-add":
                    if (len == 1) {
                        closer();
                    } else {
                        String[] notes = new String[len - 1];
                        System.arraycopy(args, 1, notes, 0, len - 1);
                        notebook.add(notes);
                    }
                case "-rm":
                    if (len == 1) {
                        closer();
                    } else {
                        String[] notes = new String[len - 1];
                        System.arraycopy(args, 1, notes, 0, len - 1);
                        notebook.remove(notes);
                    }
                case "-show":
                    ArrayList<String> out;
                    if (len == 1) {
                        out = notebook.show();

                    } else {
                        String[] keywords = new String[len - 3];
                        System.arraycopy(args, 3, keywords, 0, len - 3);
                        out = notebook.show(args[1], args[2], keywords);
                    }
                    for (String str:out) {
                        System.out.println(str);
                    }
                default:
                    throw new IllegalArgumentException("Unknown command");
            }
        }
    }
}
