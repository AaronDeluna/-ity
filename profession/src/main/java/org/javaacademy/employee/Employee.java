package org.javaacademy.employee;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.javaacademy.human.Gender;
import org.javaacademy.human.Human;

/**
 * Класс Сотрудник.
 */

@Getter
@Setter
public class Employee extends Human {

  private BigDecimal hourlyRate;

  public Employee(String name, String surname, String patronymic, Gender gender) {
    super(name, surname, patronymic, gender);
  }
}
