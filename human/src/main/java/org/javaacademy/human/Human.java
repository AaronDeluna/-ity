package org.javaacademy.human;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

/**
 * Класс Человек.
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Human {

  private String name;
  String surname;
  String patronymic;
  final Gender gender;
  @EqualsAndHashCode.Exclude
  Human father;
  @EqualsAndHashCode.Exclude
  Human mother;
  @EqualsAndHashCode.Exclude
  final List<Human> children = new ArrayList<>();

  /**
   * Конструктор принимает параметры и инициализирует объект класса Человек.
   *
   * @param name       - имя человека.
   * @param surname    - фамилия человека.
   * @param patronymic - отчество человека.
   * @param gender     - пол человека.
   */
  public Human(@NonNull String name, @NonNull String surname, @NonNull String patronymic,
      @NonNull Gender gender) {
    setName(name);
    setSurname(surname);
    setPatronymic(patronymic);
    this.gender = gender;
  }

  /**
   * Метод преобразования текста (первая заглавная буква, все остальные буквы в нижнем регистре).
   */
  private String capitalize(String text) {
    return StringUtils.capitalize(text.toLowerCase());
  }

  /**
   * Метод проверки строк на пустой ввод - наличие только пробелов или ни одного символа.
   */
  private void checkIsEmptyString(String str) {
    if (str.isBlank()) {
      throw new IllegalArgumentException(
          "Поля не должны быть пустыми или состоять только из пробелов.");
    }
  }

  private void setName(String name) {
    checkIsEmptyString(name);
    this.name = capitalize(name);
  }

  private void setSurname(String surname) {
    checkIsEmptyString(surname);
    this.surname = capitalize(surname);
  }

  private void setPatronymic(String patronymic) {
    checkIsEmptyString(patronymic);
    this.patronymic = capitalize(patronymic);
  }

  /**
   * Метод указания родителей.
   */
  protected void setParents(Human firstParent, Human secondParent) {
    if (firstParent.gender == Gender.MALE) {
      this.father = firstParent;
      this.mother = secondParent;
    }
    if (firstParent.gender == Gender.FEMALE) {
      this.mother = firstParent;
      this.father = secondParent;
    }
  }

  /**
   * Метод, которая отвечает за производство ребенка на свет.
   */
  public Human produceChild(@NonNull String name, @NonNull String surname,
      @NonNull String patronymic, @NonNull Gender gender, @NonNull Human secondParent) {

    this.checkOnOppositeGender(secondParent);

    Human child = new Human(name, surname, patronymic, gender);
    child.setParents(this, secondParent);
    this.getChildren().add(child);
    secondParent.getChildren().add(child);
    return child;
  }

  /**
   * Метод, проверяющий родителей на противоположность полов.
   */
  protected void checkOnOppositeGender(Human secondParent) {
    if (this.gender == secondParent.gender) {
      throw new IllegalArgumentException("Родители должны быть противоположных полов!");
    }
  }

  /**
   * Метод получения полного имени.
   */
  public String getFullName() {
    return String.format("%s %s %s", surname, name, patronymic);
  }
}
