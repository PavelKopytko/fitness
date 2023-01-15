package by.it_academy.kopytko.util.mappers;

import by.it_academy.kopytko.dao.entity.*;
import by.it_academy.kopytko.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserMapper {

    public UserMapper() {
    }

    public User dtoToEntityMapper(UserRegistrationDto item, PasswordEncoder passwordEncoder) {

        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        UUID id = UUID.randomUUID();

        User user = new User();
        user.setUuid(id);
        user.setDtCreate(time);
        user.setDtUpdate(time);
        user.setNick(item.getNick());
        user.setMail(item.getMail());
        user.setPassword(passwordEncoder.encode(item.getPassword()));
        user.setRole(ERole.ROLE_USER);
        user.setUserStatus(EUserStatus.WAITING_ACTIVATION);

        return user;
    }

    public User dtoToEntityMapper(UserCreateDto item, PasswordEncoder passwordEncoder) {

        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        UUID id = UUID.randomUUID();

        User user = new User();
        user.setUuid(id);
        user.setDtCreate(time);
        user.setDtUpdate(time);
        user.setNick(item.getNick());
        user.setMail(item.getMail());
        user.setPassword(passwordEncoder.encode(item.getPassword()));
        user.setRole(item.getRole());
        user.setUserStatus(item.getStatus());

        return user;
    }

    public UserDto entityToDtoMapper(User readed) {
        UserDto user = new UserDto();
        user.setUuid(readed.getUuid());
        user.setDtCreate(readed.getDtCreate());
        user.setDtUpdate(readed.getDtUpdate());
        user.setNick(readed.getNick());
        user.setMail(readed.getMail());
        user.setRole(readed.getRole());
        user.setStatus(readed.getUserStatus());
        return user;
    }

    public User updateUserMapper(UserCreateDto item, User readed, PasswordEncoder passwordEncoder) {

        readed.setNick(item.getNick());
        readed.setMail(item.getMail());
        readed.setRole(item.getRole());
        readed.setUserStatus(item.getStatus());
        readed.setPassword(passwordEncoder.encode(item.getPassword()));

        return readed;
    }

    public PageDto<UserDto> mapper(Page<User> items) {

        PageDto<UserDto> dto = new PageDto<>();
        dto.setNumber(items.getNumber());
        dto.setSize(items.getSize());
        dto.setTotalPages(items.getTotalPages());
        dto.setTotalElements((int) items.getTotalElements());
        dto.setFirst(items.isFirst());
        dto.setNumberOfElements(items.getNumberOfElements());
        dto.setLast(items.isLast());

        List<User> users = items.getContent();
        List<UserDto> userOuts = new ArrayList<>();

        for (User user : users) {
            UserDto out = new UserDto();
            out.setUuid(user.getUuid());
            out.setDtCreate(user.getDtCreate());
            out.setDtUpdate(user.getDtUpdate());
            out.setNick(user.getNick());
            out.setMail(user.getMail());
            out.setRole(user.getRole());
            out.setStatus(user.getUserStatus());
            userOuts.add(out);
        }
        dto.setContent(userOuts);

        return dto;
    }

}
