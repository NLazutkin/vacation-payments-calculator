package ru.calculator.service;

import ru.calculator.dto.NewCalculationData;

public interface CalculatorService {
    double calculate(NewCalculationData calculationData);
}
