package com.pin.app.challenge.service;

import com.pin.app.challenge.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MathService {

    private static final Logger logger = LoggerFactory.getLogger(MathService.class);
    private final int LIFE_EXPECTANCY = 75;
    public double calculateAverageAge(List<Integer> ages) {
        logger.debug("Calculating average age for ages: {}", ages);
        return ages.stream()
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0);
    }

    public double calculateStandardDeviation(List<Integer> ages, double averageAge) {
        logger.debug("Calculating standard deviation for ages: {} with average age: {}", ages, averageAge);
        double variance = ages.stream()
                .mapToDouble(age -> Math.pow(age - averageAge, 2))
                .average()
                .orElse(0);
        return Math.sqrt(variance);
    }

    public LocalDate estimateDeathDate(Client client) {
        logger.debug("Estimating death date for client: {}", client.getFirstName());
        return client.getBirthDay().plusYears(LIFE_EXPECTANCY);
    }

}
