package by.it_academy.kopytko.service.api;

import by.it_academy.kopytko.dao.entity.Audit;
import by.it_academy.kopytko.dao.entity.User;
import by.it_academy.kopytko.service.dto.AuditDto;
import by.it_academy.kopytko.service.dto.PageDto;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IAuditService {

    Audit create(Audit audit, User user);

    PageDto get(Pageable pageable);

    AuditDto read(UUID uuid);

}
