package org.javaacademy;

import java.math.BigDecimal;
import java.util.List;
import org.javaacademy.employee.Manager;
import org.javaacademy.employee.Programmer;
import org.javaacademy.human.Gender;
import org.javaacademy.task.Task;

public class Runner {

  public static void main(String[] args) {
    Programmer programmer1 = new Programmer("Петр", "Петров", "Петрович", Gender.MALE);
    Programmer programmer2 = new Programmer("Иван", "Иванов", "Иванович", Gender.MALE);
    Manager manager = new Manager("Олег", "Куликов", "Васильевич", Gender.MALE);
    Company oracle = new Company("Oracle", manager, List.of(programmer1, programmer2),
        BigDecimal.valueOf(1_500));

    Task task1 = new Task("Задача №1", BigDecimal.valueOf(1));
    Task task2 = new Task("Задача №2", BigDecimal.valueOf(2));
    Task task3 = new Task("Задача №3", BigDecimal.valueOf(3));
    oracle.weeklyWork(List.of(task1, task2, task3));
    oracle.paysForWeekOfWork();
    oracle.infoAboutCompany();
  }

}
