package ru.job4j.chat.controller;

import ru.job4j.chat.model.Message;
import ru.job4j.chat.service.MessageService;
import ru.job4j.chat.service.PersonService;
import ru.job4j.chat.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    private final MessageService messages;
    private final PersonService persons;
    private final RoomService rooms;


    public MessageController(MessageService messages, PersonService persons, RoomService rooms) {
        this.messages = messages;
        this.persons = persons;
        this.rooms = rooms;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable int id) {
        var message = this.messages.findById(id);
        return new ResponseEntity<Message>(
                message.orElse(new Message()),
                message.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/room/{roomId}")
    public List<Message> findByRoomId(@PathVariable int roomId) {
        return this.messages.findByRoomId(roomId);
    }

    @GetMapping("/person/{personId}")
    public List<Message> findByPersonId(@PathVariable int personId) {
        return this.messages.findByPersonId(personId);
    }

    @PostMapping("/room/{roomId}")
    public ResponseEntity<Message> create(@PathVariable int roomId,
                                          @RequestParam("personId") int id,
                                          @RequestBody Message message) {
        message.setRoom(rooms.findById(roomId).get());
        message.setPerson(persons.findById(id).get());
        return new ResponseEntity<>(
                this.messages.save(message),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Message message) {
        this.messages.update(message);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.messages.delete(id);
        return ResponseEntity.ok().build();
    }
}
