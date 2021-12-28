package ru.nsu.fit.lab6;

import java.util.ArrayList;

import static java.lang.System.exit;

public class Main {
    /**
     * closing a program with warning
     * does not throw an exception, because it is rather a misunderstanding
     */
    public static void close() {
        System.out.println("Usage: notebook -command <args>");
        exit(-1);
    }

    public static void main(String[] args) {
        int len = args.length;
        if (len == 0) {
            close();
        } else {
            int from;
            String command = args[0];
            Notebook notebook;
            if (command.equals("-name")) {
                if (len <= 2) {
                    close();
                }
                String name = args[1];
                command = args[2];
                notebook = new Notebook(name);
                from = 2;
            } else {
                notebook = new Notebook();
                from = 0;
            }
            switch (command) {
                case "-add":
                    if (len == 1) {
                        close();
                    } else {
                        String[] notes = new String[len - (from + 1)];
                        System.arraycopy(args, from + 1, notes, 0, len - (from + 1));
                        notebook.add(notes);
                    }
                case "-rm":
                    if (len == 1) {
                        close();
                    } else {
                        String[] notes = new String[len - (from + 1)];
                        System.arraycopy(args, from + 1, notes, 0, len - (from + 1));
                        notebook.remove(notes);
                    }
                case "-show":
                    ArrayList<String> out;
                    if (len == 1) {
                        out = notebook.show();

                    } else {
                        String[] keywords = new String[len - (from + 3)];
                        System.arraycopy(args, from + 3, keywords, 0, len - (from + 3));
                        out = notebook.show(args[from + 1], args[from + 2], keywords);
                    }
                    for (String str : out) {
                        System.out.println(str);
                    }
                default:
                    throw new IllegalArgumentException("Unknown command");
            }
        }
    }
}