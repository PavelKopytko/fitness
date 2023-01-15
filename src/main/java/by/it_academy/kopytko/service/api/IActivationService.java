package by.it_academy.kopytko.service.api;

import by.it_academy.kopytko.dao.entity.User;
import by.it_academy.kopytko.service.dto.*;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IActivationService {

    User registrate(UserRegistrationDto item);

    void sendMessage(User user);

}
