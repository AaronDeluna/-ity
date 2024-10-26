package org.javaacademy;

import org.javaacademy.entity.Citizen;
import org.javaacademy.civilregistry.CivilRegistry;
import java.time.LocalDate;

import static org.javaacademy.human.Gender.MALE;
import static org.javaacademy.human.Gender.FEMALE;

public class RunnerProm {
    public static void main(String[] args) {
        CivilRegistry civilRegistry = new CivilRegistry(args[0]);
        Citizen citizen1 = new Citizen("александр", "пушкин", "СЕргеевич", MALE);
        Citizen citizen2 = new Citizen("ЕЛЕНА", "петрова", "Петровна", FEMALE);
        Citizen citizen3 = new Citizen("Антонина  ", " Иванова", "НикоЛаевна", FEMALE);
        Citizen citizen4 = new Citizen("Сергей  ", " Есенин", "петрович", MALE);
        Citizen child1 = new Citizen("Антон  ", " пушкин", "александрович", MALE);
        Citizen child2 = new Citizen("Юлия  ", "Иванова", "Андреевна", FEMALE);
        Citizen child3 = new Citizen("Оксана  ", "Иванова", "Андреевна", FEMALE);

        civilRegistry.registrationMarriage(citizen1, citizen2, LocalDate.now());
        civilRegistry.registrationDivorce(citizen1, citizen2, LocalDate.now());
        civilRegistry.registrationMarriage(citizen3, citizen4, LocalDate.now());
        civilRegistry.birthOfChild(child1, citizen1, citizen2, LocalDate.now());
        civilRegistry.birthOfChild(child2, citizen3, citizen4, LocalDate.now());
        civilRegistry.birthOfChild(child3, citizen3, citizen4, LocalDate.now());

        civilRegistry.statisticsForDate(LocalDate.now());
    }
}
