package ru.job4j.chat.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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

    public Room findById(int id) {
        return validateRoomId(id);
    }

    public Room save(Room room) {
        if (room.getName() == null) {
            throw new NullPointerException("Room name mustn't be empty");
        }
        room.setPerson(persons.findByUsername(authentication().getName()));
        return this.rooms.save(room);
    }

    public void delete(int id) {
            validateRoomId(id);
            this.messages.deleteByRoomId(id);
            Room room = new Room();
            room.setId(id);
            this.rooms.delete(room);
    }

    private Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private Room validateRoomId(int id) {
        Room roomId = this.rooms.findById(id).orElse(null);
        if (roomId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found");
        }
        return roomId;
    }
}
