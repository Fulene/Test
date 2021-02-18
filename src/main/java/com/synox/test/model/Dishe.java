package com.synox.test.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter @Setter
public class Dishe {

    private String name;
    private List<Ingredient> ingredients;

}
