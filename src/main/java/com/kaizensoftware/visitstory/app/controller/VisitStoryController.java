package com.kaizensoftware.visitstory.app.controller;

import com.kaizensoftware.visitstory.app.dto.visit_story.create.VisitStoryCreateDTO;
import com.kaizensoftware.visitstory.app.service.VisitStoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/visit-story")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VisitStoryController {

    private final VisitStoryService vsService;

    @PostMapping
    public ResponseEntity makeVisitStory(
            @RequestPart(value = "visitStory") VisitStoryCreateDTO visitStory,
            @RequestPart(value = "contents", required = false) List<MultipartFile> contents) throws Exception {

        visitStory.setContents(contents);

        Object vs = vsService.makeVisitStory(visitStory);

        return ResponseEntity.ok(vs);
    }

}
