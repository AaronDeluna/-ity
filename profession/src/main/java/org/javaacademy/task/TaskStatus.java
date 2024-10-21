package org.javaacademy.task;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Статус задания
 */

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public enum TaskStatus {
    NOT_COMPLETED("Сделано"),
    COMPLETED("Не сделано");

    String description;
}
