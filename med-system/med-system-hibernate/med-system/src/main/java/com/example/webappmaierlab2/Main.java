package com.example.webappmaierlab2;

import java.io.*;
import java.util.Arrays;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Person {
    private String firstName;
    private String lastName;
    private String middleName;

    public Person(String firstName, String lastName, String middleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public String getFullName() {
        // Собираем полное имя, учитывая возможное наличие отчества
        if (middleName != null && !middleName.isEmpty()) {
            return firstName + " " + middleName + " " + lastName;
        } else {
            return firstName + " " + lastName;
        }
    }

    // Другие геттеры и методы класса Person
}

public class Main {
    public static void main(String[] args) {
        // Создаем список объектов Person
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("John", "Smith", ""));
        personList.add(new Person("Alice", "Johnson", "Marie"));
        personList.add(new Person("Bob", "Williams", "James"));

        // Строка, которую ввел пользователь в поисковой запрос
        String searchString = "Smith James"; // Фамилия и отчество

        // Разбиваем поисковую строку на отдельные слова
        String[] searchWords = searchString.split(" ");

        // Используем Stream API для поиска совпадений
        List<Person> searchResults = personList.stream()
                .filter(person ->
                        // Проверяем, есть ли хотя бы одно слово из поисковой строки в полном имени
                        Stream.of(searchWords)
                                .anyMatch(word -> person.getFullName().toLowerCase().contains(word.toLowerCase()))
                )
                .collect(Collectors.toList());

        // Выводим результаты поиска
        searchResults.forEach(person -> System.out.println(person.getFullName()));
    }
}
