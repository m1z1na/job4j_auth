package ru.job4j.service;

import ru.job4j.domain.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonService {
    List<Person> findAll();
    Optional<Person> findById(int id);
    Person create(Person person);
    Person update(Person person);
    void delete(int id);
}
