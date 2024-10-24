package org.javaacademy.civilregistry;

import lombok.Getter;
import lombok.Setter;
import org.javaacademy.civilregistry.entity.CivilActionRecord;
import org.javaacademy.civilregistry.entity.CivilActionType;
import org.javaacademy.entity.Citizen;
import org.javaacademy.entity.MaritalStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
    класс ЗАГСа
 */
@Getter
@Setter
public class CivilRegistry {
    private final String name;
    private Map<LocalDate, List<CivilActionRecord>> civilActionRecords = new TreeMap<>();

    public CivilRegistry(String name) {
        this.name = name;
    }

    /*
    метод регистрации новорожденного
    */
    public void birthOfChild(Citizen child, Citizen father, Citizen mother, LocalDate dateRegistration) {
        CivilActionRecord record = new CivilActionRecord(
                dateRegistration,
                CivilActionType.BIRTH_REGISTRATION,
                List.of(child, father, mother));
        addCivilActionRecord(record);
    }

    /*
    метод регистрации брака
    */
    public void registrationMarriage(Citizen firstSpouse, Citizen secondSpouse, LocalDate dateRegistration) {
        CivilActionRecord record = new CivilActionRecord(
                dateRegistration,
                CivilActionType.WEDDING_REGISTRATION,
                List.of(firstSpouse, secondSpouse));
        addCivilActionRecord(record);
        firstSpouse.setMaritalStatus(MaritalStatus.MARRIED);
        firstSpouse.setSpouse(secondSpouse);
        secondSpouse.setMaritalStatus(MaritalStatus.MARRIED);
        secondSpouse.setSpouse(firstSpouse);
    }

    /*
    метод расторжения брака
    */
    public void registrationDivorce(Citizen firstSpouse, Citizen secondSpouse, LocalDate dateReistration) {
        CivilActionRecord record = new CivilActionRecord(
                dateReistration,
                CivilActionType.DIVORCE_REGISTRATION,
                List.of(firstSpouse, secondSpouse));
        addCivilActionRecord(record);
        firstSpouse.setMaritalStatus(MaritalStatus.DIVORCED);
        firstSpouse.setSpouse(null);
        secondSpouse.setMaritalStatus(MaritalStatus.DIVORCED);
        secondSpouse.setSpouse(null);
    }

    /*
    метод добавления записи гражданского действия
    */
    private void addCivilActionRecord(CivilActionRecord record) {
        civilActionRecords.merge(record.getDate(), List.of(record), (oldValue, newValue) -> {
                    oldValue.add(record);
                    return oldValue;
                }
        );
    }

    /*
    Печать статистики ЗАГСа на дату
    */
    public void statistic(LocalDate date) {
        List<CivilActionRecord> recordsDay = civilActionRecords.stream().filter(r -> r.getDate().isEqual(date)).toList();
        int marriageCount = (int) recordsDay.stream().
                filter(r -> r.getCivilActionType() == CivilActionType.WEDDING_REGISTRATION).count();
        int divorceCount = (int) recordsDay.stream().
                filter(r -> r.getCivilActionType() == CivilActionType.DIVORCE_REGISTRATION).count();
        int birthCount = (int) recordsDay.stream().
                filter(r -> r.getCivilActionType() == CivilActionType.BIRTH_REGISTRATION).count();
        System.out.println("Статистика по ЗАГС: " + name);
        System.out.println("Дата " + date + " количество свадеб - " + marriageCount + ", количество разводов - " +
                divorceCount + ", количество рождений - " + birthCount);
    }


}
