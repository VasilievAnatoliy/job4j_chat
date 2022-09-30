package ru.job4j.chat.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
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
        return validateName(role);
    }

    private Role validateName(String roleName) {
        Role role = this.roles.findByName(roleName);
        if (role == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found");
        }
        return role;
    }
}
