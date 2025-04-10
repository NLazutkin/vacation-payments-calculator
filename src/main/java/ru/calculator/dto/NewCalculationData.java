package ru.calculator.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCalculationData {
    double avgYearSalary;
    int numberDays;
    LocalDate startDate;
    LocalDate endDate;

    public boolean isDatesExist() {
        return startDate != null && endDate != null;
    }
}
