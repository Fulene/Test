package com.synox.test.service;

import java.util.List;
import java.util.Map;

public class DictionaryReplacer {

    public String stringReplacer(String str, List<Map<String, String>> dictionary) {
        if (str == null || str.equals("")) throw new IllegalArgumentException("The input string is null or empty");
        return str;
    }

}
