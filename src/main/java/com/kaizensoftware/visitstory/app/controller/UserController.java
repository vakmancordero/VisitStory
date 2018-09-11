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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController extends BaseController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserCreatedDTO> create(@RequestBody @Valid UserCreateDTO userInput, BindingResult br) throws Exception {
        throwErrors(br);
        return ResponseEntity.ok(userService.createAccount(userInput));
    }

    //@PostMapping("/confirm/{token}")
    @GetMapping("/confirm/{token}")
    public ResponseEntity<String> confirm(@PathVariable("token") @NotEmpty String token) throws Exception {
        return ResponseEntity.ok(userService.confirmAccount(token));
    }

    @GetMapping("/contacts")
    public ResponseEntity contacts(Authentication authentication) throws Exception {
        return ResponseEntity.ok(userService.findUserContacts(authentication.getName()));
    }

}
