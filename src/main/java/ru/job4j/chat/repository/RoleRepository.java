package ru.job4j.chat.repository;

import ru.job4j.chat.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(String role);
}

