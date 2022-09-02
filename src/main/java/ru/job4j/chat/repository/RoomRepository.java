package ru.job4j.chat.repository;

import ru.job4j.chat.model.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Integer> {
}
