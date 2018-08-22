package com.kaizensoftware.visitstory.app.controller;

import com.kaizensoftware.visitstory.app.dto.user.UserInput;
import com.kaizensoftware.visitstory.app.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity find(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(userService.findUserById(id));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("recycle-bin")
    public ResponseEntity recycleBin() {
        return ResponseEntity.ok(userService.findAllUsersInactive());
    }

    @GetMapping("/recycle-bin/{id}")
    public ResponseEntity findInRecycleBin(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(userService.findInactiveUserById(id));
        } catch(Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody UserInput userInput) {
        try {
            return ResponseEntity.ok(userService.createUser(userInput));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @DeleteMapping
    public ResponseEntity deleteAll() {
        try {
            userService.deleteAllUsers();
            return ResponseEntity.ok().build();
        } catch(Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

}
