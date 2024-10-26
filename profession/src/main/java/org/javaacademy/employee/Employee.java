package org.javaacademy.employee;

import lombok.Getter;
import lombok.Setter;
import org.javaacademy.Gender;
import org.javaacademy.Human;

import java.math.BigDecimal;

/**
 * Сотрудник
 */

@Getter
@Setter
public class Employee extends Human {
    private BigDecimal hourlyRate;
    private BigDecimal earnedAmount;

    public Employee(String name, String surname, String patronymic, Gender gender, BigDecimal earnedAmount) {
        super(name, surname, patronymic, gender);
        this.earnedAmount = earnedAmount;
    }

    public void addEarnings(BigDecimal amount) {
        this.earnedAmount = this.earnedAmount.add(amount);
    }
}
