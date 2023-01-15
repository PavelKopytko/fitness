package by.it_academy.kopytko.dao;

import by.it_academy.kopytko.dao.entity.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IDiaryDao extends JpaRepository<Diary, UUID> {

    Page<Diary> findAllByProfileUuid(Pageable pageable, UUID profileUuid);
}
