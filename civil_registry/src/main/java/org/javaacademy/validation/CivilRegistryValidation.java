package org.javaacademy.validation;

import static org.javaacademy.entity.MaritalStatus.MARRIED;

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
   * @param firstSpouse  Первый супруг, чье состояние проверяется.
   * @param secondSpouse Второй супруг, чье состояние проверяется.
   * @throws IllegalArgumentException Если оба супруга не разведены.
   */
  public void checkOnNotMarriedStatus(Citizen firstSpouse, Citizen secondSpouse) {
    if (firstSpouse.getMaritalStatus() == MARRIED
        && secondSpouse.getMaritalStatus() == MARRIED) {
      throw new IllegalArgumentException(
          "Ошибка: Для регистрации брака оба супруга должны не состоять в браке.");
    }
  }

  /**
   * Проверяет, могут ли двое граждан развестись, основываясь на их текущем статусе.
   *
   * @param firstSpouse  Первый супруг, чье состояние проверяется.
   * @param secondSpouse Второй супруг, чье состояние проверяется.
   * @throws IllegalArgumentException Если оба супруга не состоят в браке.
   */
  public void checkOnMarriedStatus(Citizen firstSpouse, Citizen secondSpouse) {
    if (firstSpouse.getMaritalStatus() != MARRIED
        && secondSpouse.getMaritalStatus() != MARRIED
        && !firstSpouse.getSpouse().equals(secondSpouse)) {
      throw new IllegalArgumentException(
          "Ошибка: Для регистрации развода оба супруга должны состоять в браке друг с другом.");
    }
  }

  /**
   * Проверяет корректность связи "родитель-ребенок" для указанного ребенка и родителей.
   *
   * @param child  Ребенок, связь которого проверяется.
   * @param father Предполагаемый отец ребенка.
   * @param mother Предполагаемая мать ребенка.
   * @throws IllegalArgumentException Если указанный отец или мать не совпадают с биологическими
   *                                  родителями ребенка.
   */
  public void validateParentChildConnection(Citizen child, Citizen father, Citizen mother) {
    if (!child.getFather().equals(father)) {
      throw new IllegalArgumentException(
          "Ошибка: Указанный отец не является родителем указанного ребенка.");
    }

    if (!child.getMother().equals(mother)) {
      throw new IllegalArgumentException(
          "Ошибка: Указанная мать не является родителем указанного ребенка.");
    }
  }
}
