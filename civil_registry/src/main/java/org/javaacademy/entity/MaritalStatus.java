package org.javaacademy.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum MaritalStatus {
    DIVORCED("разведен/а"),
    MARRIED("в браке"),
    SINGLE("не женат/не замужем");
    String description;
}
