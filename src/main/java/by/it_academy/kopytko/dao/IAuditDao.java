package by.it_academy.kopytko.dao;

import by.it_academy.kopytko.dao.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IAuditDao extends JpaRepository<Audit, UUID> {

    Optional<Audit> findByUuid(UUID uuid);

}
