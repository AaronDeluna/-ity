package org.javaacademy.civilregistry;

import lombok.Getter;
import lombok.Value;
import org.javaacademy.citizen.Citizen;

import java.time.LocalDate;
import java.util.List;


@Value
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
