package com.synox.test.service;

public class LeapYear {

    public boolean isYearALeap(Integer year) {
        if (year == null) throw new IllegalArgumentException("The year is not defined");
        return year % 400 == 0 || year % 4 == 0 && year % 100 != 0;
    }

}
