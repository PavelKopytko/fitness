package by.it_academy.kopytko.service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

public class UserRegistrationDto {
    @Email
    private String mail;
    @NotEmpty(message = "Не введен nick")
    private String nick;
    @NotEmpty(message = "Не введен пароль")
    private String password;

    public UserRegistrationDto() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
