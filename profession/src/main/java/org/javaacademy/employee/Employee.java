package org.javaacademy.employee;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.javaacademy.human.Gender;
import org.javaacademy.human.Human;

/**
 * Класс Сотрудник.
 *
 */

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee extends Human {

    private BigDecimal hourlyRate;
    private BigDecimal earnedAmount;

    /**
     * Конструктор принимает параметры и инициализирует объект класса Человек.
     *
     * @param name       - имя человека.
     * @param surname    - фамилия человека.
     * @param patronymic - отчество человека.
     * @param gender     - пол человека.
     */
    public Employee(@NonNull String name, @NonNull String surname, @NonNull String patronymic, @NonNull Gender gender) {
        super(name, surname, patronymic, gender);
    }


    //public Employee(String name, String surname, String patronymic, Gender gender, BigDecimal earnedAmount) {
  //    super(name, surname, patronymic, gender);
     // this.earnedAmount = earnedAmount;
 // }

  public void addEarnings(BigDecimal amount) {
       this.earnedAmount =((this.earnedAmount == null) ? BigDecimal.ZERO : this.earnedAmount).add(amount);

  }

}
