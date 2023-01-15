package by.it_academy.kopytko.service;

import by.it_academy.kopytko.util.JwtTokenUtil;
import by.it_academy.kopytko.dao.IProfileDao;
import by.it_academy.kopytko.dao.entity.*;
import by.it_academy.kopytko.service.api.IAuditService;
import by.it_academy.kopytko.service.api.IProfileService;
import by.it_academy.kopytko.service.api.IUserService;
import by.it_academy.kopytko.service.dto.ProfileForCU;
import by.it_academy.kopytko.service.dto.ProfileForOut;
import by.it_academy.kopytko.util.mappers.ProfileMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProfileService implements IProfileService {

    private final static String CREATED = "Создан профиль";
    private final IProfileDao dao;
    private final IUserService userService;
    private final IAuditService auditService;
    private final ProfileMapper profileMapper;
    private final MailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil tokenUtil;

    public ProfileService(IProfileDao dao, IAuditService auditService, IUserService userService, ProfileMapper profileMapper, MailSender mailSender, PasswordEncoder passwordEncoder, JwtTokenUtil tokenUtil) {
        this.dao = dao;
        this.userService = userService;
        this.profileMapper = profileMapper;
        this.auditService = auditService;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = tokenUtil;
    }

    @Override
    @Transactional
    public Profile create(ProfileForCU item) {

        validate(item);

        String mail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = this.userService.getCurrentUser(mail);

        Profile profile = this.profileMapper.dtoToEntityMapper(item, user);
        Profile readed = this.dao.save(profile);

        this.auditService.create(new Audit(CREATED, EEssenceType.PROFILE, readed.getUuid().toString()), user);


        return readed;
    }

    @Override
    public ProfileForOut read(UUID uuid) {
        return this.profileMapper.entityToDtoMapper(getByUuid(uuid));
    }

    @Override
    public Profile getByUuid(UUID uuid) {
        return this.dao.findByUuid(uuid).orElseThrow();
    }


    private void validate(ProfileForCU item) {
        if (item == null) {
            throw new IllegalStateException("Не передан профиль");
        }
        if (!item.getActivityType().equals(EActivityType.ACTIVE)) {
            if (!item.getActivityType().equals(EActivityType.NOT_ACTIVE))
                throw new IllegalArgumentException("Неверный параметр активности");
        }
        if (!item.getSex().equals(EProfileSex.MALE)) {
            if (!item.getSex().equals(EProfileSex.FEMALE))
                throw new IllegalArgumentException("Неверно указан пол");
        }
    }


}
