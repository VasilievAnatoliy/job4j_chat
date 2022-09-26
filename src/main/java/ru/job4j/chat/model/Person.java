package ru.job4j.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

/**
 * Модель данных - Аккаунт пользователя.
 */
@Setter
@Getter
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    @ManyToMany
    @JoinTable(name = "persons_roles", joinColumns = {
                    @JoinColumn(name = "person_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id")
            })
    private Set<Role> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
