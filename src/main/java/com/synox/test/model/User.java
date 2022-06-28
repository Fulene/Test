package com.synox.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.synox.test.view.Views;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @JsonView(Views.WithRestrictionView.class)
    private int id;

    @JsonProperty("userName")
    @JsonView(Views.WithRestrictionView.class)
    @Value("${test.firstname}")
    private String name;

    @JsonView(Views.WithoutRestrictionView.class)
    private String lastname;

    @JsonView(Views.WithoutRestrictionView.class)
    private List<Dishe> favoriteDishes;


    @JsonIgnore
    public String getFullName() { // Si "get%" => pris en compte dans résultat Json
        return name + " " + lastname;
    }

}
