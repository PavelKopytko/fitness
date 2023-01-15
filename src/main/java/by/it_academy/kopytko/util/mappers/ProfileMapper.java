package by.it_academy.kopytko.util.mappers;

import by.it_academy.kopytko.dao.entity.ERole;
import by.it_academy.kopytko.dao.entity.EUserStatus;
import by.it_academy.kopytko.dao.entity.Profile;
import by.it_academy.kopytko.dao.entity.User;
import by.it_academy.kopytko.service.api.IUserService;
import by.it_academy.kopytko.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ProfileMapper {

    public ProfileMapper() {
    }

    public Profile dtoToEntityMapper(ProfileForCU item, User user) {

        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        UUID id = UUID.randomUUID();


        Profile profile = new Profile();
        profile.setUuid(id);
        profile.setDtCreate(time);
        profile.setDtUpdate(time);
        profile.setHeight(item.getHeight());
        profile.setWeight(item.getWeight());
        profile.setDtBirthday(item.getDtBirthday());
        profile.setTarget(item.getTarget());
        profile.setActivityType(item.getActivityType());
        profile.setSex(item.getSex());
        profile.setUser(user);

        return profile;
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

    public ProfileForOut entityToDtoMapper(Profile readed) {

        ProfileForOut profile = new ProfileForOut();
        profile.setUuid(readed.getUuid());
        profile.setDtCreate(readed.getDtCreate());
        profile.setDtUpdate(readed.getDtUpdate());
        profile.setHeight(readed.getHeight());
        profile.setWeight(readed.getWeight());
        profile.setDtBirthday(readed.getDtBirthday());
        profile.setTarget(readed.getTarget());
        profile.setActivityType(readed.getActivityType());
        profile.setSex(readed.getSex());
        profile.setUser(
                new UserDtoForProfile(
                        readed.getUser().getUuid(),
                        readed.getUser().getDtCreate(),
                        readed.getUser().getDtUpdate()
                ));
        return profile;
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
