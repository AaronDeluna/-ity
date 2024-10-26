package org.javaacademy.human;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

/**
 * Класс Человек.
 */
@Getter
public class Human {

  String name;
  String surname;
  String patronymic;
  final Gender gender;
  Human father;
  Human mother;
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
   * Функция преобразования текста (первая заглавная буква, все остальные буквы в нижнем регистре).
   */

  private String capitalize(String text) {
    return StringUtils.capitalize(text.toLowerCase());
  }

  /**
   * Функция проверки строк на пустой ввод - наличие только пробелов или ни одного символа.
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
   * Функция указания родителей.
   */

  public void setParents(@NonNull Human father, @NonNull Human mother) {
    this.father = father;
    this.mother = mother;
    father.getChildren().add(this);
    mother.getChildren().add(this);
  }

  /**
   * Функция, которая отвечает за производство ребенка на свет.
   */

  public Human produceChild(@NonNull String name, @NonNull String surname,
      @NonNull String patronymic,
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
   * Функция получения полного имени.
   */

  public String getFullName() {
    return String.format("%s %s %s", surname, name, patronymic);
  }
}
