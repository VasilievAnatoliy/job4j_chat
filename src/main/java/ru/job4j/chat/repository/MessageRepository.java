package ru.job4j.chat.repository;

import ru.job4j.chat.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findByRoomId(int id);

    List<Message> findByPersonId(int id);
}

