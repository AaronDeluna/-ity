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
        Programmer programmer1 = new Programmer("Карина", "Петрова",
                "Сергеевна", Gender.FEMALE);
        Programmer programmer2 = new Programmer("Андрей", "Чернов",
                "Александрович", Gender.MALE);
        Company company = new Company(args[0], manager, List.of(programmer1, programmer2),
                new BigDecimal(args[1]));


        company.weeklyWork(List.of(new Task("задача 1", BigDecimal.valueOf(2)),
                new Task("задача 2", BigDecimal.valueOf(5)),
                new Task("задача 3", BigDecimal.valueOf(1))));


        company.payEmployees();
        company.printCompanyInfo();

    }
}
