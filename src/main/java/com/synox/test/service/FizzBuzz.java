package com.synox.test.service;

import java.util.List;
import java.util.stream.Collectors;

public class FizzBuzz {

    public String process(List<Integer> numbers) {
        if (numbers == null) return null;
        return numbers.stream()
            .map(n -> {
                if (n % 3 == 0 && n % 5 == 0) return "FizzBuzz";
                if (n % 3 == 0) return "Fizz";
                if (n % 5 == 0) return "Buzz";
                return String.valueOf(n);
            })
            .collect(Collectors.joining());
    }

}
