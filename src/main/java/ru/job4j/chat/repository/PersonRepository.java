package ru.job4j.chat.repository;

import ru.job4j.chat.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
