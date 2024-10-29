package org.javaacademy.entity;

import org.javaacademy.human.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование Гражданина")
class CitizenTest {

    Citizen child;
    Citizen secondParent;

    @BeforeEach
    void setUp() {
        child = new Citizen("Иван", "Милованов", "Александрович", Gender.MALE);
        secondParent = new Citizen("Андрей", "Константин", "Константинович", Gender.MALE);
    }

    @DisplayName("Успешное создание ребенка")
    @Test
    void shouldCreateChildSuccessfully() {
        Assertions.assertEquals(child, child.produceChild(
                "Иван",
                "Милованов",
                "Александрович",
                Gender.MALE,
                secondParent)
        );
    }

    @DisplayName("Успешное добавление родителя для ребенка")
    @Test
    void shouldAssignParentsToChildOnBirth() {
        child = child.produceChild("Иван", "Милованов", "Александрович", Gender.MALE, secondParent);
        Assertions.assertEquals(secondParent, child.getMother());
    }

    @DisplayName("Успешно выбрасывает исключенеи при передачи имени как null")
    @Test
    void shouldThrowNullPointerExceptionWhenAnyArgumentIsNull() {
        Assertions.assertThrows(NullPointerException.class,
                () -> secondParent.produceChild(
                        null,
                        "Милованов",
                        "Александрович",
                        Gender.MALE,
                        secondParent)
        );
    }
}