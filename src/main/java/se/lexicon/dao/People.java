package se.lexicon.dao;

import se.lexicon.model.Person;

import java.util.List;
import java.util.Optional;

public interface People {
    Person create(Person person);
    List<Person> findAll();
    List <Person> findByName(String name); // match first OR last name
    Optional<Person> findbById(int id);
    Person update(Person person);
    boolean deleteById(int id);
}
