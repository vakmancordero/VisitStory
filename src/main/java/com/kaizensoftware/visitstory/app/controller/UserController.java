package com.kaizensoftware.visitstory.app.controller;

import com.kaizensoftware.visitstory.app.dto.user.create.*;
import com.kaizensoftware.visitstory.app.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid UserCreateDTO userInput) {
        try {
            return ResponseEntity.ok(userService.createUser(userInput));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

}
