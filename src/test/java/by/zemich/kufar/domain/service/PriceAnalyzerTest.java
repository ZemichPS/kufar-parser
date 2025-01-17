package by.zemich.kufar.domain.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PriceAnalyzerTest {

    private final PriceAnalyzer priceAnalyzer = new PriceAnalyzer();

    @Test
    void calculatePercentageDifference_ifFirstArgumentGraterThenSecond_shouldReturnNegativeResult() {
        BigDecimal val1 = new BigDecimal(100);
        BigDecimal val2 = new BigDecimal(70);
        BigDecimal result = priceAnalyzer.calculatePercentageDifference(val1, val2);
        assertEquals(-1, result.compareTo(new BigDecimal(0)));
    }

    @Test
    void calculatePercentageDifference_ifFirstArgumentLessThenSecond_shouldReturnPositiveResult() {
        BigDecimal val1 = new BigDecimal(70);
        BigDecimal val2 = new BigDecimal(100);
        BigDecimal result = priceAnalyzer.calculatePercentageDifference(val1, val2);
        assertEquals(1, result.compareTo(new BigDecimal(0)));
    }

    @Test
    void calculatePercentageDifference_ifFirstArgumentGraterThenSecond_shouldReturnTrue() {
        BigDecimal val1 = new BigDecimal(100);
        BigDecimal val2 = new BigDecimal(70);
        BigDecimal result = priceAnalyzer.calculatePercentageDifference(val1, val2);
        assertEquals(0, result.compareTo(new BigDecimal(-30)));
    }


}