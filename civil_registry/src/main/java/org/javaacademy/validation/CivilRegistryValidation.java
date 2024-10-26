package org.javaacademy.validation;

import lombok.experimental.UtilityClass;
import org.javaacademy.Gender;
import org.javaacademy.entity.Citizen;

import static org.javaacademy.entity.MaritalStatus.DIVORCED;
import static org.javaacademy.entity.MaritalStatus.MARRIED;

@UtilityClass
public class CivilRegistryValidation {

    /**
     * Проверяет, могут ли двое граждан вступить в брак,
     * основываясь на их текущем статусе.
     *
     * @param firstSpouse Первый супруг, чье состояние проверяется.
     * @param secondSpouse Второй супруг, чье состояние проверяется.
     * @throws IllegalArgumentException Если оба супруга не разведены.
     */
    public void validateMarriageStatus(Citizen firstSpouse, Citizen secondSpouse) {
        if (firstSpouse.getMaritalStatus() == MARRIED || secondSpouse.getMaritalStatus() == MARRIED) {
            throw new IllegalArgumentException("Ошибка: Для проведения операции оба супруга должны быть не в браке");
        }
    }

    /**
     * Проверяет, могут ли двое граждан развестись,
     * основываясь на их текущем статусе.
     *
     * @param firstSpouse Первый супруг, чье состояние проверяется.
     * @param secondSpouse Второй супруг, чье состояние проверяется.
     * @throws IllegalArgumentException Если оба супруга не состоят в браке.
     */
    public void validateDivorceStatus(Citizen firstSpouse, Citizen secondSpouse) {
        if (!firstSpouse.getSpouse().equals(secondSpouse)) {
            throw new IllegalArgumentException("Ошибка: Для проведения операции супруги должны быть " +
                    "в браке друг с другом");
        }
    }

    /**
     * Проверяет корректность связи "родитель-ребенок" для указанного ребенка и родителей.
     *
     * @param child Ребенок, связь которого проверяется.
     * @param father Предполагаемый отец ребенка.
     * @param mother Предполагаемая мать ребенка.
     * @throws IllegalArgumentException Если указанный отец или мать не совпадают с биологическими родителями ребенка.
     */
    public void validateParentChildConnection(Citizen child, Citizen father, Citizen mother) {
        if (!(child.getFather().getGender() == Gender.MALE)) {
            throw new IllegalArgumentException("Ошибка: Указанный отец не является мужчиной");
        }
        if (!(child.getMother().getGender() == Gender.FEMALE)) {
            throw new IllegalArgumentException("Ошибка: Указанная мать не является женщиной");
        }
        if (!child.getFather().equals(father)) {
            throw new IllegalArgumentException("Ошибка: Указанный отец не является биологическим родителем.");
        }
        if (!child.getMother().equals(mother)) {
            throw new IllegalArgumentException("Ошибка: Указанная мать не является биологическим родителем.");
        }
    }
}