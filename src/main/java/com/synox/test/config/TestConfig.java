package com.synox.test.config;

import com.synox.test.model.Dishe;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Set;

@Configuration
public class TestConfig {

    @Bean
    public Dishe DishConf() {
        return new Dishe("Test Dish Conf", Collections.emptySet());
    }

}
