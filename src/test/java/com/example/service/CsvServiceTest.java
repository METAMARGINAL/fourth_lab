package com.example.service;

import com.example.model.Person;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для CsvService
 */
class CsvServiceTest {

    @Test
    void shouldReadPeopleFromCsv() {
        CsvService service = new CsvService();

        List<Person> people = service.readPeople("people.csv");

        assertNotNull(people);
        assertFalse(people.isEmpty());
    }

    @Test
    void shouldParseDepartmentCorrectly() {
        CsvService service = new CsvService();

        List<Person> people = service.readPeople("people.csv");

        assertNotNull(people.get(0).getDepartment());
    }
}