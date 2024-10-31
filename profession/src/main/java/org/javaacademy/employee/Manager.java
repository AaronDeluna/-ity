package org.javaacademy.employee;

import java.math.BigDecimal;
import org.javaacademy.human.Gender;

/**
 * Класс Менеджер.
 */
public class Manager extends Employee {

  private static final BigDecimal HOURLY_RATE = BigDecimal.valueOf(10_000);

  public Manager(String name, String surname, String patronymic, Gender gender) {
    super(name, surname, patronymic, gender);
    setHourlyRate(HOURLY_RATE);
  }
}
