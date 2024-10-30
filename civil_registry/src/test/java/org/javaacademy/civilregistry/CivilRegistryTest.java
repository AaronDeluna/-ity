package org.javaacademy.civilregistry;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import org.javaacademy.entity.Citizen;
import org.javaacademy.entity.MaritalStatus;
import org.javaacademy.human.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


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
    Assertions.assertEquals(expectedCivilActionType,
        civilRegistry.getCivilActionRecordsOnDate(date).stream()
            .findFirst()
            .get()
            .getCivilActionType());
  }

  @DisplayName("Успешная запись типа гражданского действия регистрация брака")
  @Test
  void shouldRecordMarriageCivilActionSuccessfully() {
    CivilActionType expectedCivilActionType = CivilActionType.WEDDING_REGISTRATION;
    civilRegistry.registrationMarriage(firstParent, secondParent, date);
    Assertions.assertEquals(expectedCivilActionType,
        civilRegistry.getCivilActionRecordsOnDate(date).stream()
            .findFirst()
            .get()
            .getCivilActionType());
  }

  @DisplayName("Успешно установлен статус в браке")
  @Test
  void marriageStatusSuccessfullyEstablished() {
    MaritalStatus expectedMaritalStatus = MaritalStatus.MARRIED;
    civilRegistry.registrationMarriage(firstParent, secondParent, date);
    Assertions.assertEquals(expectedMaritalStatus, firstParent.getMaritalStatus());
    Assertions.assertEquals(expectedMaritalStatus, secondParent.getMaritalStatus());
  }

  @DisplayName("Успешная запись типа гражданского действия регистрация развода")
  @Test
  void successfullyRegistersDivorceActionRecord() {
    CivilActionType expectedCivilActionType = CivilActionType.DIVORCE_REGISTRATION;
    civilRegistry.registrationDivorce(firstParent, secondParent, date);
    Assertions.assertEquals(expectedCivilActionType,
        civilRegistry.getCivilActionRecordsOnDate(date).stream()
            .findFirst()
            .get()
            .getCivilActionType());
  }

  @DisplayName("Успешная установлен статус в разводе")
  @Test
  void shouldRegisterDivorceSuccessfully() {
    MaritalStatus expectedMaritalStatus = MaritalStatus.DIVORCED;
    civilRegistry.registrationDivorce(firstParent, secondParent, date);
    Assertions.assertEquals(expectedMaritalStatus, firstParent.getMaritalStatus());
    Assertions.assertEquals(expectedMaritalStatus, secondParent.getMaritalStatus());
  }

  @DisplayName("Список гражданских действий на дату "
      + "на которую не было регистраций актов гражданского действия не null")
  @Test
  void shouldNotBeNullCivilActionRecords() {
    Assertions.assertNotNull(civilRegistry.getCivilActionRecordsOnDate(date));
  }

  @DisplayName("Успешное получение статистики о количестве записей для даты")
  @Test
  void successfullyRetrievesCivilStatisticsForDate() {
    civilRegistry.registrationMarriage(firstParent, secondParent, date);
    civilRegistry.registrationMarriage(firstParent, secondParent, date);
    long expectedActionCount = 2;
    Assertions.assertEquals(expectedActionCount,
        civilRegistry.getCivilActionRecordsOnDate(date).size());
  }

  @DisplayName("Корректно выводит 0 для всех типов действий, если нет записей на эту дату")
  @Test
  void shouldPrintZeroForAllActionsWhenNoRecordsOnDate() {
    String output = captureConsoleOutput(
        () -> civilRegistry.statisticsForDate(LocalDate.of(2013, 3, 10)));

    Assertions.assertTrue(output.contains("количество свадеб - 0"));
    Assertions.assertTrue(output.contains("количество разводов - 0"));
    Assertions.assertTrue(output.contains("количество рождений - 0"));
  }

  private String captureConsoleOutput(Runnable runnable) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));
    try {
      runnable.run();
    } finally {
      System.setOut(originalOut);
    }
    return outputStream.toString();
  }
}