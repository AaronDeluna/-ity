package org.javaacademy.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

/**
 * Класс Задача - содержит описание, статус (сделано или нет), количество часов трудозатрат.
 */

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {

  final String description;
  final BigDecimal hoursSpent;
  @Setter
  TaskStatus status = TaskStatus.NOT_COMPLETED;
}
