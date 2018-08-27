package com.kaizensoftware.visitstory.app.controller;

import com.kaizensoftware.visitstory.app.dto.visit_story.create.VisitStoryCreateDTO;
import com.kaizensoftware.visitstory.app.service.VisitStoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/visit-story")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VisitStoryController {

    private final VisitStoryService vsService;

    @PostMapping
    public ResponseEntity makeVisitStory(
            @RequestBody VisitStoryCreateDTO visitStory, @ModelAttribute List<MultipartFile> contents) throws Exception {

        Object vs = vsService.makeVisitStory(visitStory);

        return ResponseEntity.ok(vs);
    }

}
