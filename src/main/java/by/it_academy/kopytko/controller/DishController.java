package by.it_academy.kopytko.controller;

import by.it_academy.kopytko.controller.exceptions.LockException;
import by.it_academy.kopytko.dao.entity.Dish;
import by.it_academy.kopytko.service.api.IDishService;
import by.it_academy.kopytko.service.dto.DishDto;
import by.it_academy.kopytko.service.dto.PageDto;
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
@RequestMapping("/api/v1/recipe")
public class DishController {

    private final IDishService service;

    public DishController(IDishService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<PageDto> getList(
            @RequestParam int page,
            @RequestParam int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageDto<Dish> items = this.service.get(pageable);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Dish> post(@RequestBody @Valid DishDto item) {


        this.service.create(item);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}/dt_update/{dt_update}")
    public ResponseEntity<?> put(@PathVariable UUID id,
                                 @PathVariable("dt_update") long dtUpdateRaw,
                                 @RequestBody DishDto item) throws LockException {

        LocalDateTime dtUpdate = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(dtUpdateRaw),
                ZoneId.of("UTC")
        );

        this.service.update(id, dtUpdate, item);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
