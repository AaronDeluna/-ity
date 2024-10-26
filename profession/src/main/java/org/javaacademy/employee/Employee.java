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

    private BigDecimal hourlyRate;
    private BigDecimal earnedAmount;

  //public Employee(String name, String surname, String patronymic, Gender gender, BigDecimal earnedAmount) {
  //    super(name, surname, patronymic, gender);
     // this.earnedAmount = earnedAmount;
 // }

  //public void addEarnings(BigDecimal amount) {
       //his.earnedAmount = this.earnedAmount.add(amount);
 // }

  BigDecimal hourlyRate;

  public Employee(String name, String surname, String patronymic, Gender gender) {
    super(name, surname, patronymic, gender);
  }

}
