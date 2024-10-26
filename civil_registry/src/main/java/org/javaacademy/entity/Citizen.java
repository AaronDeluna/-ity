package org.javaacademy.entity;

import static org.javaacademy.entity.MaritalStatus.SINGLE;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.javaacademy.human.Gender;
import org.javaacademy.human.Human;

/**
 * Класс Гражданин.
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Citizen extends Human {

  MaritalStatus maritalStatus = SINGLE;
  Citizen spouse;

  public Citizen(String name, String surname, String patronymic, Gender gender) {
    super(name, surname, patronymic, gender);
  }

  /**
   * Метод переопределенный от класса Human.
   *
   * @param name         имя ребенка
   * @param surname      фамилия ребенка
   * @param patronymic   отчество ребенка
   * @param gender       пол ребенка
   * @param secondParent второй родитель ребенка
   * @return объект гражданина
   */
  @Override
  public Citizen produceChild(@NonNull String name, @NonNull String surname,
      @NonNull String patronymic, @NonNull Gender gender, @NonNull Human secondParent) {
    Citizen child = new Citizen(name, surname, patronymic, gender);
    child.setParents(this, secondParent);
    return child;
  }
}
