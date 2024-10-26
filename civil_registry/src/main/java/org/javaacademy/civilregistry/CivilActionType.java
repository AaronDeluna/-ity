package org.javaacademy.civilregistry;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Сущность Тип гражданского действия.
 */

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public enum CivilActionType {
  BIRTH_REGISTRATION("регистрация рождения"),
  WEDDING_REGISTRATION("регистрация свадьбы"),
  DIVORCE_REGISTRATION("регистрация развода");

  String description;
}
