package ru.job4j.auth.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.job4j.auth.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User repository
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 20.03.23
 */
@Component
@AllArgsConstructor
public class UserStore {

    private final ConcurrentHashMap<String, Person> users = new ConcurrentHashMap<>();

    /**
     * Save Person
     *
     * @param person Person
     */
    public void save(Person person) {
        users.put(person.getUsername(), person);
    }

    /**
     * Find Person by username
     *
     * @param username username
     * @return Person
     */
    public Person findByUsername(String username) {
        return users.get(username);
    }

    /**
     * Find all Person
     *
     * @return List of Person
     */
    public List<Person> findAll() {
        return new ArrayList<>(users.values());
    }

}
