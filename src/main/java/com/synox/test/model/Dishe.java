package com.synox.test.model;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class Dishe {

    private final String name;
    private final List<Ingredient> ingredients;
    private Boolean isTest;

}
