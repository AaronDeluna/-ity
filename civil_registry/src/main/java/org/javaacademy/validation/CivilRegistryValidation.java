package org.javaacademy.validation;

import lombok.experimental.UtilityClass;
import org.javaacademy.entity.Citizen;
import org.javaacademy.entity.MaritalStatus;

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
        if (firstSpouse.getMaritalStatus() != MaritalStatus.DIVORCED
                && secondSpouse.getMaritalStatus() != MaritalStatus.DIVORCED) {
            throw new IllegalArgumentException("Ошибка: Для проведения операции оба супруга должны быть разведены.");
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
        if (firstSpouse.getMaritalStatus() != MaritalStatus.MARRIED
                && secondSpouse.getMaritalStatus() != MaritalStatus.MARRIED) {
            throw new IllegalArgumentException("Ошибка: Для проведения операции оба супруга должны быть в браке");
        }
    }
}
