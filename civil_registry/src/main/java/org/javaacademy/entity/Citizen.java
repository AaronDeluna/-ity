package org.javaacademy.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.javaacademy.Gender;
import org.javaacademy.Human;

@Getter
@Setter
public class Citizen extends Human {
    @NonNull
    private MaritalStatus maritalStatus;
    private Citizen spouse;

    public Citizen(String name, String surname, String patronymic, Gender gender) {
        super(name, surname, patronymic, gender);
    }

    @Override
    public Citizen giveBirth(@NonNull String name, @NonNull String surname,
                             @NonNull String patronymic, @NonNull Gender gender,
                             @NonNull Human secondParent) {
        Citizen citizen = new Citizen(name, surname, patronymic, gender);
        citizen.setParents(this, secondParent);
        return citizen;
    }
}
