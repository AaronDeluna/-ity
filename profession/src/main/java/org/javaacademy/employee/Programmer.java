package org.javaacademy.employee;

import lombok.NonNull;
import org.javaacademy.Gender;
import org.javaacademy.task.Task;
import org.javaacademy.task.TaskStatus;

import java.math.BigDecimal;

public class Programmer extends Employee {
    private static final BigDecimal MIN_HOURLY_RATE = BigDecimal.valueOf(1500.0);
    private static final BigDecimal MAX_HOURLY_RATE = BigDecimal.valueOf(2000.0);

    public Programmer(String name, String surname, String patronymic, Gender gender) {
        super(name, surname, patronymic, gender);
    }

    /**
     * Функция установления почасовой ставки.
     */

    @Override
    public void setHourlyRate(@NonNull BigDecimal hourlyRate) {
        if (hourlyRate.compareTo(MIN_HOURLY_RATE) < 0 || hourlyRate.compareTo(MAX_HOURLY_RATE) > 0) {
            throw new IllegalArgumentException
                    ("У программиста может быть ставка в диапазоне от 1500 до 2000 рублей!");
        }
        super.setHourlyRate(hourlyRate);
    }

    /**
     * Функция, которая позволяет программисту принимать задачу.
     */

    public void acceptTask(Task task) {
        task.setStatus(TaskStatus.COMPLETED);
    }
}
