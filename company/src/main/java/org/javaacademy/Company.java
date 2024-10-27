package org.javaacademy;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.javaacademy.employee.Employee;
import org.javaacademy.employee.Manager;
import org.javaacademy.employee.Programmer;
import org.javaacademy.task.Task;
import org.javaacademy.task.TaskStatus;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Company {
     @NonNull
     String name;
     @NonNull
     Manager manager;
     private static final BigDecimal MANAGER_RATIO = new BigDecimal("0.1");
     @NonNull
     List<Programmer> programmers;
     MultiValuedMap<Programmer, Task> completedTasks;
     Map<Employee, BigDecimal> timesheet;
     BigDecimal totalCosts;
     //private static final Logger logger = LoggerFactory.getLogger(Company.class);

     /**
      * Конструктор
      * @param name           имя компании
      * @param manager        Менеджер компании
      * @param programmers    Список программистов
      * @param hourlyRate     Часовая ставка для программистов
      */
     public Company(@NonNull String name, @NonNull Manager manager, @NonNull List<Programmer> programmers,
                    @NonNull BigDecimal hourlyRate) {
          this.name = name;
          this.manager = manager;
          for (Programmer programmer : programmers) {
               programmer.setHourlyRate(hourlyRate);
          }
          this.programmers = programmers;
          timesheet = new HashMap<>();
          completedTasks = new ArrayListValuedHashMap<>();
     }

     /**
      *  Недельная работа.
      * @param tasks     список заданий для программистов.
      *   Распределяет по очереди задачи и вызывает метод assignAndPrintTask
      */
     public void WeeklyWork(List<Task> tasks) {
          IntStream.range(0, tasks.size())
                  .forEach(i -> assignAndPrintTask(i, tasks.get(i)));
     }

     /**
      * Выплата ЗП на основе табеля учета рабочего времени
      */
     public void payEmployees() {
          timesheet.forEach((employee, hours) -> {
               BigDecimal earnings = employee.getHourlyRate().multiply(hours);
               employee.addEarnings(earnings);

               log.debug(employee.getFullName() + " ставка:" + employee.getHourlyRate().toString() + "\t" + hours.toString() + "\t"
                       + employee.getEarnedAmount());

               totalCosts = ((totalCosts ==null) ? BigDecimal.ZERO : totalCosts).add(earnings);
          });
          timesheet.clear();
     }

     public void printCompanyInfo() {
          System.out.printf("%s%nЗатраты: %.2f%n", name, totalCosts);
          System.out.println("Список выполненных задач у компании:");

          programmers.forEach(programmer -> {
               System.out.printf("%-40s  %-40s%n", programmer.getFullName(), getCompletedTasks(programmer));
          });
     }

     /**
      * Метод вывода списка выполненны задач для программиста
      * @param programmer          Программист
      * @return                     строка с выполненными заданиями через запятую
      */
     private String getCompletedTasks(Programmer programmer) {
          return completedTasks.get(programmer).stream()
                  .filter(task -> task.getStatus() == TaskStatus.COMPLETED)
                  .map(task -> task.getDescription())
                  .collect(Collectors.joining(", "));

     }

     /**
      * Назначает задание программистам и печатает сообщение о его выполнении
      * @param index     Порядковый номер задания для очередности назначения
      * @param task      Задание
      */
     private void assignAndPrintTask(int index, Task task) {
          Programmer programmer = programmers.get(index % programmers.size());
          programmer.acceptTask(task);
          System.out.println(task.getDescription() + " - сделана.");
          updateTimesheet(programmer, task);
          updateCompletedTasks(programmer, task);
          task.setStatus(TaskStatus.COMPLETED);
     }

     private void updateCompletedTasks(Programmer programmer, Task task) {
          completedTasks.put(programmer, task);
     }

     /**
      * Обновляет табель учета рабочего времени для программиста и менеджера.
      * Добавляет время выполненного задания.
      */
     private void updateTimesheet(Programmer programmer, Task task) {
          timesheet.put(programmer, timesheet.getOrDefault(programmer, BigDecimal.ZERO).add(task.getHoursSpent()));;

          log.debug(programmer.getName() + " добавил " + task.getHoursSpent() + " часов к табелю.");
          timesheet.put(manager,  timesheet.getOrDefault(programmer, BigDecimal.ZERO).add(task.getHoursSpent()));

          log.debug(manager.getName() + " добавил " + task.getHoursSpent().multiply(MANAGER_RATIO));
     }
}
