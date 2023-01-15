package by.it_academy.kopytko.controller;

import by.it_academy.kopytko.service.api.IProfileService;
import by.it_academy.kopytko.service.dto.ProfileForCU;
import by.it_academy.kopytko.service.dto.ProfileForOut;
import by.it_academy.kopytko.service.dto.UserCreateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final IProfileService service;

    public ProfileController(IProfileService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<UserCreateDto> create(@RequestBody @Valid ProfileForCU item) {

        this.service.create(item);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("{uuid_profile}")
    public ResponseEntity<ProfileForOut> get(@PathVariable("uuid_profile") UUID uuid) {
        ProfileForOut profile = this.service.read(uuid);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

}
