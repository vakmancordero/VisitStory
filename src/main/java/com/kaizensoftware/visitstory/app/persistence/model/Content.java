package com.kaizensoftware.visitstory.app.persistence.model;

import com.kaizensoftware.visitstory.common.persistence.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "Content")
@Table(name = "Content", schema = "visitStory")
public class Content extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitstory_id")
    private VisitStory visitStory;

    private String pathReference;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

}
