package ru.job4j.chat.controller;

import ru.job4j.chat.model.Message;
import ru.job4j.chat.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat/message")
public class MessageController {
    private final MessageService messages;

    public MessageController(MessageService messages) {
        this.messages = messages;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable int id) {
        return new ResponseEntity<>(
                this.messages.findById(id),
                HttpStatus.OK);
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
                                          @RequestBody Message message) {
        return new ResponseEntity<>(
                this.messages.save(roomId, message),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Message message) {
        this.messages.update(id, message);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.messages.delete(id);
        return ResponseEntity.ok().build();
    }

}
