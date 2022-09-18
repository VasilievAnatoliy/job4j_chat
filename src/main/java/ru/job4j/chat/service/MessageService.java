package ru.job4j.chat.service;

import ru.job4j.chat.model.Message;
import ru.job4j.chat.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messages;

    public MessageService(MessageRepository messages) {
        this.messages = messages;
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

    public Message save(Message message) {
        return messages.save(message);
    }

    public void update(Message message) {
        Message newMessage = findById(message.getId()).get();
        newMessage.setText(message.getText());
        this.messages.save(newMessage);
    }

    public void delete(int id) {
        Message message = new Message();
        message.setId(id);
        this.messages.delete(message);
    }

    public void deleteByRoomId(int id) {
        List<Message> list = new ArrayList<>();
        this.messages.findByRoomId(id).forEach(list::add);
        this.messages.deleteAll(list);
    }
}
