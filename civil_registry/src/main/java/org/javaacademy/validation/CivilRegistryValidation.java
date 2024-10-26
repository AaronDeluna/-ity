package org.javaacademy.validation;

import static org.javaacademy.entity.MaritalStatus.MARRIED;

import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.javaacademy.entity.Citizen;

/**
 * Утилитный класс реализующий валидацию гражданских действий ЗАГСа.
 */
@UtilityClass
public class CivilRegistryValidation {

  /**
   * Проверяет, могут ли двое граждан вступить в брак, основываясь на их текущем статусе.
   *
   * @param first  Первый супруг, чье состояние проверяется.
   * @param second Второй супруг, чье состояние проверяется.
   * @throws IllegalArgumentException Если оба супруга не разведены.
   */
  public void checkOnNotMarriedStatus(Citizen first, Citizen second) {
    if (first.getMaritalStatus() == MARRIED
        && second.getMaritalStatus() == MARRIED) {
      throw new IllegalArgumentException(
          "Ошибка: Для регистрации брака оба супруга должны не состоять в браке.");
    }
  }

  /**
   * Проверяет, могут ли двое граждан развестись, основываясь на их текущем статусе.
   *
   * @param first  Первый супруг, чье состояние проверяется.
   * @param second Второй супруг, чье состояние проверяется.
   * @throws IllegalArgumentException Если оба супруга не состоят в браке.
   */
  public void checkOnMarriedStatus(Citizen first, Citizen second) {
    if (first.getMaritalStatus() != MARRIED || second.getMaritalStatus() != MARRIED
        || Objects.nonNull(first.getSpouse()) && !first.getSpouse().equals(second)) {
      throw new IllegalArgumentException(
          "Ошибка: Для регистрации развода оба супруга должны состоять в браке друг с другом.");
    }
  }

  /**
   * Проверяет корректность связи "родитель-ребенок" для указанного ребенка и родителей.
   *
   * @param child        Ребенок, связь которого проверяется.
   * @param firstParent  родитель ребенка.
   * @param secondParent родитель ребенка.
   * @throws IllegalArgumentException Если указанный отец или мать не совпадают с биологическими
   *                                  родителями ребенка.
   */
  public void validateParentChildConnection(Citizen child, Citizen firstParent,
      Citizen secondParent) {
    if (!(child.getFather().equals(firstParent) || child.getFather().equals(secondParent))
        || !(child.getMother().equals(firstParent) || child.getMother().equals(secondParent))) {
      throw new IllegalArgumentException(
          "Ошибка: Какой либо из указанных граждан или оба не являются родителями ребёнка.");
    }
  }

  /**
   * Проверка на противоположность полов.
   *
   * @param first  первый гражданин
   * @param second второй гражданин
   */
  public static void checkOnOppositeGender(Citizen first, Citizen second) {
    if (first.getGender() == second.getGender()) {
      throw new IllegalArgumentException(
          "Ошибка: При регистрации (брака/развода/рождения ребенка) "
              + "супруги не могут быть одного пола.");
    }
  }
}
