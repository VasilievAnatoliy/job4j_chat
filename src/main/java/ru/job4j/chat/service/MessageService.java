package ru.job4j.chat.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.dto.MessageDTO;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.repository.MessageRepository;
import org.springframework.stereotype.Service;
import ru.job4j.chat.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.job4j.chat.dto.MessageDTO.dtoFromMessage;

@Service
public class MessageService {
    private final MessageRepository messages;
    private final PersonService persons;
    private final RoomRepository rooms;

    public MessageService(MessageRepository messages, PersonService persons,
                          RoomRepository rooms) {
        this.messages = messages;
        this.persons = persons;
        this.rooms = rooms;
    }

    public Message findById(int id) {
        return validateMessageId(id);
    }

    public MessageDTO findByIdMessageDTO(int id) {
        return dtoFromMessage(findById(id));
    }

    public List<MessageDTO> findByRoomId(int id) {
        validateRoomId(id);
        return this.messages.findByRoomId(id).stream()
                .map(message -> dtoFromMessage(message))
                .collect(Collectors.toList());
    }

    public List<MessageDTO> findByPersonId(int id) {
        validatePersonId(id);
        return this.messages.findByPersonId(id).stream()
                .map(message -> dtoFromMessage(message))
                .collect(Collectors.toList());
    }

    public Message save(int roomId, Message message) {
        validateMessage(message);
        message.setRoom(validateRoomId(roomId));
        message.setPerson(persons.findByUsername(authentication().getName()));
        return messages.save(message);
    }

    public void update(int id, Message message) {
        Person person = persons.findByUsername(authentication().getName());
        Message newMessage = findById(id);
        if (person.getId() != newMessage.getPerson().getId()) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,
                    "only author can update message");
        }
        validateMessage(message);
        newMessage.setText(message.getText());
        this.messages.save(newMessage);
    }

    public void delete(int id) {
        Person author = findById(id).getPerson();
        Person person = persons.findByUsername(authentication().getName());
        if (person.getId() == author.getId()
                || personRoles().contains("ROLE_ADMIN")) {
            Message message = new Message();
            message.setId(id);
            this.messages.delete(message);
            return;
        }
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,
                "only author or ADMIN can delete message");
    }

    public void deleteByRoomId(int id) {
        List<Message> list = new ArrayList<>();
        this.messages.findByRoomId(id).forEach(list::add);
        this.messages.deleteAll(list);
    }

    private Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private List<String> personRoles() {
        List authorities = new ArrayList<>();
        authentication().getAuthorities().stream().forEach(role ->
                authorities.add(String.valueOf(role)));
        return authorities;
    }

    private void validateMessage(Message message) {
        if (message.getText() == null) {
            throw new NullPointerException("message can't be empty");
        }
    }

    private Message validateMessageId(int id) {
        Message message = this.messages.findById(id).orElse(null);
        if (message == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");
        }
        return message;
    }

    private Room validateRoomId(int id) {
        Room roomId = this.rooms.findById(id).orElse(null);
        if (roomId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found");
        }
        return roomId;
    }

    private void validatePersonId(int id) {
        this.persons.findById(id);
    }
}

