package org.javaacademy.civilregistry;

import org.javaacademy.entity.Citizen;
import org.javaacademy.entity.CivilActionRecord;
import org.javaacademy.entity.MaritalStatus;
import org.javaacademy.human.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @DisplayName("Успешное получение статистики о количестве действий для даты")
    @Test
    void successfullyRetrievesCivilStatisticsForDate() {
        civilRegistry.registrationMarriage(firstParent, secondParent, date);
        civilRegistry.registrationMarriage(firstParent, secondParent, date);
        long expectedActionCount = 2;
        Assertions.assertEquals(expectedActionCount,
                (Long) (long) civilRegistry.getCivilActionRecords().get(date).size());
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

    @DisplayName("Успешно показывает отсутствие статисики на дату которой нет")
    @Test
    void shouldPrintZeroStatisticsForEmptyDateRecords() {
        boolean actualActionCount = civilRegistry.getCivilActionRecords().containsKey(date);
        Assertions.assertFalse(actualActionCount);
    }

    @DisplayName("Успешно вернет null если такой даты нет")
    @Test
    void shouldThrowNullPointerExceptionWhenDateNotFound() {
        Assertions.assertNull(civilRegistry.getCivilActionRecords().get(date));
    }

    @DisplayName("Корректно выводит 0 для всех типов действий, если нет записей")
    @Test
    void shouldPrintZeroForAllActionsWhenNoRecordsOnDate() {
        LocalDate date = LocalDate.of(2024, 10, 29);
        civilRegistry.getCivilActionRecords().put(date, new ArrayList<>());
        String output = captureConsoleOutput(() -> civilRegistry.statisticsForDate(date));

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

    private CivilActionType getFirstCivilActionType() {
        return civilRegistry.getCivilActionRecords()
                .get(date)
                .iterator()
                .next()
                .getCivilActionType();
    }
}