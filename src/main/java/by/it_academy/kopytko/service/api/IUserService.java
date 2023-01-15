package by.it_academy.kopytko.service.api;

import by.it_academy.kopytko.dao.entity.User;
import by.it_academy.kopytko.service.dto.*;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IUserService {

    User create(UserRegistrationDto item);

    String getToken(UserLoginDto item);

    UserDto getByMail(String mail);

    User getCurrentUser(String mail);

    UserDto activate(String code, String mail);

    User createByAdmin(UserCreateDto item);
    PageDto get(Pageable pageable);
    UserDto read(UUID uuid);
    User update(UUID id, LocalDateTime dtUpdate, UserCreateDto item);

}
