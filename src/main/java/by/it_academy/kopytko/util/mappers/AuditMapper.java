package by.it_academy.kopytko.util.mappers;

import by.it_academy.kopytko.dao.entity.Audit;
import by.it_academy.kopytko.dao.entity.ERole;
import by.it_academy.kopytko.dao.entity.EUserStatus;
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
public class AuditMapper {

    public AuditMapper() {
    }

    public Audit fullMapper(Audit audit, User user) {

        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        UUID id = UUID.randomUUID();

        audit.setUuid(id);
        audit.setDtCreate(time);
        audit.setUser(user);

        return audit;
    }


    public PageDto<AuditDto> mapper(Page<Audit> items) {

        PageDto<AuditDto> dto = new PageDto<>();
        dto.setNumber(items.getNumber());
        dto.setSize(items.getSize());
        dto.setTotalPages(items.getTotalPages());
        dto.setTotalElements((int) items.getTotalElements());
        dto.setFirst(items.isFirst());
        dto.setNumberOfElements(items.getNumberOfElements());
        dto.setLast(items.isLast());

        List<AuditDto> report = new ArrayList<>();

        for (Audit audit : items.getContent()) {

            report.add(entityToDtoListMapper(audit));
        }

        dto.setContent(report);

        return dto;
    }

    public AuditDto entityToDtoListMapper(Audit audit){

        UserDto user = new UserDto();
        user.setUuid(audit.getUser().getUuid());
        user.setDtCreate(audit.getUser().getDtCreate());
        user.setDtUpdate(audit.getUser().getDtUpdate());
        user.setNick(audit.getUser().getNick());
        user.setMail(audit.getUser().getMail());
        user.setRole(audit.getUser().getRole());
        user.setStatus(audit.getUser().getUserStatus());

        AuditDto auditDto = new AuditDto();
        auditDto.setUuid(audit.getUuid());
        auditDto.setUser(user);
        auditDto.setText(audit.getText());
        auditDto.setType(audit.getType());
        auditDto.setId(audit.getId());

        return auditDto;
    }

}
