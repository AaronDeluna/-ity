package org.example;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public enum Gender {
    MALE("Мужской"),
    FEMALE("Женский");

    String description;
}
