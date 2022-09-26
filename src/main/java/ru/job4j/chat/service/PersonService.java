package ru.job4j.chat.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository persons;
    private final RoleService roles;
    private BCryptPasswordEncoder encoder;

    public PersonService(PersonRepository persons, RoleService roles, BCryptPasswordEncoder encoder) {
        this.persons = persons;
        this.roles = roles;
        this.encoder = encoder;
    }

    public List<Person> findAll() {
        return this.persons.findAll();
    }

    public Optional<Person> findById(int id) {
        return this.persons.findById(id);
    }

    public Person save(Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        person.getRoles().add(roles.findByName("ROLE_USER"));
        return this.persons.save(person);
    }

    public void delete(int id) {
        Person person = new Person();
        person.setId(id);
        this.persons.delete(person);
    }

    public Person findByUsername(String username) {
        return this.persons.findByUsername(username);
    }

    public void addRole(int id, Role role) {
        Person person = findById(id).get();
        person.getRoles().add(roles.findByName(role.getName()));
        this.persons.save(person);
    }

    public void deleteRole(int id, Role role) {
        Person person = findById(id).get();
        person.getRoles().remove(roles.findByName(role.getName()));
        this.persons.save(person);
    }
}
