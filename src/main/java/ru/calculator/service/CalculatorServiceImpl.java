package ru.calculator.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.calculator.dto.NewCalculationData;
import ru.calculator.enums.PublicHolidays;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@NoArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CalculatorServiceImpl implements CalculatorService {

    private double getDailyAvgSalary(double salary) {
        double monthInYear = 12.00;
        double avgDaysInMonths = 29.30;
        double dailyAvgSalary = salary / monthInYear / avgDaysInMonths;
        log.debug("Среднедневной заработок {}", dailyAvgSalary);
        return dailyAvgSalary;
    }

    private double calculateByNumberDays(double salary, int number) {
        double payments = getDailyAvgSalary(salary) * number;
        log.info("Отпускные за {} дней = {}", number, payments);
        return payments;
    }

    private int getAdjustedNumber(LocalDate start, LocalDate end) {
        List<LocalDate> publicHolidays = PublicHolidays.toListDates();

        return (int) Stream.iterate(start, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(start, end) + 1)
                .filter(date -> !publicHolidays.contains(date))
                .count();
    }

    private double calculateByDates(double salary, LocalDate start, LocalDate end) {
        int adjustedNumber = getAdjustedNumber(start, end);

        double payments = calculateByNumberDays(salary, adjustedNumber);
        log.info("c {} по {}", start, end);
        return payments;
    }

    @Override
    public double calculate(NewCalculationData calcData) {
        if (calcData.isDatesExist()) {
            log.info("Рассчитываем отпускные с датами отпуска с {} по {}", calcData.getStartDate(), calcData.getEndDate());
            return calculateByDates(calcData.getAvgYearSalary(),
                    calcData.getStartDate(),
                    calcData.getEndDate());
        }

        log.info("Рассчитываем отпускные по количеству дней отпуска {}", calcData.getNumberDays());
        return calculateByNumberDays(calcData.getAvgYearSalary(), calcData.getNumberDays());
    }
}
