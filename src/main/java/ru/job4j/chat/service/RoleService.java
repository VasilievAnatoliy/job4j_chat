package ru.job4j.chat.service;

import ru.job4j.chat.model.Role;
import ru.job4j.chat.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roles;

    public RoleService(RoleRepository roles) {
        this.roles = roles;
    }

    public Role findByName(String role) {
        return this.roles.findByName(role);
    }
}
