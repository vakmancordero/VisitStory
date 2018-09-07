package com.kaizensoftware.visitstory.app.dto.place;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceDTO {

    private Long id;
    private String name;
    private double latitude;
    private double longitude;

    public PlaceDTO(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
