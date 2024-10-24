package org.javaacademy.civilregistry;

import lombok.Getter;
import lombok.Setter;
import org.javaacademy.citizen.Citizen;
import org.javaacademy.citizen.MarriageStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
    класс ЗАГСа
 */
@Getter
@Setter
public class CivilRegistry {
    private String name;
    private List<CivilActionRecord> civilActionRecords;

    public CivilRegistry(String name) {
        this.name = name;
        this.civilActionRecords = new ArrayList<CivilActionRecord>();
    }

    /*
    метод регистрации новорожденного
    */
    public void registrationBirth(Citizen child, Citizen father, Citizen mother, LocalDate dateReistration){
        CivilActionRecord record = new CivilActionRecord(
                dateReistration,
                TypeCivilAction.BIRTH_REGISTRATION,
                List.of(child, father, mother));
        addCivilActionRecord(record);
    }

    /*
    метод расторжения брака
    */
    public void registrationDivorce(Citizen firstSpouse, Citizen secondSpouse, LocalDate dateReistration){
        CivilActionRecord record = new CivilActionRecord(
                dateReistration,
                TypeCivilAction.DIVORCE_REGISTRATION,
                List.of(firstSpouse, secondSpouse));
        addCivilActionRecord(record);
        firstSpouse.setMarriageStatus(MarriageStatus.DIVORCED);
        firstSpouse.setSpouse(null);
        secondSpouse.setMarriageStatus(MarriageStatus.DIVORCED);
        secondSpouse.setSpouse(null);


    }

    /*
    метод регистрации брака
    */
    public void registrationMarriage(Citizen firstSpouse, Citizen secondSpouse, LocalDate dateReistration){
        CivilActionRecord record = new CivilActionRecord(
                dateReistration,
                TypeCivilAction.WEDDING_REGISTRATION,
                List.of(firstSpouse, secondSpouse));
        addCivilActionRecord(record);
        firstSpouse.setMarriageStatus(MarriageStatus.MARRIED);
        firstSpouse.setSpouse(secondSpouse);
        secondSpouse.setMarriageStatus(MarriageStatus.MARRIED);
        secondSpouse.setSpouse(firstSpouse);
    }

    /*
    метод добавления записи гражданского действия
    */
    private void addCivilActionRecord(CivilActionRecord record) {
        civilActionRecords.add(record);
        civilActionRecords.sort(Comparator.comparing(CivilActionRecord::getDate));
    }

    /*
    Печать статистики ЗАГСа на дату
    */
    public void statistic(LocalDate date){
        List<CivilActionRecord> recordsDay = civilActionRecords.stream().filter(r -> r.getDate().isEqual(date)).toList();
        int marriageCount = (int) recordsDay.stream().
                filter(r -> r.getTypeCivilAction() == TypeCivilAction.WEDDING_REGISTRATION).count();
        int divorceCount = (int) recordsDay.stream().
                filter(r -> r.getTypeCivilAction() == TypeCivilAction.DIVORCE_REGISTRATION).count();
        int birthCount = (int) recordsDay.stream().
                filter(r -> r.getTypeCivilAction() == TypeCivilAction.BIRTH_REGISTRATION).count();
        System.out.println("Статистика по ЗАГС: " + name );
        System.out.println("Дата " + date + " количество свадеб - " + marriageCount + ", количество разводов - " +
                divorceCount + ", количество рождений - " + birthCount);
    }


}
