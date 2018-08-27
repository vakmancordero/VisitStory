package com.kaizensoftware.visitstory.app.service;

import com.kaizensoftware.visitstory.app.dto.place.alter.PlaceDeletedDTO;
import com.kaizensoftware.visitstory.app.dto.place.alter.PlaceUpdateDTO;
import com.kaizensoftware.visitstory.app.dto.place.create.PlaceCreateDTO;
import com.kaizensoftware.visitstory.app.dto.place.PlaceDTO;
import com.kaizensoftware.visitstory.app.dto.place.create.PlaceCreatedDTO;
import com.kaizensoftware.visitstory.app.persistence.model.Place;
import com.kaizensoftware.visitstory.app.persistence.repository.PlaceRepo;

import com.kaizensoftware.visitstory.common.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService extends BaseService<PlaceRepo, Place> {

    public List<PlaceDTO> findAllPlaces() {
        return findAll(PlaceDTO.class);
    }

    public List<PlaceDTO> findPlacesByName(String name) {

        List<Place> places = repository.findByNameIgnoreCaseContaining(name);

        return convertUtils.convert(places, PlaceDTO.class);
    }

    public PlaceCreatedDTO createPlace(PlaceCreateDTO placeCreateDTO) throws Exception {
        return create(placeCreateDTO, PlaceCreatedDTO.class);
    }

    public PlaceCreatedDTO updatePlace(PlaceUpdateDTO placeUpdateDTO, boolean partialUpdate) throws Exception {
        return update(placeUpdateDTO.getId(), placeUpdateDTO, PlaceCreatedDTO.class, partialUpdate);
    }

    public PlaceDeletedDTO deletePlace(Long placeId) throws Exception {
        return delete(placeId, PlaceDeletedDTO.class, false);
    }

}
