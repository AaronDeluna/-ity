package org.javaacademy.citizen;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MarriageStatus {
    DIVORCED("разведен/а"),
    MARRIED("в браке"),
    SINGLE("не женат/не замужем");
    private final String description;
}
