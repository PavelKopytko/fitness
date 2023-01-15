package by.it_academy.kopytko.service.api;

import by.it_academy.kopytko.dao.entity.User;
import by.it_academy.kopytko.service.dto.*;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IAdminService {

    User create(UserCreateDto item);
    PageDto get(Pageable pageable);
    UserDto read(UUID uuid);
    User update(UUID id, LocalDateTime dtUpdate, UserCreateDto item);

}
