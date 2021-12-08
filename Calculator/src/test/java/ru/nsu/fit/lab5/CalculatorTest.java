package ru.nsu.fit.lab5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    public static Stream<Arguments> argumentProvider() {
        return Stream.of(
                //test from specification
                Arguments.of("sin + - 1 2 1", 0.0),

                Arguments.of("* - 5 6 7", -7.0),

                //ln of e^p is p
                Arguments.of("log sqrt pow 2.71828 3", 1.5),

                Arguments.of("+ sin / 3.1415926 6 cos 60.0801002", -0.42496996)
        );
    }

    @ParameterizedTest
    @MethodSource("argumentProvider")
    void compare(String expression, double expect) {
        double epsilon = 0.00001d;
        double res = Calculator.calculate(expression);
        assertEquals(expect, res, epsilon);
    }

    public static ArrayList<String> errorProvider() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("! + 2 3");
        strings.add("");
        return strings;
    }

    @ParameterizedTest
    @MethodSource("errorProvider")
    void errorTest(String expression){
        assertThrows(IllegalArgumentException.class,
                () -> Calculator.calculate(expression));
    }
}