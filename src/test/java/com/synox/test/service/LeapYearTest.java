package com.synox.test.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LeapYearTest {

    LeapYear leapYear;

    @BeforeEach
    public void initLeapYearClass() {
        leapYear = new LeapYear();
    }

    @Test
    public void shouldIsYearALeap_ThrowIllegalArgumentException_ifYearIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> leapYear.isYearALeap(null));
        assertEquals("The year is not defined", exception.getMessage());
    }

    @Test
    public void shouldIsYearALeap_returnTrue_ifYearIsMultipleOf400() {
        assertTrue(leapYear.isYearALeap(1200));
        assertTrue(leapYear.isYearALeap(800));
        assertTrue(leapYear.isYearALeap(2000));
    }

    @Test
    public void shouldIsYearALeap_returnFalse_ifYearIsMultipleOf100AndNotMultipleOf400() {
        assertFalse(leapYear.isYearALeap(1700));
        assertFalse(leapYear.isYearALeap(1800));
        assertFalse(leapYear.isYearALeap(1900));
        assertFalse(leapYear.isYearALeap(2100));
    }

    @Test
    public void shouldIsYearALeap_returnTrue_ifYearIsMultipleOf4AndNotMultipleOf100() {
        assertTrue(leapYear.isYearALeap(2008));
        assertTrue(leapYear.isYearALeap(2012));
        assertTrue(leapYear.isYearALeap(2016));
    }

    @Test
    public void shouldIsYearALeap_returnFalse_ifYearIsNotMultipleOf4() {
        assertFalse(leapYear.isYearALeap(2017));
        assertFalse(leapYear.isYearALeap(2018));
        assertFalse(leapYear.isYearALeap(2019));
        assertFalse(leapYear.isYearALeap(2021));
        assertTrue(leapYear.isYearALeap(4));
        assertTrue(leapYear.isYearALeap(40));
        assertTrue(leapYear.isYearALeap(44));
        assertTrue(leapYear.isYearALeap(4000));
    }

    @Test
    public void shouldIsYearALeap_returnFalse_ifYearIsMultipleOf4AndMultipleOf100AndNotMultipleOf400() {
        assertFalse(leapYear.isYearALeap(200));
        assertFalse(leapYear.isYearALeap(2200));
    }

}
