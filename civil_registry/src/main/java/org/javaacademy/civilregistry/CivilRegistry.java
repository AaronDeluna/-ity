package org.javaacademy.civilregistry;

import lombok.AllArgsConstructor;
import org.javaacademy.entity.Citizen;
import org.javaacademy.entity.CivilActionRecord;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


/**
 * класс ЗАГС.
 */
@AllArgsConstructor
public class CivilRegistry {
    private final String name;
    private final TreeMap<LocalDate, List<CivilActionRecord>> civilActionRecords = new TreeMap<>();

    /*
    метод регистрации новорожденного
    */
    public void birthOfChild(Citizen child, Citizen father, Citizen mother, LocalDate date) {
        CivilActionRecord record = new CivilActionRecord(
                date,
                CivilActionType.BIRTH_REGISTRATION,
                List.of(child, father, mother));
        addCivilActionRecord(record);
    }

    /*
    метод регистрации брака
    */
    public void registrationMarriage(Citizen firstSpouse, Citizen secondSpouse, LocalDate date) {
        CivilActionRecord record = new CivilActionRecord(
                date,
                CivilActionType.WEDDING_REGISTRATION,
                List.of(firstSpouse, secondSpouse));
        addCivilActionRecord(record);
        firstSpouse.marriage(secondSpouse);
        secondSpouse.marriage(firstSpouse);
    }

    /*
    метод расторжения брака
    */
    public void registrationDivorce(Citizen firstSpouse, Citizen secondSpouse, LocalDate date) {
        CivilActionRecord record = new CivilActionRecord(
                date,
                CivilActionType.DIVORCE_REGISTRATION,
                List.of(firstSpouse, secondSpouse));
        addCivilActionRecord(record);
        firstSpouse.divorce();
        secondSpouse.divorce();
    }

    /**
     * Добавляет запись о гражданском действии в список записей по дате.
     *
     * @param record Запись о гражданском действии для добавления.
     */
    private void addCivilActionRecord(CivilActionRecord record) {
        civilActionRecords.computeIfAbsent(record.getDate(), k -> new ArrayList<>()).add(record);
    }

    /**
     * Выводит статистику ЗАГСа на указанную дату, включая количество свадеб, разводов и рождений.
     *
     * @param date Дата для вывода статистики.
     */
    public void statisticsForDate(LocalDate date) {
        Map<CivilActionType, Long> countCivilActionByType = civilActionRecords.get(date).stream()
                .collect(Collectors.groupingBy(
                        CivilActionRecord::getCivilActionType,
                        Collectors.counting()
                ));

        System.out.printf("Статистика по ЗАГС: %s\n", this.name);
        System.out.printf("Дата %s: количество свадеб - %d, количество разводов - %d, количество рождений - %d\n",
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                countCivilActionByType.getOrDefault(CivilActionType.WEDDING_REGISTRATION, 0L),
                countCivilActionByType.getOrDefault(CivilActionType.DIVORCE_REGISTRATION, 0L),
                countCivilActionByType.getOrDefault(CivilActionType.BIRTH_REGISTRATION, 0L));
    }
}
