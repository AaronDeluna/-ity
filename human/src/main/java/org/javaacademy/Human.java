package org.javaacademy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter(value = AccessLevel.PRIVATE)
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Human {
    @NonNull
    String name;
    @NonNull
    String surname;
    @NonNull
    String patronymic;
    @NonNull
    Gender gender;

    Human father;
    Human mother;
    @ToString.Exclude
    List<Human> children = new ArrayList<>();

    /**
     * Функция преобразования текста (первая заглавная буква, все остальные буквы в нижнем регистре).
     */

    private String capitalize(String text) {
        return StringUtils.capitalize(text.toLowerCase());
    }

    /**
     * Функция проверки строк на путоту.
     */

    private void checkIsEmptyString(String str, String fieldName) {
        if(str.trim().isEmpty()){
            throw new IllegalArgumentException
                    (fieldName + " не должно быть пустым или состоять только из пробелов.");
        }
    }

    public Human(String name, String surname, String patronymic, Gender gender) {
        setName(name);
        setSurname(surname);
        setPatronymic(patronymic);
        this.gender = gender;
    }

    private void setName(@NonNull String name) {
        checkIsEmptyString(name, "Имя");
        this.name = capitalize(name);
    }

    private void setSurname(@NonNull String surname) {
        checkIsEmptyString(surname, "Фамилия");
        this.surname = capitalize(surname);
    }

    private void setPatronymic(@NonNull String patronymic) {
        checkIsEmptyString(patronymic, "Отчество");
        this.patronymic = capitalize(patronymic);
    }

    /**
     * Функция указания родителей.
     */

    private void setParents(@NonNull Human father, @NonNull Human mother) {
        setFather(father);
        setMother(mother);
        father.getChildren().add(this);
        mother.getChildren().add(this);
    }

    /**
     * Функция, которая отвечает за производство ребенка на свет.
     */

    public Human giveBirth(@NonNull String name, @NonNull String surname, @NonNull String patronymic,
                           @NonNull Gender gender, @NonNull Human secondParent) {
        if (this.gender == secondParent.gender) {
            throw new IllegalArgumentException("Родители должны быть противоположных полов!");
        }

        Human child = new Human(name, surname, patronymic, gender);
        if (this.gender == Gender.MALE) {
            child.setParents(this, secondParent);
        } else {
            child.setParents(secondParent, this);
        }
        return child;
    }

    /**
     * Функция получениия полного имени.
     */

    public String getFullName() {
        return String.format("%s %s %s", surname, name, patronymic);
    }
}
