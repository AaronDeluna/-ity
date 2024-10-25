package org.javaacademy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.javaacademy.civilregistry.CivilActionType;
import org.javaacademy.entity.Citizen;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CivilActionRecord {
    @Getter
    private final LocalDate date;
    @Getter
    private final CivilActionType civilActionType;
    private final List<Citizen> citizens;
}
