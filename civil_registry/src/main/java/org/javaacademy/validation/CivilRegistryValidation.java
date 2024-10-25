package org.javaacademy.validation;

import lombok.experimental.UtilityClass;
import org.javaacademy.entity.Citizen;
import org.javaacademy.entity.MaritalStatus;

@UtilityClass
public class CivilRegistryValidation {

    public void validateMarriageStatus(Citizen firstSpouse, Citizen secondSpouse) {
        if (firstSpouse.getMaritalStatus() != MaritalStatus.DIVORCED
                && secondSpouse.getMaritalStatus() != MaritalStatus.DIVORCED) {
            throw new IllegalArgumentException("Ошибка: Для проведения операции оба супруга должны быть разведены.");
        }
    }

    public void validateDivorceStatus(Citizen firstSpouse, Citizen secondSpouse) {
        if (firstSpouse.getMaritalStatus() != MaritalStatus.MARRIED
                && secondSpouse.getMaritalStatus() != MaritalStatus.MARRIED) {
            throw new IllegalArgumentException("Ошибка: Для проведения операции оба супруга должны быть в браке");
        }
    }
}
