package com.synox.test.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter @Setter
public class Dishe {

    private final String name;
    private final List<Ingredient> ingredients;
    private Boolean isTest;

}
