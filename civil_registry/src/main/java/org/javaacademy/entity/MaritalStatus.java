package org.javaacademy.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MaritalStatus {
    DIVORCED("разведен/а"),
    MARRIED("в браке"),
    SINGLE("не женат/не замужем");
    private final String description;
}
