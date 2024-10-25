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
    private MaritalStatus maritalStatus = MaritalStatus.DIVORCED;
    private Citizen spouse;

    public Citizen(String name, String surname, String patronymic, Gender gender) {
        super(name, surname, patronymic, gender);
    }

    /**
     * Осуществляет развод для гражданина, устанавливая его статус на 'разведенный'
     * и очищая информацию о супруге.
     */
    public void divorce() {
        maritalStatus = MaritalStatus.DIVORCED;
        spouse = null;
    }

    /**
     * Устанавливает статус гражданина как 'женатый' и
     * связывает его с указанным супругом.
     *
     * @param spouse Супруг(а), с которым заключается брак.
     */
    public void marriage(Citizen spouse) {
        maritalStatus = MaritalStatus.MARRIED;
        this.spouse = spouse;
    }

    @Override
    public Citizen giveBirth(@NonNull String name, @NonNull String surname,
                             @NonNull String patronymic, @NonNull Gender gender,
                             @NonNull Human secondParent) {
        Citizen citizen = new Citizen(name, surname, patronymic, gender);
        if (secondParent.getGender() == Gender.MALE) {
            citizen.setParents(this, secondParent);
        } else if (secondParent.getGender() == Gender.FEMALE) {
            citizen.setParents(secondParent, this);
        }

        return citizen;
    }
}
