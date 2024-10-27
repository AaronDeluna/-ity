package org.javaacademy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.NonNull;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.javaacademy.employee.Employee;
import org.javaacademy.employee.Manager;
import org.javaacademy.employee.Programmer;
import org.javaacademy.task.Task;
import org.javaacademy.task.TaskStatus;

/**
 * Класс Компания
 */
public class Company {

  private static final BigDecimal RATIO_LABOR_COST_MANAGER = BigDecimal.valueOf(0.1);

  private final String name;
  private final Manager manager;
  private final List<Programmer> programmers;
  MultiValuedMap<Programmer, Task> listCompletedTasksByProgrammers = new ArrayListValuedHashMap<>();
  Map<Employee, BigDecimal> timesheet = new HashMap<>();
  BigDecimal totalExpenses;

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
      System.out.printf("%s - сделана.", task.getDescription());

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
        (key, value) -> {
          BigDecimal payment = value.multiply(key.getHourlyRate());
          key.setAmountOfMoneyEarned(payment);
          totalExpenses = totalExpenses.add(payment);
        });
    timesheet.clear();
  }

  public void informationAboutCompany() {
    System.out.printf("""
        %s
        Затраты: %.2f
        Список выполненных задач у компании:
        """, name, totalExpenses);
    listCompletedTasksByProgrammers.entries()
        .forEach((programmerTaskEntry) -> {
          System.out.printf("""
              %s - %s
              """, programmerTaskEntry.getKey(), programmerTaskEntry.getValue());
        });
    listCompletedTasksByProgrammers.



  }

  /**
   * Назначает задачи программистам по очереди
   */

  public void assignTasks(List<Task> tasks) {
    if (isProgrammersListEmpty()) {
      return;
    }

    IntStream.range(0, tasks.size())
        .forEach(i -> assignAndLogTask(i, tasks.get(i)));
  }

  /**
   * Выплата ЗП на основе табеля учета рабочего времени
   */

  public void payEmployees() {
    if (timesheet.isEmpty()) {
      System.out.println("Табель учета времени пуст.");
      return;
    }

    timesheet.forEach((employee, hours) -> {
      BigDecimal earnings = employee.getHourlyRate().multiply(BigDecimal.valueOf(hours));
      employee.addEarnings(earnings);
      totalExpenses = totalExpenses.add(earnings);
      System.out.println(employee.getName() + " получил " + earnings + " рублей.");
    });

    timesheet.clear();
    System.out.println("Табель учета времени обнулен.");
  }
  //5.5. Создать функцию "инфо о компании". Печатает информацию:
  //"
  //[имя компании]
  //Затраты: [сумма затрат до 2х знаков после запятой]
  //Список выполненных задач у компании:
  //[ФИО программиста] - [список задач]
  //[ФИО программиста] - [список задач]
  //"

  public void info() {
    System.out.printf("%s%nЗатраты: %.2f%n", name, totalExpenses);
    System.out.println("Список выполненных задач у компании:");

    programmers.forEach(programmer -> {
      String completedTasks = getCompletedTasks(programmer);
      System.out.printf("%s %s - %s%n", programmer.getName(), programmer.getSurname(),
          completedTasks);
    });
  }

  private String getCompletedTasks(Programmer programmer) {
    return listCompletedTasksByProgrammers.get(programmer).stream()
        .filter(task -> task.getStatus() == TaskStatus.COMPLETED)
        .map(Task::getDescription)
        .collect(Collectors.joining(", "));
  }


  private boolean isProgrammersListEmpty() {
    if (programmers == null || programmers.isEmpty()) {
      System.out.println("Нет программистов для выполнения задач.");
      return true;
    }
    return false;
  }

  /**
   * Назначает задачу программисту и логирует выполнение
   */

  private void assignAndLogTask(int index, Task task) {
    Programmer programmer = programmers.get(index % programmers.size());
    programmer.acceptTask(task);
    System.out.println(task.getDescription() + " - сделана.");

    updateTimesheet(programmer, task);

    task.setStatus(TaskStatus.COMPLETED);
  }

  /**
   * Обновляет табель учета рабочего времени для программиста и менеджера
   */

  private void updateTimesheet(Programmer programmer, Task task) {
    int hoursSpent = task.getHoursOfLabor();
    timesheet.put(programmer, hoursSpent);
    System.out.println(programmer.getName() + " добавил " + hoursSpent + " часов к табелю.");

    int managerHours = (int) (hoursSpent * 10E-1);
    timesheet.put(manager, managerHours);
    System.out.println(manager.getName() + " добавил " + managerHours + " часов к табелю.");
  }
}
