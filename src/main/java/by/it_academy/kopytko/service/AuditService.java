package by.it_academy.kopytko.service;

import by.it_academy.kopytko.dao.IAuditDao;
import by.it_academy.kopytko.dao.entity.Audit;
import by.it_academy.kopytko.dao.entity.User;
import by.it_academy.kopytko.service.api.IAuditService;
import by.it_academy.kopytko.service.dto.AuditDto;
import by.it_academy.kopytko.service.dto.PageDto;
import by.it_academy.kopytko.util.mappers.AuditMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AuditService implements IAuditService {
    private final IAuditDao dao;
    private final AuditMapper auditMapper;


    public AuditService(IAuditDao dao, AuditMapper auditMapper) {
        this.dao = dao;
        this.auditMapper = auditMapper;
    }

    @Override
    @Transactional
    public Audit create(Audit audit, User user) {

        Audit audit1 = this.auditMapper.fullMapper(audit, user);

        return this.dao.save(audit1);
    }

    @Override
    public PageDto<AuditDto> get(Pageable pageable) {
        Page<Audit> items = this.dao.findAll(pageable);
        return this.auditMapper.mapper(items);
    }

    @Override
    public AuditDto read(UUID uuid) {
        Audit audit = this.dao.findByUuid(uuid).orElseThrow();
        return this.auditMapper.entityToDtoListMapper(audit);
    }

}
