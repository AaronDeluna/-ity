package org.javaacademy;

import java.math.BigDecimal;
import org.javaacademy.employee.Manager;
import org.javaacademy.employee.Programmer;
import org.javaacademy.human.Gender;
import org.javaacademy.task.Task;

/**
 * Класс Runner.
 */
public class Runner {

  private static final Long HOURS_OF_LABOR_TASK_1 = 1L;
  private static final Long HOURS_OF_LABOR_TASK_2 = 2L;
  private static final Long HOURS_OF_LABOR_TASK_3 = 3L;

  /**
   * Метод входа в программу.
   *
   * @param args аргументы метода
   */
  public static void main(String[] args) {
    Programmer programmer1 = new Programmer("Петр", "Петров", "Петрович", Gender.MALE);
    Programmer programmer2 = new Programmer("Иван", "Иванов", "Иванович", Gender.MALE);
    Manager manager = new Manager("Олег", "Куликов", "Васильевич", Gender.MALE);
    Company oracle = new Company(args[0], manager, BigDecimal.valueOf(Long.parseLong(args[1])),
        programmer1,
        programmer2);

    Task task1 = new Task("Задача №1", BigDecimal.valueOf(HOURS_OF_LABOR_TASK_1));
    Task task2 = new Task("Задача №2", BigDecimal.valueOf(HOURS_OF_LABOR_TASK_2));
    Task task3 = new Task("Задача №3", BigDecimal.valueOf(HOURS_OF_LABOR_TASK_3));
    oracle.weeklyWork(task1, task2, task3);
    oracle.paysForWeekOfWork();
    oracle.infoAboutCompany();
  }
}
