package by.it_academy.kopytko.service.api;

import by.it_academy.kopytko.controller.exceptions.LockException;
import by.it_academy.kopytko.service.dto.PageDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IService<TYPE, DtoType> {

    TYPE create(DtoType item);

    TYPE read(UUID id);

    PageDto get(Pageable pageable);

    TYPE update(UUID id, LocalDateTime dtUpdate, DtoType item) throws LockException;

}
