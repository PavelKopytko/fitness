package by.it_academy.kopytko.service.dto;

import by.it_academy.kopytko.dao.entity.EEssenceType;

import java.util.UUID;

public class AuditDto {

    private UUID uuid;
    private UserDto user;
    private String text;
    private EEssenceType type;
    private String id;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EEssenceType getType() {
        return type;
    }

    public void setType(EEssenceType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
