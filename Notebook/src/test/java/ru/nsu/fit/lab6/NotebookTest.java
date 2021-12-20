package ru.nsu.fit.lab6;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

class NotebookTest {

    @Test
    public void testSpecification(){
        Notebook notebook = new Notebook();
        notebook.add(new String[]{"My note","Very important note"});
        notebook.remove(new String[]{"My note"});
        ArrayList<String> expect = new ArrayList<>();
        expect.add("Very important note");
        assertEquals(expect,notebook.show());
    }

    @Test
    public void testSearch(){
        Notebook notebook = new Notebook();
        notebook.add(new String[]{"I had the best day with you today",
                                  "Today was a fairytale",
                                  "Fearless - Taylor's version",
                                  "the lakes",
                                  "folklore deluxe"});
        notebook.add(new String[]{"From The Vault","15 Grammys"});
        ArrayList<String> expect = new ArrayList<>();
        expect.add("I had the best day with you today");
        expect.add("the lakes");
        LocalDateTime from = LocalDateTime.now();
        from=from.minusDays(3);
        String fromStr =from.toString();
        LocalDateTime to = LocalDateTime.now();
        to = to.plusMinutes(10);
        String toStr =to.toString();
        ArrayList<String> act = notebook.show(fromStr,toStr,new String[]{"the "});
        assertEquals(expect,act);
        expect.clear();
        assertEquals(expect,notebook.show(fromStr,toStr,new String[]{"10"," hello ", "vaULt"}));
    }
}