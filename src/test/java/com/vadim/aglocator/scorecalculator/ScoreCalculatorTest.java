package com.vadim.aglocator.scorecalculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ScoreCalculatorTest {

    private static ScoreCalculator scoreCalculator;

    @BeforeEach
    public void init() {
        scoreCalculator = new ScoreCalculatorImpl();
    }

    @Test
    void calculateCityNameScore() {
    }

    @Test
    void calculateLocationScore() {
    }

    @Test
    void calculateSearchScore() {
    }
}
