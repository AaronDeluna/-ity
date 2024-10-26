package org.javaacademy.employee;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.javaacademy.human.Gender;
import org.javaacademy.human.Human;

/**
 * Класс Сотрудник.
 */

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee extends Human {

  BigDecimal hourlyRate;

  public Employee(String name, String surname, String patronymic, Gender gender) {
    super(name, surname, patronymic, gender);
  }
}
