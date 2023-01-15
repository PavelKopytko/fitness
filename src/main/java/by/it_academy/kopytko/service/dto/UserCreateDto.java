package by.it_academy.kopytko.service.dto;

import by.it_academy.kopytko.dao.entity.ERole;
import by.it_academy.kopytko.dao.entity.EUserStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserCreateDto {

    @NotEmpty(message = "Не введен nick")
    private String nick;
    @Email
    private String mail;
    @NotEmpty(message = "Не введен пароль")
    private String password;

    private ERole role;
    private EUserStatus status;

    public UserCreateDto() {
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public EUserStatus getStatus() {
        return status;
    }

    public void setStatus(EUserStatus status) {
        this.status = status;
    }
}
