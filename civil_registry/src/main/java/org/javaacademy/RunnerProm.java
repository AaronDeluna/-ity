package org.javaacademy;

import static org.javaacademy.human.Gender.FEMALE;
import static org.javaacademy.human.Gender.MALE;

import java.time.LocalDate;
import org.javaacademy.civilregistry.CivilRegistry;
import org.javaacademy.entity.Citizen;

public class RunnerProm {

  public static void main(String[] args) {
    CivilRegistry civilRegistry = new CivilRegistry("TEST_ZAGS");
    Citizen citizen1 = new Citizen("александр", "пушкин", "СЕргеевич", MALE);
    Citizen citizen2 = new Citizen("ЕЛЕНА", "петрова", "Петровна", FEMALE);
    Citizen citizen3 = new Citizen("Антонина  ", " Иванова", "НикоЛаевна", FEMALE);
    Citizen citizen4 = new Citizen("Сергей  ", " Есенин", "петрович", MALE);
    Citizen child1 = citizen1.produceChild("Антон  ",
        " пушкин", "александрович", MALE, citizen2);
    Citizen child2 = citizen4.produceChild("Юлия  ", "Иванова",
        "Андреевна", FEMALE, citizen3);
    Citizen child3 = citizen1.produceChild("Оксана  ", "Иванова",
        "Андреевна", FEMALE, citizen2);

    civilRegistry.registrationMarriage(citizen1, citizen2, LocalDate.now());
    civilRegistry.registrationDivorce(citizen1, citizen2, LocalDate.now());
    civilRegistry.registrationMarriage(citizen3, citizen4, LocalDate.now());
//    civilRegistry.registrationMarriage(citizen3, citizen4, LocalDate.now());
//    civilRegistry.registrationDivorce(citizen3, citizen4, LocalDate.now());
    civilRegistry.registrationDivorce(citizen3, citizen4, LocalDate.now());
    civilRegistry.birthOfChild(child1, citizen1, citizen2, LocalDate.now());
    civilRegistry.birthOfChild(child3, citizen1, citizen2, LocalDate.now());

    civilRegistry.birthOfChild(child2, citizen3, citizen4, LocalDate.now());
//    civilRegistry.birthOfChild(child2, citizen3, citizen1, LocalDate.now());

    civilRegistry.statisticsForDate(LocalDate.now());
  }
}
