package com.example;

import com.example.model.Person;
import com.example.service.CsvService;

import java.util.List;

/**
 * Точка входа в приложение
 */
public class Main {

    public static void main(String[] args) {

        CsvService service = new CsvService();

        List<Person> people = service.readPeople("people.csv");

        for (Person person : people) {
            System.out.println(
                    person.getId() + " | " +
                            person.getName() + " | " +
                            person.getGender() + " | " +
                            person.getDepartment().getName() + " | " +
                            person.getSalary() + " | " +
                            person.getBirthDate()
            );
        }
    }
}