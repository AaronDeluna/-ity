package org.javaacademy.civilregistry;

import org.javaacademy.entity.Citizen;
import org.javaacademy.entity.CivilActionRecord;
import org.javaacademy.entity.MaritalStatus;
import org.javaacademy.human.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

@DisplayName("Тестирование ЗАГСА")
class CivilRegistryTest {

  private CivilRegistry civilRegistry;
  private Citizen child;
  private Citizen firstParent;
  private Citizen secondParent;
  private LocalDate date;

  @BeforeEach
  void setUp() {
    civilRegistry = new CivilRegistry("Тестовый ЗАГС");
    child = new Citizen("Иван", "Милованов", "Александрович", Gender.MALE);
    firstParent = new Citizen("Иван", "Милованов", "Александрович", Gender.MALE);
    secondParent = new Citizen("Иван", "Милованов", "Александрович", Gender.MALE);
    date = LocalDate.now();
  }

  @DisplayName("Успешная регистрация ребенка")
  @Test
  void shouldRegisterBirthOfChild() {
    civilRegistry.birthOfChild(child, firstParent, secondParent, date);
    CivilActionType expectedCivilActionType = CivilActionType.BIRTH_REGISTRATION;
    Assertions.assertEquals(expectedCivilActionType, getFirstCivilActionType());
  }

  @DisplayName("Список гражданских действий не null")
  @Test
  void shouldNotBeNullCivilActionRecords() {
    civilRegistry.birthOfChild(child, firstParent, secondParent, date);
    List<CivilActionRecord> civilActionRecordList = civilRegistry.getCivilActionRecords().get(date);
    Assertions.assertNotNull(civilActionRecordList);
  }

  @DisplayName("Список гражданских действий не пустой")
  @Test
  void shouldNotBeEmptyCivilActionRecords() {
    civilRegistry.birthOfChild(child, firstParent, secondParent, date);
    List<CivilActionRecord> civilActionRecordList = civilRegistry.getCivilActionRecords().get(date);
    Assertions.assertFalse(civilActionRecordList.isEmpty());
  }

  @DisplayName("Успешно установлен статус 'в браке'")
  @Test
  void marriageStatusSuccessfullyEstablished() {
    MaritalStatus expectedMaritalStatus = MaritalStatus.MARRIED;
    civilRegistry.registrationMarriage(firstParent, secondParent, date);
    Assertions.assertEquals(expectedMaritalStatus, firstParent.getMaritalStatus());
  }

  @DisplayName("Успешная запись типа гражданского действия 'регистрация свадьбы'")
  @Test
  void shouldRecordMarriageCivilActionSuccessfully() {
    CivilActionType expectedCivilActionType = CivilActionType.WEDDING_REGISTRATION;
    civilRegistry.registrationMarriage(firstParent, secondParent, date);
    Assertions.assertEquals(expectedCivilActionType, getFirstCivilActionType());
  }

  @DisplayName("Успешно выдает исключение NPE при регситрации брака")
  @Test
  void throwsNPEWhenRegisteringMarriageWithNullCitizen() {
    Assertions.assertThrows(NullPointerException.class,
            () -> civilRegistry.registrationMarriage(null, secondParent, date));
  }

  @DisplayName("Успешная регистрация развода")
  @Test
  void shouldRegisterDivorceSuccessfully() {
    MaritalStatus expectedMaritalStatus = MaritalStatus.DIVORCED;
    civilRegistry.registrationDivorce(firstParent, secondParent, date);
    Assertions.assertEquals(expectedMaritalStatus, firstParent.getMaritalStatus());
  }

  @DisplayName("Успешно установлен статус 'разведен/а'")
  @Test
  void shouldSetStatusSuccessfully() {
    MaritalStatus expectedMaritalStatus = MaritalStatus.DIVORCED;
    civilRegistry.registrationDivorce(firstParent, secondParent, date);
    Assertions.assertEquals(expectedMaritalStatus, firstParent.getMaritalStatus());
  }

  @DisplayName("Успешная запись типа гражданского действия 'регистрация развода'")
  @Test
  void successfullyRegistersDivorceActionRecord() {
    CivilActionType expectedCivilActionType = CivilActionType.DIVORCE_REGISTRATION;
    civilRegistry.registrationDivorce(firstParent, secondParent, date);
    Assertions.assertEquals(expectedCivilActionType, getFirstCivilActionType());
  }

  @DisplayName("Успешно выдает исключение NPE при регистрация развода")
  @Test
  void throwsNPEWhenRegisteringDivorceWithNullCitizen() {
    Assertions.assertThrows(NullPointerException.class,
            () -> civilRegistry.registrationDivorce(null, secondParent, date));
  }


  @Test
  void statisticsForDate() {
    //TODO Написать тест на то-что вернется корректная Map
    //TODO Написать тест на то-что вернется Map с ожидаемыви значениями
  }

  @DisplayName("Успешное получение статистики о количестве действий для даты")
  @Test
  void successfullyRetrievesCivilStatisticsForDate() {
    civilRegistry.registrationMarriage(firstParent, secondParent, date);
    long expectedActionCount = 1;
    Assertions.assertEquals(expectedActionCount, (Long) (long) civilRegistry.getCivilActionRecords().get(date).size());
  }

  @DisplayName("Успешное получение статистики о количестве действий для регистрации брака")
  @Test
  void successfullyRetrievesMarriageRegistrationActionCount() {
    civilRegistry.registrationMarriage(firstParent, secondParent, date);
    long expectedActionCount = 1;
    long actualActionCount = civilRegistry.getCivilActionRecords().get(date).stream()
            .filter(action -> action.getCivilActionType() == CivilActionType.WEDDING_REGISTRATION)
            .count();

    Assertions.assertEquals(expectedActionCount, actualActionCount);
  }

  private CivilActionType getFirstCivilActionType() {
    return civilRegistry.getCivilActionRecords()
            .get(date)
            .iterator()
            .next()
            .getCivilActionType();
  }
}