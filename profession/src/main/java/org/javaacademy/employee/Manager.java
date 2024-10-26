package org.javaacademy.employee;

import org.javaacademy.Gender;

import java.math.BigDecimal;

public class Manager extends Employee {

    private static final BigDecimal HOURLY_RATE = BigDecimal.valueOf(10000.0);

    public Manager(String name, String surname, String patronymic, Gender gender, BigDecimal earnedAmount) {
        super(name, surname, patronymic, gender, earnedAmount);
        setHourlyRate(HOURLY_RATE);
    }

}
