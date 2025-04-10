package ru.calculator.enums;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PublicHolidays {
    JANUARY_1(LocalDate.of(LocalDate.now().getYear(), 1, 1), "Новогодние каникулы"),
    JANUARY_2(LocalDate.of(LocalDate.now().getYear(), 1, 2), "Новогодние каникулы"),
    JANUARY_3(LocalDate.of(LocalDate.now().getYear(), 1, 3), "Новогодние каникулы"),
    JANUARY_4(LocalDate.of(LocalDate.now().getYear(), 1, 4), "Новогодние каникулы"),
    JANUARY_5(LocalDate.of(LocalDate.now().getYear(), 1, 5), "Новогодние каникулы"),
    JANUARY_6(LocalDate.of(LocalDate.now().getYear(), 1, 6), "Новогодние каникулы"),
    JANUARY_7(LocalDate.of(LocalDate.now().getYear(), 1, 7), "Рождество Христово"),
    JANUARY_8(LocalDate.of(LocalDate.now().getYear(), 1, 8), "Новогодние каникулы"),
    FEBRUARY_23(LocalDate.of(LocalDate.now().getYear(), 2, 23), "День защитника Отечества"),
    MARCH_8(LocalDate.of(LocalDate.now().getYear(), 3, 8), "Международный женский день"),
    MAY_1(LocalDate.of(LocalDate.now().getYear(), 5, 1), "Праздник Весны и Труда"),
    MAY_9(LocalDate.of(LocalDate.now().getYear(), 5, 9), "День Победы"),
    JUNE_12(LocalDate.of(LocalDate.now().getYear(), 6, 12), "День России"),
    NOVEMBER_4(LocalDate.of(LocalDate.now().getYear(), 11, 4), "День народного единства");

    private final LocalDate date;
    private final String description;

    PublicHolidays(LocalDate date, String description) {
        this.date = date;
        this.description = description;
    }

    public static List<LocalDate> toListDates() {
        return Arrays.stream(values())
                .map(holiday -> holiday.date)
                .collect(Collectors.toList());
    }
}
