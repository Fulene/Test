package com.synox.test.service;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class RandomTestService {

    private List<String> names = Arrays.asList(null, "Mehdi", "Narj", "", null , "Aish", "Saf", null, "Asma");

    private List<Optional<String>> optionalNames =
            Arrays.asList(Optional.ofNullable(null), Optional.ofNullable("Mehdi"), Optional.ofNullable("Narj"),
                    Optional.ofNullable(null), Optional.ofNullable("Aish"), Optional.ofNullable("Saf"), Optional.ofNullable(null), Optional.ofNullable("Asma"));

    public void optionalTest() {
        getOptionalNames().forEach(n -> System.out.println(n.map(na -> "Hello " + na).orElse("null value")));
    }

    public String streamTest() {
//        return getOptionalNames().stream().map(n -> n.orElse("null value")).filter(n -> n.equals("null value")).count();
        return getOptionalNames().stream().map(n -> n.orElse("null value")).filter(n -> n.equals("null value")).collect(Collectors.joining(" | "));
    }

}
