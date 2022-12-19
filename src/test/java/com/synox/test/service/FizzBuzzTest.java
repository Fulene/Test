package com.synox.test.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class FizzBuzzTest {

    FizzBuzz fizzBuzz;

    @BeforeEach
    public void initFizzBuzzClass() {
        fizzBuzz = new FizzBuzz();
    }

    @Test
    public void shouldProcess_returnNull_ifNumbersIsNull() {
        assertNull(fizzBuzz.process(null));
    }

    @Test
    public void shouldProcess_return1_ifNumbersContainsOnly1() {
        assertEquals("1", fizzBuzz.process(List.of(1)));
    }

    @Test
    public void shouldProcess_return2_ifNumbersContainsOnly2() {
        assertEquals("2", fizzBuzz.process(List.of(2)));
    }

    @Test
    public void shouldProcess_return12_ifNumbersContains12() {
        assertEquals("12", fizzBuzz.process(List.of(1, 2)));
    }

    @Test
    public void shouldProcess_returnFizz_ifNumbersContainsOnly3() {
        assertEquals("Fizz", fizzBuzz.process(List.of(3)));
    }

    @Test
    public void shouldProcess_return12Fizz_ifNumbersContains1To3() {
        List<Integer> inputList = IntStream.range(1, 3 +1)
            .boxed()
            .collect(Collectors.toList());

        assertEquals("12Fizz", fizzBuzz.process(inputList));
    }

    @Test
    public void shouldProcess_returnBuzz_ifNumbersContainsOnly5() {
        assertEquals("Buzz", fizzBuzz.process(List.of(5)));
    }

    @Test
    public void shouldProcess_return12Fizz4Buzz_ifNumbersContains1To5() {
        List<Integer> inputList = IntStream.range(1, 5 +1)
            .boxed()
            .collect(Collectors.toList());

        assertEquals("12Fizz4Buzz", fizzBuzz.process(inputList));
    }

    @Test
    public void shouldProcess_returnFizz_onlyIfNumberInListIsMultipleOf3AndIsNotMultipleOf5() {
        for (int i = 1; i <= 100; i++) {
            if (i%3 == 0 && i%5 != 0) assertEquals("Fizz", fizzBuzz.process(List.of(i)));
        }
    }

    @Test
    public void shouldProcess_returnBuzz_onlyIfNumberInListIsMultipleOf5AndIsNotMultipleOf3() {
        for (int i = 1; i <= 100; i++) {
            if (i%5 == 0 && i%3 != 0) assertEquals("Buzz", fizzBuzz.process(List.of(i)));
        }
    }

    @Test
    public void shouldProcess_returnBuzz_onlyIfNumberInListIsMultipleOf5AndIsMultipleOf3() {
        for (int i = 1; i <= 100; i++) {
            if (i%5 == 0 && i%3 == 0) assertEquals("FizzBuzz", fizzBuzz.process(List.of(i)));
            else assertNotEquals("FizzBuzz", fizzBuzz.process(List.of(i)));
        }
    }

    @Test
    public void shouldProcess_returnTheExpectedResult() {
        List<Integer> inputList = IntStream.range(1, 100 + 1)
            .boxed()
            .collect(Collectors.toList());
        String expectedResult = "1    2    Fizz    4    Buzz    Fizz    7    8    Fizz    Buzz    11    Fizz    13    14    FizzBuzz    16    17    Fizz    19    Buzz    Fizz    22    23    Fizz    Buzz    26    Fizz    28    29    FizzBuzz    31    32    Fizz    34    Buzz    Fizz    37    38    Fizz    Buzz    41    Fizz    43    44    FizzBuzz    46    47    Fizz    49    Buzz    Fizz    52    53    Fizz    Buzz    56    Fizz    58    59    FizzBuzz    61    62    Fizz    64    Buzz    Fizz    67    68    Fizz    Buzz    71    Fizz    73    74    FizzBuzz    76    77    Fizz    79    Buzz    Fizz    82    83    Fizz    Buzz    86    Fizz    88    89    FizzBuzz    91    92    Fizz    94    Buzz    Fizz    97    98    FizzBuzz".replace(" ", "");

        assertEquals(expectedResult, fizzBuzz.process(inputList));
    }

}
