package ru.job4j.chat.service;

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

    public RoomService(RoomRepository rooms, MessageService messages) {
        this.rooms = rooms;
        this.messages = messages;
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
        return this.rooms.save(room);
    }

    public void delete(int id) {
        this.messages.deleteByRoomId(id);
        Room room = new Room();
        room.setId(id);
        this.rooms.delete(room);
    }
}
