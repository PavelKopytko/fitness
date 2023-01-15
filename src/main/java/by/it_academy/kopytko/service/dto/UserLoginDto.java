package by.it_academy.kopytko.service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserLoginDto {
    @Email
    private String mail;
    @NotEmpty(message = "Не введен пароль")
    private String password;

    public UserLoginDto() {
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
}
