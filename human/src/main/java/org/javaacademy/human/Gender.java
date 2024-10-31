package org.javaacademy.human;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Пол человека (мужчина/женщина).
 */

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public enum Gender {
  MALE("Мужской"),
  FEMALE("Женский");

  String description;
}
