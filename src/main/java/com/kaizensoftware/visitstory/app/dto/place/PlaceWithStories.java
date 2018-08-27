package com.kaizensoftware.visitstory.app.dto.place;

import com.kaizensoftware.visitstory.app.dto.vs.VisitStoryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlaceWithStories extends PlaceDTO {

    private List<VisitStoryDTO> visitStories;

}
