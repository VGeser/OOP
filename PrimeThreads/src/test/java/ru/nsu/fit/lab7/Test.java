package ru.nsu.fit.lab7;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

class Test {
    public static Stream<Arguments> argumentProvider() {
        int[] ar1 = new int[100];
        Arrays.fill(ar1, 13);
        ar1[99] = 4;
        return Stream.of(
                //tests from specification
                Arguments.of(new int[]{6, 8, 7, 13, 9, 4}, true),
                Arguments.of(new int[]{6997901, 6997927, 6997937, 6997967,
                        6998009, 6998029, 6998039, 6998051, 6998053}, false),

                Arguments.of(ar1, true),
                Arguments.of(new int[]{103549, 3, Integer.MAX_VALUE, 3, 10000019},false),
                Arguments.of(new int[]{102217, 102229, 102233, 102241, 102251, 10000019,
                        102259, 102293, 102299, 102301, 102317, 102329, 102337, 102359,
                        102367, 102397, 102407, 102409, 102433, 102437, 102451, 102461,
                        102481, 102497, 102499, 102503, 102523, 102533, 102539, 102547,
                        102551, 102559, 102563, 102587, 102593, 102607, 102611, 102643,
                        102647, 102653, 102667, 102673, 102677, 102679, 102701, 102761,
                        102763, 102769, 102793, 102797, 102811, 102829, 102841, 102859,
                        102871, 102877, 102881, 102911, 102913, 102929, 102931, 102953,
                        102967, 102983, 103001, 103007, 103043, 103049, 103067, 103069,
                        103079, 103087, 103091, 103093, 103099, 103123, 103141, 103171,
                        103177, 103183, 103217, 103231, 103237, 103289, 103291, 103307,
                        103319, 103333, 103349, 103357, 103387, 103391, 103393, 103399,
                        103409, 103421, 103423, 103451, 103457, 103471, 103483, 103511,
                        103529, 103549, 3, Integer.MAX_VALUE, 3, 10000001, 103567,
                        103573, 103577, 103583, 103591, 103613, 103619, 103643, 3,
                        103687, 103699, 103703, 103723, 103769, 103787, 103801, 103811,
                        103813, 103837, 103841, 103843, 103867, 103889, 103903, 103913,
                        103919, 103951, 103963, 103967, 103969, 103979, 103981, 103991,
                        103993, 103997, 104003, 104009, 104021, 104033, 104047, 104053,
                        104059, 104087, 104089, 104107, 104113, 104119, 104123, 104147,
                        104149, 104161, 104173, 104179, 104183, 104207, 104231, 104233,
                        104239, 104243, 104281, 104287, 104297, 104309, 104311, 104323,
                        104327, 104347, 104369, 104381, 104383, 104393, 104399, 104417,
                        104459, 104471, 104473, 104479, 104491, 104513, 104527, 104537,
                        104543, 104549, 104551, 104561, 104579, 104593, 104597, 104623,
                        104639, 104651, 104659, 104677, 104681, 104683, 104693, 104701,
                        103651, 103657, 103669, 103681, 104711, 104717, 104729}, true),
                Arguments.of(new int[]{0}, true),
                Arguments.of(new int[]{}, false)
        );
    }

    @ParameterizedTest
    @MethodSource("argumentProvider")
    void testSequent(int[] inArr, boolean res) {
        long start1 = System.nanoTime();
        Sequential sequential = new Sequential();
        boolean answ = sequential.sequentPrime(inArr);
        long end1 = System.nanoTime();
        System.out.println("Sequential time: " + (end1-start1) + "\n");
        Assert.assertEquals(answ, res);
    }

    @ParameterizedTest
    @MethodSource("argumentProvider")
    void testWithThreads(int[] inArr, boolean res) {
        byte[] quantity = {2, 4, 6, 8};
        for (byte num : quantity) {
            long start1 = System.nanoTime();
            WithThreads concurrent = new WithThreads(inArr);
            boolean answ = concurrent.threadPrime(num);
            long end1 = System.nanoTime();
            System.out.println(num + " Threads time: " + (end1-start1) + "\n");
            Assert.assertEquals(answ, res);
        }
    }

    @ParameterizedTest
    @MethodSource("argumentProvider")
    void testAPI(int[] inArr, boolean res) {
        long start1 = System.nanoTime();
        WithAPI parallel = new WithAPI();
        boolean answ = parallel.apiPrime(inArr);
        long end1 = System.nanoTime();
        System.out.println("Parallel Stream time: " + (end1-start1) + "\n");
        Assert.assertEquals(answ, res);
    }

}