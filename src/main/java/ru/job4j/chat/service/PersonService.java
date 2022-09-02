package ru.job4j.chat.service;

import ru.job4j.chat.model.Person;
import ru.job4j.chat.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository persons;
    private final RoleService roles;

    public PersonService(PersonRepository persons, RoleService roles) {
        this.persons = persons;
        this.roles = roles;
    }

    public List<Person> findAll() {
        return this.persons.findAll();
    }

    public Optional<Person> findById(int id) {
        return this.persons.findById(id);
    }

    public Person save(Person person) {
        person.setRole(roles.findByName("ROLE_USER"));
        return this.persons.save(person);
    }

    public void delete(int id) {
        Person person = new Person();
        person.setId(id);
        this.persons.delete(person);
    }
}
