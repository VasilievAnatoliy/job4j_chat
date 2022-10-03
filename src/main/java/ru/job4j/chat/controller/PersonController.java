package ru.job4j.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/chat/person")
public class PersonController {
    private final PersonService persons;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class.getSimpleName());
    private final ObjectMapper objectMapper;

    public PersonController(PersonService persons, ObjectMapper objectMapper) {
        this.persons = persons;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/all")
    public List<Person> findAll() {
        return this.persons.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        return new ResponseEntity<>(
                this.persons.findById(id),
                HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity create(@RequestBody Person person) {
        String password = person.getPassword();
        if (person.getUsername() == null || password == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        if (password.length() < 3) {
            throw new IllegalArgumentException(
                    "Invalid password. Password length must be more than 3 characters");
        }
        this.persons.save(person);
        return new ResponseEntity<>(
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        this.persons.update(person);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/role")
    public ResponseEntity<Person> addRole(@PathVariable int id,
                                          @RequestBody Role role) {
        this.persons.addRole(id, role);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/role")
    public ResponseEntity<Person> deleteRole(@PathVariable int id,
                                             @RequestBody Role role) {
        this.persons.deleteRole(id, role);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public void exceptionHandler(Exception e, HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.LENGTH_REQUIRED.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }
}
