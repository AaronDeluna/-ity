package org.javaacademy;

import org.javaacademy.entity.Citizen;
import org.javaacademy.civilregistry.CivilRegistry;

import java.time.LocalDate;

public class RunnerTest {
    public static void main(String[] args) {
        CivilRegistry civilRegistry = new CivilRegistry ("TEST_ZAGS");
        Citizen citizen1 = new Citizen("александр", "пушкин", "СЕргеевич", Gender.MALE);
        Citizen citizen2 = new Citizen("ЕЛЕНА", "петрова", "Петровна", Gender.FEMALE);
        Citizen citizen3 = new Citizen("Антонина  ", " Иванова", "НикоЛаевна", Gender.FEMALE);
        Citizen citizen4 = new Citizen("Сергей  ", " Есенин", "петрович", Gender.MALE);
        Citizen child1 = new Citizen("Антон  ", " пушкин", "александрович", Gender.MALE);
        Citizen child2 = new Citizen("Юлия  ", "Иванова", "Андреевна", Gender.FEMALE);
        Citizen child3 = new Citizen("Оксана  ", "Иванова", "Андреевна", Gender.FEMALE);

        civilRegistry.registrationMarriage(citizen1, citizen2, LocalDate.now());
        civilRegistry.registrationDivorce(citizen1, citizen2, LocalDate.now());
        civilRegistry.registrationMarriage(citizen3, citizen4, LocalDate.now());
        civilRegistry.birthOfChild(child1, citizen1, citizen2, LocalDate.now());
        civilRegistry.birthOfChild(child2, citizen3, citizen4, LocalDate.now());
        civilRegistry.birthOfChild(child3, citizen3, citizen4, LocalDate.now());

        civilRegistry.statistic(LocalDate.now());
    }
}
