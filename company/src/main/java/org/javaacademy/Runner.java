package org.javaacademy;

import org.javaacademy.employee.Manager;
import org.javaacademy.employee.Programmer;
import org.javaacademy.human.Gender;
import org.javaacademy.task.Task;

import java.math.BigDecimal;
import java.util.List;

public class Runner {
    public static void main(String[] args) {

        Manager manager = new Manager("Юрий", "Молодыко", "Иванович", Gender.MALE);
        Programmer programmer1 = new Programmer("Карина", "Петрова"
                , "Сергеевна", Gender.FEMALE);
        Programmer programmer2 = new Programmer("Андрей", "Чернов"
                , "Александрович", Gender.MALE);
        Programmer programmer3 = new Programmer("Геннадий", "Сидоров"
                , "Сергеевич", Gender.MALE);
        Programmer programmer4 = new Programmer("Иван", "Иванов"
                , "Альбертович", Gender.MALE);
        Company company = new Company("Oracle", manager, List.of(programmer1, programmer2,
                programmer3, programmer4), BigDecimal.valueOf(1800));


        company.WeeklyWork(List.of(new Task("задача 1", BigDecimal.valueOf(2))
                , new Task("задача 2", BigDecimal.valueOf(5))
                , new Task("задача 3", BigDecimal.valueOf(1))
                , new Task("задача 4", BigDecimal.valueOf(1))
                , new Task("задача 5", BigDecimal.valueOf(3))
                , new Task("задача 6", BigDecimal.valueOf(4))
                , new Task("задача 7", BigDecimal.valueOf(5))
                , new Task("задача 8", BigDecimal.valueOf(10))));

        company.payEmployees();
        company.printCompanyInfo();

    }
}
