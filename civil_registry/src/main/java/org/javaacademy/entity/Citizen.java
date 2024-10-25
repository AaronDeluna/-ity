package org.javaacademy.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.javaacademy.Gender;
import org.javaacademy.Human;

import static org.javaacademy.Gender.MALE;
import static org.javaacademy.Gender.FEMALE;
import static org.javaacademy.entity.MaritalStatus.DIVORCED;
import static org.javaacademy.entity.MaritalStatus.MARRIED;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Citizen extends Human {
    @NonNull
    MaritalStatus maritalStatus = DIVORCED;
    Citizen spouse;

    public Citizen(String name, String surname, String patronymic, Gender gender) {
        super(name, surname, patronymic, gender);
    }

    /**
     * Осуществляет развод для гражданина, устанавливая его статус на 'разведенный'
     * и очищая информацию о супруге.
     */
    public void divorce() {
        maritalStatus = DIVORCED;
        spouse = null;
    }

    /**
     * Устанавливает статус гражданина как 'женатый' и
     * связывает его с указанным супругом.
     *
     * @param spouse Супруг(а), с которым заключается брак.
     */
    public void marriage(Citizen spouse) {
        maritalStatus = MARRIED;
        this.spouse = spouse;
    }

    @Override
    public Citizen giveBirth(@NonNull String name, @NonNull String surname,
                             @NonNull String patronymic, @NonNull Gender gender,
                             @NonNull Human secondParent) {
        Citizen citizen = new Citizen(name, surname, patronymic, gender);
        if (secondParent.getGender() == MALE) {
            citizen.setParents(this, secondParent);
        } else if (secondParent.getGender() == FEMALE) {
            citizen.setParents(secondParent, this);
        }

        return citizen;
    }
}
