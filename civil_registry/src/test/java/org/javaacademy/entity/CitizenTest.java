package org.javaacademy.entity;

import org.javaacademy.human.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Тестирование Гражданина")
class CitizenTest {

  Citizen child;
  Citizen firstParent;
  Citizen secondParent;

  @BeforeEach
  void setUp() {
    child = new Citizen("Иван", "Милованов", "Александрович", Gender.MALE);
    firstParent = new Citizen("Мария", "Иванова", "Константиновна", Gender.FEMALE);
    secondParent = new Citizen("Андрей", "Константин", "Константинович", Gender.MALE);
  }

  @DisplayName("Успешное создание ребенка")
  @Test
  void shouldCreateChildSuccessfully() {
    Assertions.assertEquals(child, firstParent.produceChild(
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
    child = firstParent.produceChild("Иван", "Милованов", "Александрович", Gender.MALE,
        secondParent);
    Assertions.assertEquals(secondParent, child.getFather());
  }

  @DisplayName("Успешное выбрасывание исключение при передачи родителей одного пола при рождении ребенка")
  @Test
  void shouldThrowIllegalArgumentExceptionWhenSameSexParents() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> firstParent.produceChild("Иван", "Милованов", "Александрович", Gender.MALE,
            firstParent));
  }

  @DisplayName("Успешно выбрасывает исключение при передачи имени как null")
  @Test
  void shouldThrowNullPointerExceptionWhenAnyArgumentIsNull() {
    Assertions.assertThrows(NullPointerException.class,
        () -> firstParent.produceChild(
            null,
            "Милованов",
            "Александрович",
            Gender.MALE,
            secondParent)
    );
  }
}