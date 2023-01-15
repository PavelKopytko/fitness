package by.it_academy.kopytko.service;

import by.it_academy.kopytko.util.JwtTokenUtil;
import by.it_academy.kopytko.dao.IUserDao;
import by.it_academy.kopytko.dao.entity.*;
import by.it_academy.kopytko.service.api.IAuditService;
import by.it_academy.kopytko.service.api.IUserService;
import by.it_academy.kopytko.service.dto.*;
import by.it_academy.kopytko.util.mappers.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserService implements IUserService {
    private final static String ACTIVATED = "Активирован пользователь через почту";
    private final static String CREATED = "Создан пользователь";
    private final static String CREATED_BY_ADMIN = "Создан пользователь админом";
    private final static String UPDATED_BY_ADMIN = "Создан пользователь админом";
    private final static String EDITED = "Пользователь уже отредактирован кем-то другим";
    private final IUserDao dao;
    private final IAuditService auditService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil tokenUtil;

    public UserService(IUserDao dao, IAuditService auditService, UserMapper userMapper, PasswordEncoder passwordEncoder, JwtTokenUtil tokenUtil) {
        this.dao = dao;
        this.auditService = auditService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = tokenUtil;
    }

    /////////////////////////////////////
    //USER SERVICE

    @Override
    @Transactional
    public User create(UserRegistrationDto item) {

        validate(item);

        User user = this.userMapper.dtoToEntityMapper(item, passwordEncoder);
        User readed = this.dao.save(user);

        this.auditService.create(
                new Audit(CREATED, EEssenceType.USER, readed.getUuid().toString()),
                user);

        return readed;
    }

    @Override
    public String getToken(UserLoginDto item) {

        User readed = this.dao.findUserByMail(item.getMail());

        if (readed == null) {
            throw new IllegalArgumentException("Введен неверный логин или пароль");
        }

        if (!passwordEncoder.matches(item.getPassword(), readed.getPassword())) {
            throw new IllegalArgumentException("Введен неверный логин или пароль");
        }

        if (readed.getUserStatus().equals(EUserStatus.DEACTIVATED) ||
                readed.getUserStatus().equals(EUserStatus.WAITING_ACTIVATION)) {
            throw new IllegalStateException("Ваш профиль не активирован");
        }

        return tokenUtil.generateAccessToken(readed.getMail());
    }

    @Override
    public UserDto getByMail(String mail) {

        User readed = getCurrentUser(mail);
        return this.userMapper.entityToDtoMapper(readed);
    }

    @Override
    public User getCurrentUser(String mail) {
        return this.dao.findUserByMail(mail);
    }

    @Override
    @Transactional
    public User createByAdmin(UserCreateDto item) {

        validateForCreateByAdmin(item);

        User user = this.userMapper.dtoToEntityMapper(item, passwordEncoder);


        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = getCurrentUser(mail);

        User saveUser = this.dao.save(user);

        this.auditService.create(
                new Audit(CREATED_BY_ADMIN, EEssenceType.USER, saveUser.getUuid().toString()),
                currentUser);

        return saveUser;
    }

    @Override
    public PageDto get(Pageable pageable) {

        Page<User> items = this.dao.findAll(pageable);
        PageDto<UserDto> dto = this.userMapper.mapper(items);

        return dto;

    }

    @Override
    public UserDto read(UUID uuid) {

        User readed = this.dao.findById(uuid).orElseThrow();
        UserDto out = this.userMapper.entityToDtoMapper(readed);

        return out;
    }

    @Override
    @Transactional
    public User update(UUID id, LocalDateTime dtUpdate, UserCreateDto item) {

        validateForCreateByAdmin(item);

        User readed = this.dao.findById(id).orElseThrow();


        if (!readed.getDtUpdate().isEqual(dtUpdate)) {
            throw new OptimisticLockException(EDITED);
        }
        readed = this.userMapper.updateUserMapper(item, readed, passwordEncoder);

        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = getCurrentUser(mail);

        this.auditService.create(
                new Audit(UPDATED_BY_ADMIN, EEssenceType.USER, readed.getUuid().toString()),
                currentUser);

        return this.dao.save(readed);
    }


    @Override
    @Transactional
    public UserDto activate(String code, String mail) {
        User user = this.dao.findUserByMail(mail);
        if (!user.getUuid().toString().equals(code)) {
            throw new NoSuchElementException("Пользователь не найден");
        }
        user.setUserStatus(EUserStatus.ACTIVATED);
        this.dao.save(user);

        this.auditService.create(new Audit(ACTIVATED, EEssenceType.USER, user.getUuid().toString()),
                user);

        return this.userMapper.entityToDtoMapper(user);
    }

    private void validate(UserRegistrationDto item) {
        if (item == null) {
            throw new IllegalStateException("Вы не передали item");
        }
    }

    private void validateForCreateByAdmin(UserCreateDto item) {
        if (item == null) {
            throw new IllegalStateException("Вы не передали item");
        }
        if (!item.getRole().name().equals(ERole.ROLE_ADMIN.name()) && !item.getRole().name().equals(ERole.ROLE_USER.name())) {
            throw new IllegalArgumentException("Неверно указана роль юзера");
        }
        if (item.getStatus() != EUserStatus.ACTIVATED &&
                item.getStatus() != EUserStatus.DEACTIVATED &&
                item.getStatus() != EUserStatus.WAITING_ACTIVATION) {
            throw new IllegalArgumentException("Неверно указан статус");
        }
    }


}
