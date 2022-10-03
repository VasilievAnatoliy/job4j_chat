package ru.job4j.chat.dto;

import lombok.Data;
import ru.job4j.chat.model.Person;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class PersonDTO {
    private int id;
    private String username;
    private Set<String> roles = new HashSet<>();

    public static PersonDTO dtoFromPerson(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setUsername(person.getUsername());
        dto.setRoles(person.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet()));
        return dto;
    }
}
