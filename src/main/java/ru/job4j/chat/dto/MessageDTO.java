package ru.job4j.chat.dto;

import lombok.Data;
import ru.job4j.chat.model.Message;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class MessageDTO {
    private int id;
    private String text;
    private LocalDateTime created;
    private int usernameId;
    private String username;
    private Set<String> userRoles = new HashSet<>();
    private int roomId;
    private String roomName;
    private String roomAuthor;
    private int roomAuthorId;

    public static MessageDTO dtoFromMessage(Message message) {
        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setText(message.getText());
        dto.setCreated(message.getCreated());
        dto.setUsernameId(message.getPerson().getId());
        dto.setUsername(message.getPerson().getUsername());
        dto.setUserRoles(message.getPerson().getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet()));
        dto.setRoomId(message.getRoom().getId());
        dto.setRoomName(message.getRoom().getName());
        dto.setRoomAuthor(message.getRoom().getPerson().getUsername());
        dto.setRoomAuthorId(message.getRoom().getPerson().getId());
        return dto;
    }
}
