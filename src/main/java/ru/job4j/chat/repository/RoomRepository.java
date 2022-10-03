package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.chat.model.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
