package by.it_academy.kopytko.controller;

import by.it_academy.kopytko.service.UserService;
import by.it_academy.kopytko.service.api.IActivationService;
import by.it_academy.kopytko.service.api.IUserService;
import by.it_academy.kopytko.service.dto.UserCreateDto;
import by.it_academy.kopytko.service.dto.UserDto;
import by.it_academy.kopytko.service.dto.UserLoginDto;
import by.it_academy.kopytko.service.dto.UserRegistrationDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserService service;
    private final IActivationService activationService;

    public UserController(UserService service, IActivationService activationService) {
        this.service = service;
        this.activationService = activationService;
    }

    @PostMapping("/registration")
    public ResponseEntity<UserCreateDto> registrate(@RequestBody @Valid UserRegistrationDto item) {

        this.activationService.registrate(item);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginate(@RequestBody @Valid UserLoginDto item) {

        String token = this.service.getToken(item);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = this.service.getByMail(userDetails.getUsername());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/activate/{code}/userMail/{userMail}")
    public ResponseEntity<?> activate(@PathVariable String code,
                                      @PathVariable("userMail") String userMail) {
        this.service.activate(code, userMail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
