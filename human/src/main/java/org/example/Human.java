package org.example;

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
@Setter
@ToString(exclude = "children")
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
    List<Human> children = new ArrayList<>();

    /**
     * Функция преобразования текста (первая заглавная буква, все остальные буквы в нижнем регистре).
     */

    private String capitalize(String text) {
        return StringUtils.capitalize(text.toLowerCase());
    }

    public Human(@NonNull String name, @NonNull String surname, @NonNull String patronymic, @NonNull Gender gender) {
        this.name = capitalize(name);
        this.surname = capitalize(surname);
        this.patronymic = capitalize(patronymic);
        this.gender = gender;
    }

    public void setName(@NonNull String name) {
        this.name = capitalize(name);
    }

    public void setSurname(@NonNull String surname) {
        this.surname = capitalize(surname);
    }

    public void setPatronymic(@NonNull String patronymic) {
        this.patronymic = capitalize(patronymic);
    }

    /**
     * Функция указания родителей.
     */

    public void setParents(@NonNull Human father, @NonNull Human mother) {
        if (father.gender == Gender.MALE && mother.gender == Gender.FEMALE) {
            this.father = father;
            this.mother = mother;
            father.getChildren().add(this);
            mother.getChildren().add(this);
        } else {
            throw new IllegalArgumentException("Родители должны быть противоположных полов!");
        }
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
