package org.javaacademy.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Семейное положение.
 */
@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public enum MaritalStatus {
  DIVORCED("разведен/а"),
  MARRIED("в браке"),
  SINGLE("не женат/не замужем");

  String description;
}
