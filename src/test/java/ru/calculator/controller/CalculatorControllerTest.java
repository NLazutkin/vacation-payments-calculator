package ru.calculator.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.calculator.service.CalculatorService;

import javax.validation.ConstraintViolationException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CalculatorController.class)
class CalculatorControllerTest {

    @MockBean
    CalculatorService calculatorService;

    @Autowired
    private MockMvc mvc;

    private final String urlTemplate = "/calculate";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    void getTestWith4Params() throws Exception {

        when(calculatorService.calculate(any())).thenReturn(61433.4470);

        mvc.perform(get(urlTemplate)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("salary", "1800000.00")
                        .param("number", "14")
                        .param("start", LocalDate.of(2025, 5, 1).format(FORMATTER))
                        .param("end", LocalDate.of(2025, 5, 14).format(FORMATTER))
                        .contentType(MediaType.TEXT_PLAIN)
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("61433,4470"));
    }

    @Test
    void getTestWith2Params() throws Exception {

        when(calculatorService.calculate(any())).thenReturn(71672.3549);

        mvc.perform(get(urlTemplate)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("salary", "1800000.00")
                        .param("number", "14")
                        .contentType(MediaType.TEXT_PLAIN)
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("71672,3549"));
    }

    @Test
    void getTestWithOutSalary() throws Exception {

        when(calculatorService.calculate(any())).thenReturn(0.00);

        mvc.perform(get(urlTemplate)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("number", "14")
                        .contentType(MediaType.TEXT_PLAIN)
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertInstanceOf(MissingServletRequestParameterException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("Required request parameter 'salary' for method " +
                                "parameter type double is not present",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    void getTestWithOutNumber() throws Exception {

        when(calculatorService.calculate(any())).thenReturn(0.00);

        mvc.perform(get(urlTemplate)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("salary", "1800000.00")
                        .contentType(MediaType.TEXT_PLAIN)
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertInstanceOf(MissingServletRequestParameterException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("Required request parameter 'number' for method " +
                                "parameter type int is not present",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    void getTestWithNegativeSalary() throws Exception {

        when(calculatorService.calculate(any())).thenReturn(0.00);

        mvc.perform(get(urlTemplate)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("salary", "-1800000.00")
                        .param("number", "14")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertInstanceOf(ConstraintViolationException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("calculate.salary: must be greater than 0",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));

    }

    @Test
    void getTestWithNegativeNumber() throws Exception {

        when(calculatorService.calculate(any())).thenReturn(0.00);

        mvc.perform(get(urlTemplate)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("salary", "1800000.00")
                        .param("number", "-14")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertInstanceOf(ConstraintViolationException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("calculate.number: must be greater than 0",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    void getTestWithNotCorrectNumber() throws Exception {

        when(calculatorService.calculate(any())).thenReturn(0.00);

        mvc.perform(get(urlTemplate)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("salary", "1800000.00")
                        .param("number", "50")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertInstanceOf(ConstraintViolationException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("calculate.number: must be less than or equal to 28",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    void getTestWithNotCorrectDates() throws Exception {
        DateTimeFormatter wrongFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        when(calculatorService.calculate(any())).thenReturn(0.00);

        mvc.perform(get(urlTemplate)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("salary", "1800000.00")
                        .param("number", "14")
                        .param("start", LocalDate.of(2025, 5, 1).format(wrongFormatter))
                        .param("end", LocalDate.of(2025, 5, 14).format(wrongFormatter))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertInstanceOf(MethodArgumentTypeMismatchException.class, result.getResolvedException()))
                .andExpect(result -> assertTrue(Objects.requireNonNull(result.getResponse())
                        .getContentAsString(StandardCharsets.UTF_8)
                        .contains("Поле: start. Некорректное значения поля")));
    }

    @Test
    void getTestWithServerError() throws Exception {

        when(calculatorService.calculate(any())).thenThrow(new RuntimeException("Error"));

        mvc.perform(get(urlTemplate)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("salary", "0.01")
                        .param("number", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertInstanceOf(Throwable.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("Error",
                        Objects.requireNonNull(result.getResolvedException()).getLocalizedMessage()));
    }
}
