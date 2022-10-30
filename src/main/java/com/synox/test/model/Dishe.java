package com.synox.test.model;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Data
public class Dishe {

    private final String name;
    private final Set<Ingredient> ingredients;
    private Boolean isTest;

}
