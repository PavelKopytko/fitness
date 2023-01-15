package by.it_academy.kopytko.service.api;

import by.it_academy.kopytko.controller.exceptions.LockException;
import by.it_academy.kopytko.service.dto.PageDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IMailSender {

    void send(String mailTo, String subject, String message);

}
