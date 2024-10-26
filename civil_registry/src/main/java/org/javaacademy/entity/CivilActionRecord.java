package org.javaacademy.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.javaacademy.civilregistry.CivilActionType;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CivilActionRecord {
    @Getter
    LocalDate date;
    @Getter
    CivilActionType civilActionType;
    List<Citizen> citizens;
}
