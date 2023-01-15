package by.it_academy.kopytko.controller;

import by.it_academy.kopytko.controller.exceptions.LockException;
import by.it_academy.kopytko.dao.entity.Diary;
import by.it_academy.kopytko.service.api.IDiaryService;
import by.it_academy.kopytko.service.dto.DiaryDto;
import by.it_academy.kopytko.service.dto.PageDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profile")
public class DiaryController {

    private final IDiaryService service;

    public DiaryController(IDiaryService service) {
        this.service = service;
    }

    @GetMapping("/{uuid_profile}/journal/food")
    public ResponseEntity<PageDto> getList(@PathVariable("uuid_profile") UUID profileUuid,
                                           @RequestParam int page,
                                           @RequestParam int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageDto<Diary> diaries = this.service.get(pageable, profileUuid);

        return new ResponseEntity<>(diaries, HttpStatus.OK);
    }


    @PostMapping("/{uuid_profile}/journal/food")
    public ResponseEntity<?> post(@PathVariable("uuid_profile") UUID profileUuid,
                                  @RequestBody @Valid DiaryDto item) throws LockException {

        this.service.create(item, profileUuid);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
