package by.it_academy.kopytko.service;

import by.it_academy.kopytko.dao.entity.User;
import by.it_academy.kopytko.service.api.IActivationService;
import by.it_academy.kopytko.service.api.IUserService;
import by.it_academy.kopytko.service.dto.UserRegistrationDto;
import org.springframework.stereotype.Service;

@Service
public class ActivationService implements IActivationService {

    private final IUserService userService;
    private final MailSender mailSender;

    public ActivationService(IUserService userService, MailSender mailSender) {
        this.userService = userService;
        this.mailSender = mailSender;
    }

    @Override
    public User registrate(UserRegistrationDto item) {
        User user = this.userService.create(item);
        sendMessage(user);
        return user;
    }

    @Override
    public void sendMessage(User user) {
        String message =
                "Пожалуйста, для активации передите по ссылке " +
                        "http://localhost:8080/api/v1/users/activate/" + user.getUuid() +
                        "/userMail/" + user.getMail();
        this.mailSender.send(user.getMail(), "Activation code", message);
    }
}
