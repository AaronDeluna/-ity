package org.javaacademy.civilregistry;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CivilActionType {
    BIRTH_REGISTRATION("регистрация рождения"),
    WEDDING_REGISTRATION("регистрация свадьбы"),
    DIVORCE_REGISTRATION("регистрация развода");
    private final String description;
}
