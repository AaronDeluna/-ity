package org.javaacademy;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.MultiValuedMap;
import org.javaacademy.employee.Employee;
import org.javaacademy.employee.Manager;
import org.javaacademy.employee.Programmer;
import org.javaacademy.task.Task;
import org.javaacademy.task.TaskStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Company {
     String name;
     Manager manager;
     List<Programmer> programmers;
     MultiValuedMap<Programmer, Task> tasks;
     Map<Employee, Integer> timesheet;
     BigDecimal totalExpenses;

     public Company(String name, Manager manager, List<Programmer> programmers, MultiValuedMap<Programmer, Task> tasks,
                    Map<Employee, Integer> timesheet, BigDecimal totalExpenses, BigDecimal hourlyRate) {
          this.name = name;
          this.manager = manager;
          this.programmers = programmers;
          this.tasks = tasks;
          this.timesheet = timesheet;
          this.totalExpenses = totalExpenses;

          if (programmers != null && hourlyRate != null) {
               programmers.forEach(programmer -> programmer.setHourlyRate(hourlyRate));
          }
     }

     /**
      *  Назначает задачи программистам по очереди
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
               System.out.printf("%s %s - %s%n", programmer.getName(), programmer.getSurname(), completedTasks);
          });
     }

     private String getCompletedTasks(Programmer programmer) {
          return tasks.get(programmer).stream()
                  .filter(task -> task.getStatus() == TaskStatus.COMPLETED)
                  .map(task -> task.getDescription())
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
          int hoursSpent = task.getHoursSpent();
          timesheet.put(programmer, hoursSpent);
          System.out.println(programmer.getName() + " добавил " + hoursSpent + " часов к табелю.");

          int managerHours = (int) (hoursSpent * 0.1);
          timesheet.put(manager, managerHours);
          System.out.println(manager.getName() + " добавил " + managerHours + " часов к табелю.");
     }
}
