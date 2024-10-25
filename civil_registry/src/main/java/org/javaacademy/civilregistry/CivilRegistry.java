package org.javaacademy.civilregistry;

import org.javaacademy.Gender;
import org.javaacademy.civilregistry.entity.CivilActionRecord;
import org.javaacademy.civilregistry.entity.CivilActionType;
import org.javaacademy.entity.Citizen;
import org.javaacademy.entity.MaritalStatus;

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
        if (father.getGender()!= Gender.MALE) {
            throw new IllegalArgumentException("Отец должен быть мужского пола!");
        }
        if (mother.getGender()!= Gender.FEMALE) {
            throw new IllegalArgumentException("Мать должна быть женского пола!");
        }
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
        if (firstSpouse.getGender()== secondSpouse.getGender()) {
            throw new IllegalArgumentException("Брачующиеся должны быть противоположных полов!");
        }
        if (firstSpouse.getMaritalStatus()== MaritalStatus.MARRIED||
                secondSpouse.getMaritalStatus()== MaritalStatus.MARRIED) {
            throw new IllegalArgumentException("Один из брачующихся уже состоит в браке");
        }
        CivilActionRecord record = new CivilActionRecord(
                date,
                CivilActionType.WEDDING_REGISTRATION,
                List.of(firstSpouse, secondSpouse));
        addCivilActionRecord(record);
        firstSpouse.setMaritalStatus(MaritalStatus.MARRIED);
        firstSpouse.setSpouse(secondSpouse);
        secondSpouse.setMaritalStatus(MaritalStatus.MARRIED);
        secondSpouse.setSpouse(firstSpouse);
    }

    /**
     * метод расторжения брака
     * @param firstSpouse - первый супруг
     * @param secondSpouse - второй супруг
     * @param date - дата регистрации записи
     */
    public void registrationDivorce(Citizen firstSpouse, Citizen secondSpouse, LocalDate date) {
        if (!firstSpouse.getSpouse().equals(secondSpouse)) {
            throw new IllegalArgumentException("Расторгающие брак не состоят в браке друг с другом");
        }
        CivilActionRecord record = new CivilActionRecord(
                date,
                CivilActionType.DIVORCE_REGISTRATION,
                List.of(firstSpouse, secondSpouse));
        addCivilActionRecord(record);
        firstSpouse.setMaritalStatus(MaritalStatus.DIVORCED);
        firstSpouse.setSpouse(null);
        secondSpouse.setMaritalStatus(MaritalStatus.DIVORCED);
        secondSpouse.setSpouse(null);
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
