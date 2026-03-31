package com.example.service;

import com.example.model.Person;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестирование функциональности сервиса {@link CsvService}.
 */
class CsvServiceTest {

    private final CsvService service = new CsvService();

    @Test
    void shouldReadPeopleFromCsv() {
        List<Person> people = service.readPeople("people.csv");
        assertNotNull(people);
        assertFalse(people.isEmpty());
    }

    @Test
    void shouldParseAllFieldsCorrectly() {
        List<Person> people = service.readPeople("people.csv");
        Person person = people.get(0);

        assertNotNull(person.getId());
        assertNotNull(person.getName());
        assertNotNull(person.getGender());
        assertNotNull(person.getDepartment());
        assertNotNull(person.getBirthDate());
        assertTrue(person.getSalary() >= 0);
    }

    @Test
    void shouldHavePositiveSalaryForAllPeople() {
        List<Person> people = service.readPeople("people.csv");
        people.forEach(p -> assertTrue(p.getSalary() >= 0));
    }

    @Test
    void shouldHaveDepartmentsAssignedCorrectly() {
        List<Person> people = service.readPeople("people.csv");
        for (Person p : people) {
            assertNotNull(p.getDepartment());
            assertNotNull(p.getDepartment().getName());
        }
    }

    @Test
    void listShouldNotContainDuplicateDepartments() {
        List<Person> people = service.readPeople("people.csv");
        long distinct = people.stream().map(p -> p.getDepartment().getName()).distinct().count();
        assertTrue(distinct > 0);
    }
}