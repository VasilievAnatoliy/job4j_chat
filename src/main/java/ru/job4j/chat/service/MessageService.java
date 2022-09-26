package ru.job4j.chat.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.repository.MessageRepository;
import org.springframework.stereotype.Service;
import ru.job4j.chat.repository.PersonRepository;
import ru.job4j.chat.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messages;
    private final PersonRepository persons;
    private final RoomRepository rooms;

    public MessageService(MessageRepository messages, PersonRepository persons, RoomRepository rooms) {
        this.messages = messages;
        this.persons = persons;
        this.rooms = rooms;
    }

    public Optional<Message> findById(int id) {
        return messages.findById(id);
    }

    public List<Message> findByRoomId(int id) {
        return this.messages.findByRoomId(id);
    }

    public List<Message> findByPersonId(int id) {
        return this.messages.findByPersonId(id);
    }

    public Message save(int roomId, Message message) {
        message.setRoom(rooms.findById(roomId).get());
        message.setPerson(persons.findByUsername(authentication().getName()));
        return messages.save(message);
    }

    public void update(int id, Message message) {
        Person person = persons.findByUsername(authentication().getName());
        Message newMessage = findById(id).get();
        if (person.getId() == newMessage.getPerson().getId()) {
            newMessage.setText(message.getText());
            this.messages.save(newMessage);
        }
    }

    public void delete(int id) {
        Person author = findById(id).get().getPerson();
        Person person = persons.findByUsername(authentication().getName());
        if (person.getId() == author.getId()
                || authentication().getAuthorities().contains("ROLE_ADMIN")) {
            Message message = new Message();
            message.setId(id);
            this.messages.delete(message);
        }
    }

    public void deleteByRoomId(int id) {
        List<Message> list = new ArrayList<>();
        this.messages.findByRoomId(id).forEach(list::add);
        this.messages.deleteAll(list);
    }

    private Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
