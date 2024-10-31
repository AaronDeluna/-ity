package org.javaacademy.task;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * Класс Задача - содержит описание, статус (сделано или нет), количество часов трудозатрат.
 */

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {

  final String description;
  final BigDecimal hoursOfLabor;
  @Setter
  TaskStatus status = TaskStatus.NOT_COMPLETED;
}
