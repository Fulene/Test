package com.synox.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.synox.test.view.Views;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class User {

    @JsonProperty("userName")
    @JsonView(Views.WithRestrictionView.class)
    private String name;

    @JsonView(Views.WithRestrictionView.class)
    private String lastname;

    @JsonView(Views.WithRestrictionView.class)
    private int age;

    @JsonView(Views.WithoutRestrictionView.class)
    private String weight;

    @JsonView(Views.WithoutRestrictionView.class)
    private String size;


    @JsonIgnore
    public String getFullName() { // Si "get%" => pris en compte dans résultat Json
        return name + " " + lastname;
    }

    @Override
    public String toString() {
        return name + "," + lastname + "," + age + System.lineSeparator();
    }

}
