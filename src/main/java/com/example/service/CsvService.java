package com.example.service;

import com.example.model.Department;
import com.example.model.Gender;
import com.example.model.Person;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParserBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;

/**
 * Сервис для чтения CSV файла и преобразования его в список сотрудников
 */
public class CsvService {

    /**
     * Читает CSV файл из resources и возвращает список сотрудников
     *
     * @param filePath путь к CSV файлу (например: "people.csv")
     * @return список сотрудников
     */
    public List<Person> readPeople(String filePath) {
        List<Person> people = new ArrayList<>();
        Map<String, Department> departments = new HashMap<>();

        InputStream in = getClass().getClassLoader().getResourceAsStream(filePath);

        if (in == null) {
            throw new RuntimeException("Файл не найден: " + filePath);
        }

        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(in))
                .withCSVParser(new CSVParserBuilder()
                        .withSeparator(';')
                        .build())
                .withSkipLines(1) // если есть заголовок
                .build()) {

            String[] line;

            while ((line = reader.readNext()) != null) {

                // Валидация строки
                if (line.length < 6) {
                    continue; // пропускаем некорректные строки
                }

                long id = parseLong(line[0]);
                String name = line[1];
                Gender gender = parseGender(line[2]);
                String departmentName = line[3];
                double salary = parseDouble(line[4]);
                LocalDate birthDate = parseDate(line[5]);

                Department department = departments.computeIfAbsent(
                        departmentName,
                        Department::new
                );

                Person person = new Person(
                        id,
                        name,
                        gender,
                        department,
                        salary,
                        birthDate
                );

                people.add(person);
            }

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при чтении CSV файла", e);
        }

        return people;
    }

    /**
     * Парсинг пола
     */
    private Gender parseGender(String value) {
        if (value == null) return Gender.MALE;

        return value.equalsIgnoreCase("male")
                ? Gender.MALE
                : Gender.FEMALE;
    }

    /**
     * Безопасный парсинг long
     */
    private long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Безопасный парсинг double
     */
    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * Парсинг даты (формат: yyyy-MM-dd)
     */
    private LocalDate parseDate(String value) {
        try {
            return LocalDate.parse(value);
        } catch (Exception e) {
            return null;
        }
    }
}