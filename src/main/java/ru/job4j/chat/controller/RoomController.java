package ru.job4j.chat.controller;

import ru.job4j.chat.service.RoomService;
import ru.job4j.chat.model.Room;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat/room")
public class RoomController {
    private final RoomService rooms;

    public RoomController(RoomService rooms) {
        this.rooms = rooms;
    }

    @GetMapping("/all")
    public List<Room> findAll() {
        return this.rooms.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable int id) {
        var person = this.rooms.findById(id);
        return new ResponseEntity<>(
                person.orElse(new Room()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
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
        this.rooms.delete(id);
        return ResponseEntity.ok().build();
    }
}
