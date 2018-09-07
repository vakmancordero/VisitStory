package com.kaizensoftware.visitstory.app.service;

import static com.kaizensoftware.visitstory.common.util.EventMessage.*;

import com.kaizensoftware.visitstory.app.dto.place.alter.PlaceDeletedDTO;
import com.kaizensoftware.visitstory.app.dto.place.alter.PlaceUpdateDTO;
import com.kaizensoftware.visitstory.app.dto.place.create.PlaceCreateDTO;
import com.kaizensoftware.visitstory.app.dto.place.PlaceDTO;
import com.kaizensoftware.visitstory.app.dto.place.create.PlaceCreatedDTO;
import com.kaizensoftware.visitstory.app.persistence.model.Place;
import com.kaizensoftware.visitstory.app.persistence.repository.PlaceRepo;

import com.kaizensoftware.visitstory.common.config.exception.model.ValidationException;
import com.kaizensoftware.visitstory.common.service.BaseService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceService extends BaseService<PlaceRepo, Place> {

    public PlaceDTO findPlaceById(Long placeId) throws Exception {
        return findById(placeId, PlaceDTO.class);
    }

    public PlaceDTO findPlaceByName(String name) throws ValidationException {

        String messageError = String.format(NON_EXISTENT_PLACE.getMessage(), name);

        Optional<Place> place = repository.findByName(name);

        return convertUtil.convert(place.orElseThrow(() -> new ValidationException(messageError)), PlaceDTO.class);
    }

    public List<PlaceDTO> findAllPlaces() {
        return findAll(PlaceDTO.class);
    }

    public List<PlaceDTO> findPlacesByName(String name) {

        List<Place> places = repository.findByNameIgnoreCaseContaining(name);

        return convertUtil.convert(places, PlaceDTO.class);
    }

    public PlaceCreatedDTO createPlace(PlaceCreateDTO placeCreateDTO) throws Exception {

        try {
            return convertUtil.convert(findPlaceByName(placeCreateDTO.getName()), PlaceCreatedDTO.class);
        } catch(ValidationException ex) {
            return create(placeCreateDTO, PlaceCreatedDTO.class);
        }
    }

    public PlaceCreatedDTO updatePlace(PlaceUpdateDTO placeUpdateDTO, boolean partialUpdate) throws Exception {
        return update(placeUpdateDTO.getId(), placeUpdateDTO, PlaceCreatedDTO.class, partialUpdate);
    }

    public PlaceDeletedDTO deletePlace(Long placeId) throws Exception {
        return delete(placeId, PlaceDeletedDTO.class, false);
    }

    public PlaceDTO findPlace(Long placeId) throws Exception {

        String errorMessage = String.format(INVALID_PLACE.getMessage(), placeId);

        try {
            return this.findPlaceById(placeId);
        } catch(EntityNotFoundException ex) {
            throw new ValidationException(errorMessage);
        }

    }

}
