package com.kaizensoftware.visitstory.app.dto.place.create;

import com.kaizensoftware.visitstory.app.dto.place.PlaceDTO;

import lombok.EqualsAndHashCode;
import lombok.Data;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlaceCreateDTO extends PlaceDTO {

    public PlaceCreateDTO(String name, double latitude, double longitude) {
        setName(name);
        setLatitude(latitude);
        setLongitude(longitude);
    }

}
