package com.kaizensoftware.visitstory.app.controller;

import com.kaizensoftware.visitstory.app.dto.comment.SimpleCommentDTO;
import com.kaizensoftware.visitstory.app.dto.visit_story.create.*;
import com.kaizensoftware.visitstory.app.service.VisitStoryService;

import com.kaizensoftware.visitstory.common.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/visit-story")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VisitStoryController extends BaseController {

    private final VisitStoryService visitStoryService;

    @PostMapping
    public ResponseEntity makeVisitStory(Authentication authentication,
            @RequestPart(value = "visitStory") SimpleVisitStoryCreateDTO visitStory,
            @RequestPart(value = "contents", required = false) List<MultipartFile> contents) throws Exception {

        visitStory.setCurrentUser(authentication.getName());
        visitStory.setContents(contents);

        return ResponseEntity.ok(visitStoryService.makeVisitStory(visitStory));
    }

    @GetMapping
    public ResponseEntity findVisitStories(Authentication authentication, Pageable pageable) throws Exception {
        return ResponseEntity.ok(visitStoryService.findVisitStories(authentication.getName(), pageable));
    }

    @PostMapping
    public ResponseEntity addComment(Authentication authentication,
            @RequestPart(value = "comment") SimpleCommentDTO simpleComment) throws Exception {

        simpleComment.setCurrentUser(authentication.getName());

        return ResponseEntity.ok(visitStoryService.addComment(simpleComment));
    }

}
