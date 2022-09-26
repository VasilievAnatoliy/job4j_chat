package ru.job4j.chat.controller;

import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat/person")
public class PersonController {
    private final PersonService persons;

    public PersonController(PersonService persons) {
        this.persons = persons;
    }

    @GetMapping("/all")
    public List<Person> findAll() {
        return this.persons.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = this.persons.findById(id);
        return new ResponseEntity<>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return new ResponseEntity<>(
                this.persons.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        this.persons.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.persons.delete(id);
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
}
