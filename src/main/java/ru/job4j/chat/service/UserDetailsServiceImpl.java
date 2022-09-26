package ru.job4j.chat.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.repository.PersonRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PersonRepository users;

    public UserDetailsServiceImpl(PersonRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person user = users.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getUsername(), user.getPassword(), roleToAuthorities(user));

    }

    private Collection<SimpleGrantedAuthority> roleToAuthorities(Person user) {
       Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getName()
                )));
        return authorities;
    }
}
