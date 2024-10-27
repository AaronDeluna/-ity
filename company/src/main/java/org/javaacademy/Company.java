package org.javaacademy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.javaacademy.employee.Employee;
import org.javaacademy.employee.Manager;
import org.javaacademy.employee.Programmer;
import org.javaacademy.task.Task;

/**
 * Класс Компания.
 */
public class Company {

  private static final BigDecimal RATIO_LABOR_COST_MANAGER = BigDecimal.valueOf(0.1);

  private final String name;
  private final Manager manager;
  private final List<Programmer> programmers;
  MultiValuedMap<Programmer, Task> listCompletedTasksByProgrammers = new ArrayListValuedHashMap<>();
  Map<Employee, BigDecimal> timesheet = new HashMap<>();
  BigDecimal totalExpenses = BigDecimal.ZERO;

  /**
   * Конструктор класса Company.
   *
   * @param name                     имя компании
   * @param manager                  манеджер
   * @param programmers              список программистов
   * @param hourlyRateForProgrammers единая часовая ставка для программистов
   */
  public Company(@NonNull String name, @NonNull Manager manager,
      @NonNull List<Programmer> programmers, @NonNull BigDecimal hourlyRateForProgrammers) {
    this.name = name;
    this.manager = manager;
    this.programmers = programmers;

    programmers.forEach(programmer -> programmer.setHourlyRate(hourlyRateForProgrammers));
  }

  /**
   * Метод - Работа на неделю.
   *
   * @param tasks список задач
   */
  public void weeklyWork(@NonNull List<Task> tasks) {
    int i = 0;
    for (Task task : tasks) {
      if (i == programmers.size()) {
        i = 0;
      }
      Programmer programmer = programmers.get(i);
      programmer.acceptTask(task);
      System.out.printf("%s - сделана.\n", task.getDescription());

      addRecordToTimesheet(programmer, task.getHoursOfLabor());
      addRecordToTimesheet(manager, task.getHoursOfLabor().multiply(RATIO_LABOR_COST_MANAGER));

      listCompletedTasksByProgrammers.put(programmer, task);
      i++;
    }
  }

  /**
   * Метод добавления записи в табель учета времени.
   *
   * @param employee     работник
   * @param hoursOfLabor часы трудозатрат
   */
  private void addRecordToTimesheet(Employee employee, BigDecimal hoursOfLabor) {
    timesheet.merge(employee, hoursOfLabor, BigDecimal::add);
  }

  /**
   * Метод выплаты работникам за неделю.
   */
  public void paysForWeekOfWork() {
    timesheet.forEach(
        (employee, amountOfHours) -> {
          BigDecimal payment = amountOfHours.multiply(employee.getHourlyRate());
          employee.setAmountOfMoneyEarned(payment);
          totalExpenses = totalExpenses.add(payment);
        });
    timesheet.clear();
  }

  /**
   * Метод - инфо о компании.
   */
  public void infoAboutCompany() {
    System.out.printf("%s\nЗатраты: %.2f\nСписок выполненных задач у компании:\n",
        name, totalExpenses);
    listCompletedTasksByProgrammers.keySet()
        .forEach((programmer) -> {
          System.out.printf("%s - %s\n",
              programmer.getFullName(),
              joinListOfTasksToString(listCompletedTasksByProgrammers, programmer));
        });
  }

  private String joinListOfTasksToString(MultiValuedMap<Programmer, Task> lisTasksByProgrammers,
      Programmer programmer) {
    return String.join(", ",
        listCompletedTasksByProgrammers.get(programmer).stream()
            .map(Task::getDescription)
            .toList());
  }
}
