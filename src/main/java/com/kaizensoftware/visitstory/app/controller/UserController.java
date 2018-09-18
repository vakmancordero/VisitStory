package com.kaizensoftware.visitstory.app.controller;

import com.kaizensoftware.visitstory.app.dto.user.create.*;
import com.kaizensoftware.visitstory.app.service.user.UserService;

import com.kaizensoftware.visitstory.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api-visit-story/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController extends BaseController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity create(@RequestBody @Valid UserCreateDTO userInput, BindingResult br) throws Exception {
        throwErrors(br);
        return ResponseEntity.ok(userService.createAccount(userInput));
    }

    @GetMapping("/confirm/{token}")
    public ResponseEntity confirm(@PathVariable("token") @NotEmpty String token) throws Exception {
        return ResponseEntity.ok(userService.confirmAccount(token));
    }

    @PostMapping("/addContact")
    public ResponseEntity addContact(Authentication authentication, @RequestParam("userContactId") Long userContactId) throws Exception {
        return ResponseEntity.ok(userService.addUserContact(authentication.getName(), userContactId));
    }

    @PostMapping("/removeContact")
    public ResponseEntity removeContact(Authentication authentication, @RequestParam("userContactId") Long userContactId) throws Exception {
        return ResponseEntity.ok(userService.removeUserContact(authentication.getName(), userContactId));
    }

    @PostMapping("/blockContact")
    public ResponseEntity blockContact(Authentication authentication, @RequestParam("userContactId") Long userContactId) throws Exception {
        return ResponseEntity.ok(userService.blockUserContact(authentication.getName(), userContactId));
    }

    @GetMapping("/contacts")
    public ResponseEntity contacts(Authentication authentication) throws Exception {
        return ResponseEntity.ok(userService.findUserContacts(authentication.getName()));
    }

}
