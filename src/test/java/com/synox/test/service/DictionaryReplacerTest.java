package com.synox.test.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryReplacerTest {

    DictionaryReplacer dictionaryReplacer;

    @BeforeEach
    public void init() {
        dictionaryReplacer = new DictionaryReplacer();
    }

    @Test
    public void shouldStringReplacer_ThrowIllegalArgumentException_ifStrIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dictionaryReplacer.stringReplacer(null, Collections.emptyList()));
        assertEquals("The input string is null or empty", exception.getMessage());
    }

    @Test
    public void shouldStringReplacer_ThrowIllegalArgumentException_ifStrIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dictionaryReplacer.stringReplacer("", Collections.emptyList()));
        assertEquals("The input string is null or empty", exception.getMessage());
    }

    @Test
    public void shouldStringReplacer_returnTheSameStrAsOnEntry_ifDictionaryIsNull() {
        String str = "$temp$ here comes the name $name$";
        assertEquals(str, dictionaryReplacer.stringReplacer(str, null));
    }


}
