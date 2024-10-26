package org.javaacademy.entity;

import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.javaacademy.civilregistry.CivilActionType;
import org.javaacademy.human.Human;

/**
 * Сущность Запись гражданского действия.
 */
@RequiredArgsConstructor
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CivilActionRecord {

  LocalDate date;
  CivilActionType civilActionType;
  List<Human> citizens;
}
