package com.pin.app.challenge.utils;

import com.pin.app.challenge.exception.InvalidAgeException;

import java.time.LocalDate;
import java.time.Period;

public class ClientUtils {

    public static void validateAge(int age, LocalDate birthDate) {
        if (!isAgeValid(age, birthDate)) {
            throw new InvalidAgeException("The age is not valid based on the birth date.");
        }
    }
    public static boolean isAgeValid(int age, LocalDate birthDate) {
        return age == Period.between(birthDate, LocalDate.now()).getYears();
    }
}