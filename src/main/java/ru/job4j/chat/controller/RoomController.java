package ru.job4j.chat.controller;

import org.springframework.util.MultiValueMapAdapter;
import ru.job4j.chat.dto.RoomDTO;
import ru.job4j.chat.service.RoomService;
import ru.job4j.chat.model.Room;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat/room")
public class RoomController {
    private final RoomService rooms;

    public RoomController(RoomService rooms) {
        this.rooms = rooms;
    }

    @GetMapping("/all")
    public List<RoomDTO> findAll() {
        return this.rooms.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> findById(@PathVariable int id) {
        return new ResponseEntity<>(
                this.rooms.findByIdRoomDTO(id),
                HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Room> create(@RequestBody Room room) {
        return new ResponseEntity<>(
                this.rooms.save(room),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        String room = this.rooms.findById(id).getName();
        this.rooms.delete(id);
        var entity = new ResponseEntity(
                new HashMap<>() { { put("Room deleted ", room); } },
                new MultiValueMapAdapter<>(Map.of("Room deleted", List.of(room))),
                HttpStatus.OK
        );
        return entity;
    }
}
