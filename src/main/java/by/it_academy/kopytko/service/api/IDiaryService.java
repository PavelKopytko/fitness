package by.it_academy.kopytko.service.api;

import by.it_academy.kopytko.controller.exceptions.LockException;
import by.it_academy.kopytko.dao.entity.Diary;
import by.it_academy.kopytko.service.dto.DiaryDto;
import by.it_academy.kopytko.service.dto.PageDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;


public interface IDiaryService {
    public Diary create(DiaryDto item, UUID profileUuid) throws LockException;

    PageDto get(Pageable pageable, UUID uuid);

    Diary read(UUID id);


}
