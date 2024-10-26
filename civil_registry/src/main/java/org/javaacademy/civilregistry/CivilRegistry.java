package org.javaacademy.civilregistry;

import static org.javaacademy.civilregistry.CivilActionType.BIRTH_REGISTRATION;
import static org.javaacademy.civilregistry.CivilActionType.DIVORCE_REGISTRATION;
import static org.javaacademy.civilregistry.CivilActionType.WEDDING_REGISTRATION;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.javaacademy.entity.Citizen;
import org.javaacademy.entity.CivilActionRecord;
import org.javaacademy.entity.MaritalStatus;
import org.javaacademy.validation.CivilRegistryValidation;

/**
 * класс ЗАГС.
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CivilRegistry {

  String name;
  Map<LocalDate, List<CivilActionRecord>> civilActionRecords = new TreeMap<>();

  public CivilRegistry(@NonNull String name) {
    this.name = name;
  }

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
    CivilRegistryValidation.checkOnNotMarriedStatus(firstSpouse, secondSpouse);
    CivilActionRecord record = new CivilActionRecord(
        date,
        WEDDING_REGISTRATION,
        List.of(firstSpouse, secondSpouse));
    addCivilActionRecord(record);
    changeStatusCivilActionOnMarried(firstSpouse, secondSpouse);
    changeStatusCivilActionOnMarried(secondSpouse, firstSpouse);
  }

  /**
   * Осуществляет изменения статуса гражданина при свадьбе.
   */
  private void changeStatusCivilActionOnMarried(Citizen firstCitizen, Citizen secondCitizen) {
    firstCitizen.setSpouse(secondCitizen);
    firstCitizen.setMaritalStatus(MaritalStatus.MARRIED);
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
    CivilRegistryValidation.checkOnMarriedStatus(firstSpouse, secondSpouse);
    CivilActionRecord record = new CivilActionRecord(
        date,
        DIVORCE_REGISTRATION,
        List.of(firstSpouse, secondSpouse));
    addCivilActionRecord(record);
    changeStatusCivilActionOnDivorce(firstSpouse);
    changeStatusCivilActionOnDivorce(secondSpouse);
  }

  /**
   * Осуществляет изменения статуса гражданина при разводе.
   */
  private void changeStatusCivilActionOnDivorce(Citizen citizen) {
    citizen.setSpouse(null);
    citizen.setMaritalStatus(MaritalStatus.DIVORCED);
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
   * Получает статистику ЗАГСа на указанную дату, включая количество свадеб, разводов и рождений.
   *
   * @param date Дата для вывода статистики.
   */
  public void statisticsForDate(LocalDate date) {
    Map<CivilActionType, Long> countCivilActionByType = civilActionRecords.get(date).stream()
        .collect(Collectors.groupingBy(
            CivilActionRecord::getCivilActionType,
            Collectors.counting()
        ));
    printStatisticsForDate(countCivilActionByType, date);
  }

  /**
   * Печать в консоль статистики ЗАГСа на указанную дату.
   *
   * @param countCivilAction статистика (кол-во записей гражданского действия по типам).
   * @param date             дата на которую выводится статистика.
   */
  private void printStatisticsForDate(Map<CivilActionType, Long> countCivilAction,
      LocalDate date) {
    System.out.printf("Статистика по ЗАГС: %s\n", this.name);
    System.out.printf(
        "Дата %s: количество свадеб - %d, количество разводов - %d, количество рождений - %d\n",
        date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
        countCivilAction.getOrDefault(WEDDING_REGISTRATION, 0L),
        countCivilAction.getOrDefault(DIVORCE_REGISTRATION, 0L),
        countCivilAction.getOrDefault(BIRTH_REGISTRATION, 0L));
  }
}
