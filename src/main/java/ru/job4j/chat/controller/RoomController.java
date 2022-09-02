package ru.job4j.chat.controller;

import ru.job4j.chat.service.RoomService;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService rooms;
    private final PersonService persons;

    public RoomController(RoomService rooms, PersonService persons) {
        this.rooms = rooms;
        this.persons = persons;
    }

    @GetMapping("/")
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
    public ResponseEntity<Room> create(@RequestParam("personId") int id,
                                       @RequestBody Room room) {
        room.setPerson(persons.findById(id).get());
        return new ResponseEntity<>(
                this.rooms.save(room),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Room room) {
        this.rooms.save(room);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.rooms.delete(id);
        return ResponseEntity.ok().build();
    }
}
