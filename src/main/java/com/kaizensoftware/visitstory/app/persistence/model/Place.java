package com.kaizensoftware.visitstory.app.persistence.model;

import com.kaizensoftware.visitstory.common.persistence.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "Place")
@Table(name = "Place", schema = "visitStory")
public class Place extends BaseEntity {

    private String name;
    private double latitude;
    private double longitude;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VisitStory> visitStories;

}
