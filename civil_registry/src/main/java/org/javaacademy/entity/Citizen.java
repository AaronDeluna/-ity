package org.javaacademy.entity;

import static org.javaacademy.entity.MaritalStatus.DIVORCED;
import static org.javaacademy.entity.MaritalStatus.MARRIED;
import static org.javaacademy.human.Gender.FEMALE;
import static org.javaacademy.human.Gender.MALE;

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

  MaritalStatus maritalStatus = DIVORCED;
  Citizen spouse;

  public Citizen(String name, String surname, String patronymic, Gender gender) {
    super(name, surname, patronymic, gender);
  }

  /**
   * Осуществляет развод для гражданина, устанавливая его статус на 'разведенный' и очищая
   * информацию о супруге.
   */
  public void divorce() {
    maritalStatus = DIVORCED;
    spouse = null;
  }

  /**
   * Устанавливает статус гражданина как 'женатый' и связывает его с указанным супругом.
   *
   * @param spouse Супруг(а), с которым заключается брак.
   */
  public void marriage(Citizen spouse) {
    maritalStatus = MARRIED;
    this.spouse = spouse;
  }

  @Override
  public Citizen produceChild(@NonNull String name, @NonNull String surname,
      @NonNull String patronymic, @NonNull Gender gender,
      @NonNull Human secondParent) {
//    Human child = super.produceChild(name, surname, patronymic, gender, secondParent);
    Citizen citizen = new Citizen(name, surname, patronymic, gender);
    if (secondParent.getGender() == MALE) {
      citizen.setParents(this, secondParent);
    } else if (secondParent.getGender() == FEMALE) {
      citizen.setParents(secondParent, this);
    }

    return citizen;
  }
}
