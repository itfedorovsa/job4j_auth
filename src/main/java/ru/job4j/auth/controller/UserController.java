package ru.job4j.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.auth.model.Person;
import ru.job4j.auth.repository.UserStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * User controller
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 20.03.23
 */
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserStore users;

    private BCryptPasswordEncoder encoder;

    private final ObjectMapper objectMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());

    @PostMapping("/sign-up")
    public ResponseEntity<Person> signUp(@RequestBody Person person) {
        String username = person.getUsername();
        String password = person.getPassword();
        if (username == null || password == null) {
            throw new NullPointerException("Username or password could not be empty");
        }
        if (username.length() < 3) {
            throw new IllegalArgumentException("Username must be longer than 3 symbols");
        }
        person.setPassword(encoder.encode(password));
        users.save(person);
        return new ResponseEntity<>(
                users.save(person),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll1() {
        List<Person> result = users.findAll();
        return !result.isEmpty()
                ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", e.getMessage());
                put("type", e.getClass());
            }
        }));
        LOGGER.error(e.getLocalizedMessage());
    }

}
