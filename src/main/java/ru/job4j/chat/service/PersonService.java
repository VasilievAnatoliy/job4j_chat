package ru.job4j.chat.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public PersonService(PersonRepository persons, RoleService roles,
                         BCryptPasswordEncoder encoder) {
        this.persons = persons;
        this.roles = roles;
        this.encoder = encoder;
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
        Person newPerson = persons.findByUsername(person.getUsername());
        if (newPerson != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("User with name: %s already exists", person.getUsername()));
        }
        person.setPassword(encoder.encode(person.getPassword()));
        person.getRoles().add(roles.findByName("ROLE_USER"));
        return this.persons.save(person);
    }


    public void update(Person person) {
        Person savedPerson = findByUsername(authentication().getName());
        if (person.getUsername() != null) {
            savedPerson.setUsername(person.getUsername());
        }
        if (person.getPassword() != null) {
            savedPerson.setPassword(person.getPassword());
        }
        save(savedPerson);
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

    private Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
