package com.synox.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.synox.test.view.Views;
import lombok.*;

import java.util.List;

@Getter @Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NonNull
    @JsonView(Views.WithRestrictionView.class)
    private int id;

    @JsonProperty("userName")
    @NonNull
    @JsonView(Views.WithRestrictionView.class)
    private String name;

    @JsonView(Views.WithRestrictionView.class)
    private String lastname;

    @JsonView(Views.WithoutRestrictionView.class)
    private List<Dishe> favoriteDishes;


    @JsonIgnore
    public String getFullName() { // Si "get%" => pris en compte dans résultat Json
        return name + " " + lastname;
    }

}
