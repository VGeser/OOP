package ru.nsu.fit.lab6;

import com.google.gson.Gson;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

public class Notebook {
    private final File file = new File("notes.json");
    private final DateTimeFormatter dateTimeFormatter;
    private final Gson gson;

    public Notebook() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                System.out.println("Can't create a file for your notes");
            }
        }
        dateTimeFormatter = ISO_LOCAL_DATE_TIME;
        gson = new Gson();
    }

    /**
     * utility method for adding notes to a file
     *
     * @param append - whether to rewrite the file
     * @param elems  - notes to be written
     */
    private void utilAdd(boolean append, ArrayList<String> elems) {
        try {
            FileWriter fileWriter = new FileWriter(file, append);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            for (String elem : elems) {
                bufWriter.write(elem);
                bufWriter.write("\n");
            }
            bufWriter.flush();
            bufWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't write to a file notes.json");
        }

    }

    /**
     * adding notes to the end of file
     *
     * @param notes - content of notes
     */
    public void add(String[] notes) {
        LocalDateTime now = LocalDateTime.now();
        ArrayList<String> formattedNotes = new ArrayList<>();
        for (String iter : notes) {
            Note note = new Note(dateTimeFormatter.format(now), iter);
            String formatted = gson.toJson(note);
            formattedNotes.add(formatted);
        }
        utilAdd(true, formattedNotes);
    }

    /**
     * removing certain notes by rewriting the file
     *
     * @param notes - notes to be removed
     */
    public void remove(String[] notes) {
        ArrayList<String> out = new ArrayList<>();
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(reader);
            String line;
            int len = notes.length;
            while ((line = bufReader.readLine()) != null) {
                Note noteObj = gson.fromJson(line, Note.class);
                String formatted = noteObj.getText();
                int cnt = 0;
                for (String note : notes) {
                    if (!formatted.equals(note)) {
                        cnt++;
                    }
                }
                if (cnt == len) {
                    out.add(line);
                }
            }
            bufReader.close();
            reader.close();
            utilAdd(false, out);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't find or read file notes.json");
        }
    }

    /**
     * show all the notes of the file
     * no need for sorting bc each note is added to the end of file
     *
     * @return - list of notes
     */
    public ArrayList<String> show() {
        ArrayList<String> output = new ArrayList<>();
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(reader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                Note note = gson.fromJson(line, Note.class);
                output.add(note.getText());
            }
            bufReader.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't find or read file notes.json");
        }
        return output;
    }

    /**
     * show only certain notes
     * this method relies on optimization of default 'contains' function
     *
     * @param time1    - notes older than this time
     * @param time2    - notes younger than this time
     * @param keywords - notes containing these substrings
     * @return - list of notes' contents
     */
    public ArrayList<String> show(String time1, String time2, String[] keywords) {
        ArrayList<String> texts;
        ArrayList<String> out = new ArrayList<>();
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(reader);
            String line;
            LocalDateTime time;
            LocalDateTime before = LocalDateTime.parse(time1, dateTimeFormatter);
            LocalDateTime after = LocalDateTime.parse(time2, dateTimeFormatter);
            texts = new ArrayList<>();
            while ((line = bufReader.readLine()) != null) {
                Note note = gson.fromJson(line, Note.class);
                time = LocalDateTime.parse(note.getTimestamp(), dateTimeFormatter);
                if ((time.isAfter(before)) && (time.isBefore(after))) {
                    texts.add(note.getText());
                }
            }
            for (String text : texts) {
                int cnt = 0;
                for (String keyword : keywords) {
                    if (text.contains(keyword)) {
                        cnt++;
                    }
                }
                if (cnt != 0) {
                    out.add(text);
                }
            }
            bufReader.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't find or read file notes.json");
        }
        return out;
    }
}
