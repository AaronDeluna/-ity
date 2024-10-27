package org.javaacademy.employee;

import lombok.NonNull;
import org.javaacademy.human.Gender;

import java.math.BigDecimal;

/**
 * Класс Менеджер
 * HOURLY_RATE постоянная ставка 10000
 */
public class Manager extends Employee {

    private static final BigDecimal HOURLY_RATE = new BigDecimal("10000.00");

    /**
     * Конструктор принимает параметры и инициализирует объект класса Человек.
     *
     * @param name       - имя человека.
     * @param surname    - фамилия человека.
     * @param patronymic - отчество человека.
     * @param gender     - пол человека.
     */
    public Manager(@NonNull String name, @NonNull String surname, @NonNull String patronymic,
                   @NonNull Gender gender) {
        super(name, surname, patronymic, gender);
        setHourlyRate(HOURLY_RATE);
    }
}
