package com.kaizensoftware.visitstory.app.controller;

import com.kaizensoftware.visitstory.app.dto.vs.create.VisitStoryCreateDTO;
import com.kaizensoftware.visitstory.app.service.VisitStoryService;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity makeVisitStory(@RequestBody @Valid VisitStoryCreateDTO visitStory) throws Exception {

        Object vs = vsService.makeVisitStory(visitStory);

        return ResponseEntity.ok(vs);
    }

}
