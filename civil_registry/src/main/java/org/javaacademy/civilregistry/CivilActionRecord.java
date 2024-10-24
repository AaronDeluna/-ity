package org.javaacademy.civilregistry;

import lombok.Getter;
import org.javaacademy.citizen.Citizen;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CivilActionRecord {
    private LocalDate date;
    private TypeCivilAction typeCivilAction;
    private List<Citizen> citizens;

    public CivilActionRecord(LocalDate date, TypeCivilAction typeCivilAction, List<Citizen> citizens) {
        this.date = date;
        this.typeCivilAction = typeCivilAction;
        this.citizens = citizens;
    }
}
