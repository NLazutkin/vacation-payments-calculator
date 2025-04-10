package ru.calculator.enums;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PublicHolidaysTest {
    @Test
    public void testValueToString() {
        PublicHolidays value = PublicHolidays.valueOf("JANUARY_1");

        assertEquals("JANUARY_1", value.toString(), "Имя запрашиваемого праздника не соответствует ожидаемому");
    }

    @Test
    public void testToListDatesMethod() {
        List<LocalDate> holidaysList = PublicHolidays.toListDates();

        assertEquals(14, holidaysList.size(), "Кол-во праздников не соответствует ожидаемому");
    }
}
