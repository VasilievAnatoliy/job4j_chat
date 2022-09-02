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

    public RoomService(RoomRepository rooms) {
        this.rooms = rooms;
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
        Room room = new Room();
        room.setId(id);
        this.rooms.delete(room);
    }
}
