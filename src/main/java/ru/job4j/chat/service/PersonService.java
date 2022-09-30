package ru.job4j.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository persons;
    private final RoleService roles;
    private BCryptPasswordEncoder encoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class.getSimpleName());
    private final ObjectMapper objectMapper;


    public PersonService(PersonRepository persons, RoleService roles,
                         BCryptPasswordEncoder encoder, ObjectMapper objectMapper) {
        this.persons = persons;
        this.roles = roles;
        this.encoder = encoder;
        this.objectMapper = objectMapper;
    }

    public List<Person> findAll() {
        return this.persons.findAll();
    }

    public Person findById(int id) {
        Person person = this.persons.findById(id).orElse(null);
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found");
        }
        return person;
    }

    public Person save(Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        person.getRoles().add(roles.findByName("ROLE_USER"));
        return this.persons.save(person);
    }

    public Person findByUsername(String username) {
        Person person = this.persons.findByUsername(username);
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found");
        }
        return person;
    }

    public void addRole(int id, Role role) {
        Person person = findById(id);
        person.getRoles().add(roles.findByName(role.getName()));
        this.persons.save(person);
    }

    public void deleteRole(int id, Role role) {
        Person person = findById(id);
        person.getRoles().remove(roles.findByName(role.getName()));
        this.persons.save(person);
    }
}
