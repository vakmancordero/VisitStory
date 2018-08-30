package com.kaizensoftware.visitstory.app.controller;

import com.kaizensoftware.visitstory.app.dto.user.create.*;
import com.kaizensoftware.visitstory.app.service.UserService;

import com.kaizensoftware.visitstory.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController extends BaseController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity create(@RequestBody @Valid UserCreateDTO userInput,
                                 BindingResult br, HttpServletRequest request) throws Exception {
        throwErrors(br);

        return ResponseEntity.ok(userService.createUser(userInput, request));
    }

}
