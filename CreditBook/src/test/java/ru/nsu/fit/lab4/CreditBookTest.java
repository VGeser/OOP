package ru.nsu.fit.lab4;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CreditBookTest {
    public static Stream<Arguments> argumentProvider() {
        //Credit Book 1 (I am not the best student)
        CreditBook creditBook1 = new CreditBook();
        Subject history = new Subject("History");
        history.setDuration(new byte[]{1});
        history.setGrades(new byte[]{0, 5, 0, 0, 0, 0, 0, 0, 0});
        creditBook1.addSubject(history);
        Subject imperative = new Subject("Imperative", new byte[]{1, 2});
        imperative.setGrade((byte) 1, (byte) 3);
        imperative.setGrade((byte) 2, (byte) 4);
        creditBook1.addSubject(imperative);
        Subject declarative = new Subject("Declarative", new byte[]{1, 2});
        declarative.setGrades(new byte[]{0, 3, 4, 0, 0, 0, 0, 0, 0});
        creditBook1.addSubject(declarative);
        Subject discrete = new Subject("Discrete");
        discrete.setDuration(new byte[]{1, 2});
        discrete.setGrade((byte) 1, (byte) 5);
        discrete.setGrade((byte) 2, (byte) 3);
        creditBook1.addSubject(discrete);
        creditBook1.setQualification((byte) 4);
        //creditBooks.add(creditBook1);
        //Credit Book 2 (Perfect)
        ArrayList<Subject> subjects = new ArrayList<>();
        Subject russian = new Subject("Russian", new byte[]{1});
        russian.setGrades(new byte[]{0, 5, 0, 0, 0, 0, 0, 0, 0});
        subjects.add(russian);
        Subject eng = new Subject("English", new byte[]{1, 2, 3});
        eng.setGrades(new byte[]{0, 5, 5, 5, 0, 0, 0, 0, 0});
        subjects.add(eng);
        Subject math = new Subject("Math");
        math.setDuration(new byte[]{1, 2, 3, 4});
        math.setGrades(new byte[]{0, 5, 5, 5, 5, 0, 0, 0, 0});
        subjects.add(math);
        CreditBook creditBook2 = new CreditBook(subjects);
        creditBook2.setQualification((byte) 5);
        return Stream.of(Arguments.of(creditBook1, false, false, (byte) 2, 3.857143f, 3, "Discrete"),
                Arguments.of(creditBook2, true, true, (byte) 3, 5f, 2, "Math"));
    }

    @ParameterizedTest
    @MethodSource("argumentProvider")
    void compare(CreditBook source, boolean bAns1, boolean bAns2,
                 byte bytAns, float fAns, int iAns, String sAns) {
        assertEquals(bAns1, source.excellent());
        assertEquals(bAns2, source.highScholarship(bytAns));
        assertEquals(fAns, source.averageGrade());
        assertEquals(sAns, source.getSubjects().get(iAns).getName());
    }
}