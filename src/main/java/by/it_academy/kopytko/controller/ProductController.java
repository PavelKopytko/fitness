package by.it_academy.kopytko.controller;

import by.it_academy.kopytko.controller.exceptions.LockException;
import by.it_academy.kopytko.dao.entity.Product;
import by.it_academy.kopytko.service.api.IProductService;
import by.it_academy.kopytko.service.dto.PageDto;
import by.it_academy.kopytko.service.dto.ProductForCUDto;
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
@RequestMapping("/api/v1/product")
public class ProductController {

    private final IProductService service;

    public ProductController(IProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PageDto> getList(
            @RequestParam int page,
            @RequestParam int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageDto<Product> products = this.service.get(pageable);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody @Valid ProductForCUDto item) {

        this.service.create(item);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}/dt_update/{dt_update}")
    public ResponseEntity<?> put(@PathVariable UUID id,
                                 @PathVariable("dt_update") long dtUpdateRaw,
                                 @RequestBody @Valid ProductForCUDto item) throws LockException {

        LocalDateTime dtUpdate = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(dtUpdateRaw),
                ZoneId.of("UTC")
        );

        Product user = this.service.update(id, dtUpdate, item);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
