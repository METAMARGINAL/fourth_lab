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
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Сервис для чтения и парсинга CSV файлов.
 * Преобразует строки CSV в объекты модели {@link Person}.
 */
public class CsvService {

    /** Форматтер для парсинга дат в формате день.месяц.год (напр. 31.12.1990) */
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Читает данные из CSV файла и формирует список сотрудников.
     * Поддерживает уникальность объектов {@link Department} в рамках одного вызова.
     *
     * @param filePath имя файла в директории ресурсов (напр. "people.csv")
     * @return список объектов {@link Person}
     * @throws RuntimeException если файл не найден или произошла ошибка чтения
     */
    public List<Person> readPeople(String filePath) {
        List<Person> people = new ArrayList<>();
        Map<String, Department> departments = new HashMap<>();

        InputStream in = getClass().getClassLoader().getResourceAsStream(filePath);
        if (in == null) throw new RuntimeException("Файл не найден: " + filePath);

        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(in))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .withSkipLines(1)
                .build()) {

            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line.length < 6) continue;

                long id = parseLong(line[0]);
                String name = line[1];
                Gender gender = parseGender(line[2]);
                LocalDate birthDate = parseDate(line[3]);
                String departmentName = line[4];
                double salary = parseDouble(line[5]);

                Department department = departments.computeIfAbsent(departmentName, Department::new);
                people.add(new Person(id, name, gender, department, salary, birthDate));
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при чтении CSV", e);
        }
        return people;
    }

    /**
     * Преобразует строковое значение в {@link Gender}.
     *
     * @param value строка (напр. "Male" или "Female")
     * @return соответствующий элемент enum или MALE по умолчанию
     */
    private Gender parseGender(String value) {
        return (value != null && value.equalsIgnoreCase("female")) ? Gender.FEMALE : Gender.MALE;
    }

    /**
     * Безопасно парсит строку в long.
     *
     * @param value строка с числом
     * @return число или 0 в случае ошибки
     */
    private long parseLong(String value) {
        try { return Long.parseLong(value.trim()); } catch (Exception e) { return 0; }
    }

    /**
     * Безопасно парсит строку в double.
     *
     * @param value строка с числом
     * @return число или 0.0 в случае ошибки
     */
    private double parseDouble(String value) {
        try { return Double.parseDouble(value.trim()); } catch (Exception e) { return 0.0; }
    }

    /**
     * Парсит строку в дату с использованием заданного форматтера.
     *
     * @param value строка с датой
     * @return объект {@link LocalDate} или null в случае ошибки
     */
    private LocalDate parseDate(String value) {
        try { return LocalDate.parse(value.trim(), formatter); } catch (Exception e) { return null; }
    }
}