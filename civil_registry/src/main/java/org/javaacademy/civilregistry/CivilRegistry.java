package org.javaacademy.civilregistry;

import org.javaacademy.Gender;
import org.javaacademy.civilregistry.entity.CivilActionRecord;
import org.javaacademy.civilregistry.entity.CivilActionType;
import org.javaacademy.entity.Citizen;
import org.javaacademy.entity.MaritalStatus;
import org.javaacademy.validation.CivilRegistryValidation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * класс ЗАГС
 * name -название ЗАГСа
 * civilActionRecords - коллекция записей гражданского действия
 */
public class CivilRegistry {
    private final String name;
    private final TreeMap<LocalDate, List<CivilActionRecord>> civilActionRecords = new TreeMap<>();

    public CivilRegistry(String name) {
        this.name = name;
    }

    /**
     * метод регистрации новорожденного
     * @param child - ребенок
     * @param father -отец
     * @param mother - мать
     * @param date - дата регистрации записи
    */
    public void birthOfChild(Citizen child, Citizen father, Citizen mother, LocalDate date) {
        CivilRegistryValidation.validateParentChildConnection(child, father, mother);
        CivilActionRecord record = new CivilActionRecord(
                date,
                CivilActionType.BIRTH_REGISTRATION,
                List.of(child, father, mother));
        addCivilActionRecord(record);
    }

    /**
     * метод регистрации брака
     * @param firstSpouse - первый брачующийся
     * @param secondSpouse - второй брачующийся
     * @param date - дата регистрации записи
     */
    public void registrationMarriage(Citizen firstSpouse, Citizen secondSpouse, LocalDate date) {
        CivilRegistryValidation.validateMarriageStatus(firstSpouse, secondSpouse);
        CivilActionRecord record = new CivilActionRecord(
                date,
                CivilActionType.WEDDING_REGISTRATION,
                List.of(firstSpouse, secondSpouse));
        addCivilActionRecord(record);
        registrationCitizen(firstSpouse, secondSpouse, CivilActionType.WEDDING_REGISTRATION);
    }

    /**
     *
     * @param firstSpouse - первый супруг
     * @param secondSpouse - второй супруе
     * @param civilActionType - вид гражданского действия (заключение брака или расторжение брака)
     */
    private void registrationCitizen(Citizen firstSpouse, Citizen secondSpouse, CivilActionType civilActionType) {
        if (civilActionType == CivilActionType.WEDDING_REGISTRATION) {
            firstSpouse.setMaritalStatus(MaritalStatus.MARRIED);
            firstSpouse.setSpouse(secondSpouse);
            secondSpouse.setMaritalStatus(MaritalStatus.MARRIED);
            secondSpouse.setSpouse(firstSpouse);
        } else if (civilActionType == CivilActionType.DIVORCE_REGISTRATION) {
            firstSpouse.setMaritalStatus(MaritalStatus.DIVORCED);
            firstSpouse.setSpouse(null);
            secondSpouse.setMaritalStatus(MaritalStatus.DIVORCED);
            secondSpouse.setSpouse(null);
        }
    }

    /**
     * метод расторжения брака
     * @param firstSpouse - первый супруг
     * @param secondSpouse - второй супруг
     * @param date - дата регистрации записи
     */
    public void registrationDivorce(Citizen firstSpouse, Citizen secondSpouse, LocalDate date) {
        CivilRegistryValidation.validateDivorceStatus(firstSpouse, secondSpouse);
        CivilActionRecord record = new CivilActionRecord(
                date,
                CivilActionType.DIVORCE_REGISTRATION,
                List.of(firstSpouse, secondSpouse));
        addCivilActionRecord(record);
        registrationCitizen(firstSpouse, secondSpouse, CivilActionType.DIVORCE_REGISTRATION);
    }

    /**
     * метод добавления записи гражданского действия в общий список     *
     * @param record - новая запись
     */
    private void addCivilActionRecord(CivilActionRecord record) {
        civilActionRecords.merge(record.getDate(), new ArrayList<>(List.of(record)), (oldValue, newValue) -> {
                    oldValue.add(newValue.get(0));
                    return oldValue;
                }
        );
    }


    /**
     * метод вывыода статистики ЗАГСа
     * @param date - Дата, на которую сформирован отчет
     * */
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
