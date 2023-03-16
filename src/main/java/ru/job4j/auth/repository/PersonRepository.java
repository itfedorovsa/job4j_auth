package ru.job4j.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.auth.model.Person;

import java.util.List;

/**
 * Person Spring Data repository interface
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 16.03.23
 */
public interface PersonRepository extends CrudRepository<Person, Integer> {

    List<Person> findAll();

}