package ru.calculator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.calculator.dto.NewCalculationData;
import ru.calculator.service.CalculatorService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/calculate")
public class CalculatorController {
    private final CalculatorService calculatorService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String calculate(@RequestParam @Positive double salary,
                            @RequestParam @Positive @Max(value = 28) int number,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        log.info("Получен запрос GET /calculate c годовой з\\п {}, кол-вом дней отпуска {}, и датами отпуска с {} по {}",
                salary, number, start, end);
        NewCalculationData inputData = new NewCalculationData(salary, number, start, end);
        return String.format("%.4f", calculatorService.calculate(inputData));
    }
}
