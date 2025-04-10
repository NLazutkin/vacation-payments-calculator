package ru.calculator.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.calculator.dto.NewCalculationData;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CalculatorServiceTest {
    @InjectMocks
    private CalculatorServiceImpl calculatorService;

    private NewCalculationData makeCalculationData(double salary, int number, LocalDate start,  LocalDate end) {
        NewCalculationData calculationData =  new NewCalculationData();
        calculationData.setAvgYearSalary(salary);
        calculationData.setNumberDays(number);
        calculationData.setStartDate(start);
        calculationData.setEndDate(end);

        return calculationData;
    }

    private NewCalculationData makeCalculationData(double salary, int number) {
        NewCalculationData calculationData =  new NewCalculationData();
        calculationData.setAvgYearSalary(salary);
        calculationData.setNumberDays(number);

        return calculationData;
    }

    @Test
    void testCalculatePaymentsWithOutDates() {
        NewCalculationData inputData = makeCalculationData(1800000.00, 14);

        assertEquals(71672.35494880546, calculatorService.calculate(inputData), "Отпускные за "
                + inputData.getNumberDays() + " дней не соответствуют ожидаемой сумме");
    }

    @Test
    void testCalculatePaymentsWithDatesAndWithHolidays() {
        NewCalculationData inputData = makeCalculationData(1800000.00, 14, LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5, 14));

        assertEquals(61433.4470989761, calculatorService.calculate(inputData), "Отпускные с " + inputData.getStartDate()
                + " по " + inputData.getEndDate() + " с учетом праздников не соответствуют ожидаемой сумме");
    }

    @Test
    void testCalculatePaymentsWithDatesAndWithOutHolidays() {
        NewCalculationData inputData = makeCalculationData(1800000.00, 14, LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 14));

        assertEquals(66552.90102389078, calculatorService.calculate(inputData), "Отпускные с " + inputData.getStartDate()
                + " по " + inputData.getEndDate() + " без праздников не соответствуют ожидаемой сумме");
    }
}
