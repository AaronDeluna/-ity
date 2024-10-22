package org.javaacademy.task;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Статус задания
 */

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public enum TaskStatus {
    NOT_COMPLETED("Не сделана"),
    COMPLETED("Сделана");

    String description;
}
