package com.kaizensoftware.visitstory.app.controller;

import com.kaizensoftware.visitstory.app.dto.place.alter.PlaceDeletedDTO;
import com.kaizensoftware.visitstory.app.dto.place.alter.PlaceUpdateDTO;
import com.kaizensoftware.visitstory.app.service.PlaceService;

import com.kaizensoftware.visitstory.app.dto.place.PlaceDTO;
import com.kaizensoftware.visitstory.app.dto.place.create.PlaceCreatedDTO;
import com.kaizensoftware.visitstory.app.dto.place.create.PlaceCreateDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api-visit-story/places")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<List<PlaceDTO>> findAll() {
        return ResponseEntity.ok(placeService.findAllPlaces());
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<PlaceDTO>> findByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(placeService.findPlacesByName(name));
    }

    @PostMapping
    public ResponseEntity<PlaceCreatedDTO> create(@RequestBody PlaceCreateDTO placeCreateDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(placeService.createPlace(placeCreateDTO));
    }

    @PutMapping
    public ResponseEntity<PlaceCreatedDTO> update(@RequestBody PlaceUpdateDTO placeUpdateDTO) throws Exception {
        return ResponseEntity.ok(placeService.updatePlace(placeUpdateDTO, false));
    }

    @PatchMapping
    public ResponseEntity<PlaceCreatedDTO> partialUpdate(@RequestBody PlaceUpdateDTO placeUpdateDTO) throws Exception {
        return ResponseEntity.ok(placeService.updatePlace(placeUpdateDTO, true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlaceDeletedDTO> delete(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(placeService.deletePlace(id));
    }

}
