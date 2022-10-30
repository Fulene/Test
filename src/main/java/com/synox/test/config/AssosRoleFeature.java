package com.synox.test.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("demo")
@Getter
@Setter
@ToString
public class AssosRoleFeature {

    private List<String> role1 = new ArrayList<>();
    private List<String> role2 = new ArrayList<>();
    private List<String> role3 = new ArrayList<>();

}
