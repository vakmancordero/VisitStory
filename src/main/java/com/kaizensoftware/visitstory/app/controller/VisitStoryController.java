package com.kaizensoftware.visitstory.app.controller;

import com.kaizensoftware.visitstory.app.dto.user.UserInput;
import com.kaizensoftware.visitstory.app.dto.vs.VisitStoryCreateDTO;
import com.kaizensoftware.visitstory.app.service.VisitStoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

@RestController
@RequestMapping("/visit-story")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VisitStoryController {

    private final VisitStoryService vsService;

    @PostMapping
    public ResponseEntity makeVisitStory(@RequestBody @Valid VisitStoryCreateDTO visitStory) {
        try {
            return ResponseEntity.ok(vsService.makeVisitStory(visitStory));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

}
