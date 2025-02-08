package com.pin.app.challenge.service;

import com.pin.app.challenge.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MathServiceUnitTest {

    private MathService mathService;

    @BeforeEach
    void setUp() {
        mathService = new MathService();
    }

    @Test
    void testCalculateAverageAge_ShouldReturnCorrectAverage() {
        List<Integer> ages = Arrays.asList(25, 30, 35, 40);
        double result = mathService.calculateAverageAge(ages);
        assertEquals(32.5, result);
    }

    @Test
    void testCalculateAverageAge_EmptyList_ShouldReturnZero() {
        List<Integer> ages = Collections.emptyList();
        double result = mathService.calculateAverageAge(ages);
        assertEquals(0, result);
    }

    @Test
    void testCalculateStandardDeviation_ShouldReturnCorrectValue() {
        List<Integer> ages = Arrays.asList(25, 30, 35, 40);
        double average = mathService.calculateAverageAge(ages);
        double result = mathService.calculateStandardDeviation(ages, average);
        assertEquals(5.59, result, 0.01);
    }

    @Test
    void testCalculateStandardDeviation_EmptyList_ShouldReturnZero() {
        List<Integer> ages = Collections.emptyList();
        double result = mathService.calculateStandardDeviation(ages, 0);
        assertEquals(0, result);
    }

    @Test
    void testEstimateDeathDate_ShouldReturnCorrectDate() {
        Client client = new Client();
        client.setBirthDay(LocalDate.of(1990, 5, 20));
        LocalDate result = mathService.estimateDeathDate(client);
        assertEquals(LocalDate.of(2065, 5, 20), result);
    }
}