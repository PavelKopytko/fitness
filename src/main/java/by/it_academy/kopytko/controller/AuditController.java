package by.it_academy.kopytko.controller;

import by.it_academy.kopytko.dao.entity.Audit;
import by.it_academy.kopytko.service.api.IAuditService;
import by.it_academy.kopytko.service.dto.AuditDto;
import by.it_academy.kopytko.service.dto.PageDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/audit")
public class AuditController {

    private final IAuditService service;

    public AuditController(IAuditService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PageDto> getList(
            @RequestParam int page,
            @RequestParam int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageDto<Audit> report = this.service.get(pageable);

        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<AuditDto> put(@PathVariable UUID uuid) {

        AuditDto audit = this.service.read(uuid);

        return new ResponseEntity<>(audit, HttpStatus.OK);
    }


}
