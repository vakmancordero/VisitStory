package com.kaizensoftware.visitstory.app.persistence.model;

import com.kaizensoftware.visitstory.common.persistence.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "GenderReference")
@Table(name = "GenderReference", schema = "visitStory")
public class GenderReference extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String description;

}
