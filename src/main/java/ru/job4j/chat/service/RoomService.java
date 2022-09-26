package ru.job4j.chat.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository rooms;
    private final MessageService messages;
    private final PersonService persons;

    public RoomService(RoomRepository rooms, MessageService messages, PersonService persons) {
        this.rooms = rooms;
        this.messages = messages;
        this.persons = persons;
    }

    public List<Room> findAll() {
        List<Room> list = new ArrayList<>();
        this.rooms.findAll().forEach(list::add);
        return list;
    }

    public Optional<Room> findById(int id) {
        return this.rooms.findById(id);
    }

    public Room save(Room room) {
        room.setPerson(persons.findByUsername(authentication().getName()));
        return this.rooms.save(room);
    }

    public void delete(int id) {
        if (authentication().getAuthorities().contains("ROLE_ADMIN")) {
            this.messages.deleteByRoomId(id);
            Room room = new Room();
            room.setId(id);
            this.rooms.delete(room);
        }
    }

    private Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
