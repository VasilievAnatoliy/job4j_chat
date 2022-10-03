package ru.job4j.chat.dto;

import lombok.Data;
import ru.job4j.chat.model.Room;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class RoomDTO {
    private int id;
    private String name;
    private int usernameId;
    private String username;
    private Set<String> userRoles = new HashSet<>();

    public static RoomDTO dtoFromRoom(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setUsernameId(room.getPerson().getId());
        dto.setUsername(room.getPerson().getUsername());
        dto.setUserRoles(room.getPerson().getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet()));
        return dto;
    }
}
