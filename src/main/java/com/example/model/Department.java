package com.example.model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Сущность подразделения
 */
public class Department {

    private static final AtomicLong COUNTER = new AtomicLong(1);

    private final long id;
    private String name;

    public Department(String name) {
        this.id = COUNTER.getAndIncrement();
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}