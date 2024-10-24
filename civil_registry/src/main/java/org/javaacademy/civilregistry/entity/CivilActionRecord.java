package org.javaacademy.civilregistry.entity;

import lombok.Getter;
import org.javaacademy.entity.Citizen;

import java.time.LocalDate;
import java.util.List;

public class CivilActionRecord {
    @Getter
    private final LocalDate date;
    @Getter
    private final CivilActionType civilActionType;
    private final List<Citizen> citizens;

    public CivilActionRecord(LocalDate date, CivilActionType civilActionType, List<Citizen> citizens) {
        this.date = date;
        this.civilActionType = civilActionType;
        this.citizens = citizens;
    }
}
