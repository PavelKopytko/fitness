package by.it_academy.kopytko.controller;

import by.it_academy.kopytko.dao.entity.User;
import by.it_academy.kopytko.service.api.IUserService;
import by.it_academy.kopytko.service.dto.PageDto;
import by.it_academy.kopytko.service.dto.UserCreateDto;
import by.it_academy.kopytko.service.dto.UserDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class AdminController {

    private final IUserService service;

    public AdminController(IUserService service) {
        this.service = service;
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody @Valid UserCreateDto item) {

        this.service.createByAdmin(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<PageDto> getList(
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page, size);
        PageDto<UserDto> users = this.service.get(pageable);

        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @GetMapping("/users/{uuid}")
    public ResponseEntity<UserDto> read(@PathVariable UUID uuid) {
        UserDto dto = this.service.read(uuid);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/users/{id}/dt_update/{dt_update}")
    public ResponseEntity<?> put(@PathVariable UUID id,
                                 @PathVariable("dt_update") long dtUpdateRaw,
                                 @RequestBody @Valid UserCreateDto item) {
        LocalDateTime dtUpdate = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(dtUpdateRaw),
                ZoneId.of("UTC")
        );

        User user = this.service.update(id, dtUpdate, item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
