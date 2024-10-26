package org.javaacademy.civilregistry;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.javaacademy.entity.Citizen;
import org.javaacademy.entity.CivilActionRecord;
import org.javaacademy.validation.CivilRegistryValidation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static org.javaacademy.civilregistry.CivilActionType.BIRTH_REGISTRATION;
import static org.javaacademy.civilregistry.CivilActionType.WEDDING_REGISTRATION;
import static org.javaacademy.civilregistry.CivilActionType.DIVORCE_REGISTRATION;

/**
 * класс ЗАГС.
 */
@AllArgsConstructor
public class CivilRegistry {
    private final String name;
    private final TreeMap<LocalDate, List<CivilActionRecord>> civilActionRecords = new TreeMap<>();

    /**
     * Регистрация рождения ребенка.
     *
     * @param child  Ребенок, который рождается.
     * @param father Отец ребенка.
     * @param mother Мать ребенка.
     * @param date   Дата регистрации рождения.
     */
    public void birthOfChild(Citizen child, Citizen father, Citizen mother, LocalDate date) {
        CivilRegistryValidation.validateParentChildConnection(child, father, mother);
        CivilActionRecord record = new CivilActionRecord(
                date,
                BIRTH_REGISTRATION,
                List.of(child, father, mother));
        addCivilActionRecord(record);
    }

    /**
     * Регистрация брака между супругами.
     *
     * @param firstSpouse  Первый супруг, который вступает в брак.
     * @param secondSpouse Второй супруг, который вступает в брак.
     * @param date         Дата регистрации брака.
     * @throws IllegalArgumentException если один из супругов уже состоит в браке.
     */
    public void registrationMarriage(Citizen firstSpouse, Citizen secondSpouse, LocalDate date) {
        CivilRegistryValidation.validateMarriageStatus(firstSpouse, secondSpouse);
        CivilActionRecord record = new CivilActionRecord(
                date,
                WEDDING_REGISTRATION,
                List.of(firstSpouse, secondSpouse));
        addCivilActionRecord(record);
        firstSpouse.marriage(secondSpouse);
        secondSpouse.marriage(firstSpouse);
    }

    /**
     * Регистрация развода между супругами.
     *
     * @param firstSpouse  Первый супруг, который разводится.
     * @param secondSpouse Второй супруг, который разводится.
     * @param date         Дата регистрации развода.
     * @throws IllegalArgumentException если один из супругов уже разведён или не может развестись.
     */
    public void registrationDivorce(Citizen firstSpouse, Citizen secondSpouse, LocalDate date) {
        CivilRegistryValidation.validateDivorceStatus(firstSpouse, secondSpouse);
        CivilActionRecord record = new CivilActionRecord(
                date,
                DIVORCE_REGISTRATION,
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
                countCivilActionByType.getOrDefault(WEDDING_REGISTRATION, 0L),
                countCivilActionByType.getOrDefault(DIVORCE_REGISTRATION, 0L),
                countCivilActionByType.getOrDefault(BIRTH_REGISTRATION, 0L));
    }
}
