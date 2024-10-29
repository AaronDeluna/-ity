package org.javaacademy;

import org.javaacademy.employee.Manager;
import org.javaacademy.employee.Programmer;
import org.javaacademy.task.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Тестирование компании")
class CompanyTest {
  Company company;
  Manager manager;
  Programmer programmer1;
  Programmer programmer2;
  Task task1;
  Task task2;

  @BeforeEach
  void setUp() {
    manager = Mockito.mock(Manager.class);
    programmer1 = Mockito.mock(Programmer.class);
    programmer2 = Mockito.mock(Programmer.class);

    when(programmer1.getFullName()).thenReturn("Иванов Иван Иванович");
    when(programmer2.getFullName()).thenReturn("Росиец Тарас Дмитриевич");

    company = new Company("Тестовая компания", manager, BigDecimal.valueOf(1500), programmer1, programmer2);

    task1 = Mockito.mock(Task.class);
    task2 = Mockito.mock(Task.class);

    when(task1.getDescription()).thenReturn("Задача 1");
    when(task2.getDescription()).thenReturn("Задача 2");
    when(task1.getHoursOfLabor()).thenReturn(BigDecimal.valueOf(5));
    when(task2.getHoursOfLabor()).thenReturn(BigDecimal.valueOf(8));
  }

  @AfterEach
  void tearDown() {
    company = null;
    manager = null;
    programmer1 = null;
    programmer2 = null;
    task1 = null;
    task2 = null;
  }

  @DisplayName("Успеншное выполнение метода задачи работы на неделю")
  @Test
  void weeklyWork() {
    company.weeklyWork(task1, task2);

    verify(programmer1).acceptTask(task1);
    verify(programmer2).acceptTask(task2);

    assertEquals(BigDecimal.valueOf(5), company.getTimesheet().get(programmer1));
    assertEquals(BigDecimal.valueOf(8), company.getTimesheet().get(programmer2));
  }

  @DisplayName("Успешная выплата работникам за неделю")
  @Test
  void paysForWeekOfWork() {
    company.weeklyWork(task1, task2);

    when(programmer1.getHourlyRate()).thenReturn(BigDecimal.valueOf(1500));
    when(programmer2.getHourlyRate()).thenReturn(BigDecimal.valueOf(1500));
    when(manager.getHourlyRate()).thenReturn(BigDecimal.valueOf(10_000));

    company.paysForWeekOfWork();

    verify(programmer1).setAmountOfMoneyEarned(BigDecimal.valueOf(7_500));
    verify(programmer2).setAmountOfMoneyEarned(BigDecimal.valueOf(12_000));
    assertEquals(BigDecimal.valueOf(32_500.0), company.getTotalExpenses());
    assertTrue(company.getTimesheet().isEmpty());
  }

  @DisplayName("Успешный вывод информации о компании")
  @Test
  void infoAboutCompany() {
    company.weeklyWork(task1, task2);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    company.infoAboutCompany();

    String output = outputStream.toString();
    assertTrue(output.contains("Тестовая компания"));
    assertTrue(output.contains("Затраты: 0,00"));
    assertTrue(output.contains("Список выполненных задач у компании:"));
    assertTrue(output.contains("Иванов Иван Иванович - Задача 1"));
    assertTrue(output.contains("Росиец Тарас Дмитриевич - Задача 2"));

    System.setOut(System.out);
  }
}