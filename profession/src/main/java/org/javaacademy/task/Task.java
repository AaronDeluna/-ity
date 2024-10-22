package org.javaacademy.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {
    final String description;
    final int hoursSpent;
    @Setter
    TaskStatus status = TaskStatus.NOT_COMPLETED;
}
