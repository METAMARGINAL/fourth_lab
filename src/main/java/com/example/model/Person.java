package com.example.model;

import java.time.LocalDate;

/**
 * Сущность сотрудника
 */
public class Person {

    private long id;
    private String name;
    private Gender gender;
    private Department department;
    private double salary;
    private LocalDate birthDate;

    public Person(long id, String name, Gender gender,
                  Department department, double salary, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.department = department;
        this.salary = salary;
        this.birthDate = birthDate;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public Gender getGender() { return gender; }
    public Department getDepartment() { return department; }
    public double getSalary() { return salary; }
    public LocalDate getBirthDate() { return birthDate; }
}